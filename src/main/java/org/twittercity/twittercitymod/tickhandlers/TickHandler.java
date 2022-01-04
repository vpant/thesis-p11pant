package org.twittercity.twittercitymod.tickhandlers;

import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.city.BuildingReference;
import org.twittercity.twittercitymod.city.ConstructionOrchestration;
import org.twittercity.twittercitymod.concurrency.ExecutorProvider;
import org.twittercity.twittercitymod.config.ConfigurationManager;
import org.twittercity.twittercitymod.data.db.Tweet;
import org.twittercity.twittercitymod.data.db.TweetManager;
import org.twittercity.twittercitymod.data.world.StateData;
import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TickHandler {
    // 20 ticks per second
    private static final int TICKS_TO_MINUTES = 20 * 60;
    private int searchDatabaseTimer = 0;
    //private final ITaskBlocker taskBlocker = new ReentrantLockTaskBlocker();
    private Future<List<Tweet>> getTweetsFuture;

    @SubscribeEvent
    public void buildFromTweetsQueue(TickEvent.ServerTickEvent event) {
        if (!BuildingReference.tweetsToBuild.isEmpty()) {
            int endIndex = BuildingReference.tweetsToBuild.size();
            int fromIndex = Math.max(endIndex - BuildingReference.tweetsPerTick, 0);
            List<Tweet> sublistToBuild = BuildingReference.tweetsToBuild.subList(fromIndex, endIndex);
            final boolean changeStateId = ConstructionOrchestration.getInstance().build(sublistToBuild);

            if (changeStateId) {
                BuildingReference.tweetsToBuild.clear();
            } else {
                sublistToBuild.clear();
            }
        }
    }

    @SubscribeEvent
    public void checkForNewTweets(TickEvent.WorldTickEvent event) throws ExecutionException, InterruptedException {
        if (ConfigurationManager.buildingOptions.pauseNewTweetsCheck.isEnabled()) {
            return;
        }

        if (event.side == Side.CLIENT) {
            return;
        }

        searchDatabaseTimer++;
        if (ConfigurationManager.buildingOptions.minutesBetweenCheckingForNewTweets * TICKS_TO_MINUTES <= searchDatabaseTimer) {
            if(getTweetsFuture == null) {
                getTweetsFuture = getTweetsFuture();
            } else {
                if(getTweetsFuture.isDone()) {
                    List<Tweet> tweets = getTweetsFuture.get();
                    getTweetsFuture = null;
                    if(!tweets.isEmpty()) {
                        BuildingReference.tweetsToBuild.addAll(tweets);
                    } else {
                        World twitterWorld = DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID);
                        final int latestCityStateId = StateData.get(twitterWorld).getCurrentStateId();
                        final int nextStateId = StateData.get(twitterWorld).getNextStateId(latestCityStateId);
                        TwitterCity.logger.info("No tweets found for stateId: {} moving to next state id: {}", latestCityStateId, nextStateId);
                    }
                }
            }
            searchDatabaseTimer = 0;
        }
    }

    private Future<List<Tweet>> getTweetsFuture() {
        final WorldServer twitterWorld = DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID);
        final int latestCityStateId = StateData.get(twitterWorld).getCurrentStateId();
        final int latestRetrievedTweetId = StateData.get(twitterWorld).getLatestTweetIdForStateId(latestCityStateId);

        return ExecutorProvider.getExecutorService().submit(() -> {
            List<Tweet> tweets = TweetManager.getInstance()
                    .getTweetsAfterIdAndUsState(latestRetrievedTweetId, latestCityStateId, 10000);
            Collections.sort(tweets);
            TwitterCity.logger.info("Taking tweets after id: {}, the Tweets list size is: {} and stateId is: {}, tweetsToBeBuilt list size: {}"
                    , latestRetrievedTweetId, tweets.size(), latestCityStateId, BuildingReference.tweetsToBuild.size());
            return tweets;
        });
    }

    private void getTweets(World twitterWorld, int tweetId, int stateId) {
        List<Tweet> tweets = TweetManager.getInstance().getTweetsAfterIdAndUsState(tweetId, stateId, 10000);
        TwitterCity.logger.info("Taking tweets after id: {}, the Tweets list size is: {} and stateId is: {}", tweetId, tweets.size(), stateId);

        if (!tweets.isEmpty()) {
            Collections.sort(tweets);
            BuildingReference.tweetsToBuild.addAll(tweets);
        } else {
            final int nextStateId = StateData.get(twitterWorld).getNextStateId(stateId);
            TwitterCity.logger.info("No tweets found for stateId: {} moving to next state id: {}", stateId, nextStateId);
        }
    }
}
