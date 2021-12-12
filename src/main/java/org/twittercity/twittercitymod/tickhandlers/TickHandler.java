package org.twittercity.twittercitymod.tickhandlers;

import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.twittercity.twittercitymod.city.BuildingReference;
import org.twittercity.twittercitymod.city.ConstructionOrchestration;
import org.twittercity.twittercitymod.concurrency.ExecutorProvider;
import org.twittercity.twittercitymod.concurrency.GetTweetsRunnable;
import org.twittercity.twittercitymod.concurrency.ITaskBlocker;
import org.twittercity.twittercitymod.concurrency.ReentrantLockTaskBlocker;
import org.twittercity.twittercitymod.config.ConfigurationManager;
import org.twittercity.twittercitymod.data.db.Tweet;
import org.twittercity.twittercitymod.data.world.StateData;
import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;

import java.util.List;

public class TickHandler {
    // 20 ticks per second
    private static final int TICKS_TO_MINUTES = 20 * 60;
    private int searchDatabaseTimer = 0;
    private final ITaskBlocker taskBlocker = new ReentrantLockTaskBlocker();

    @SubscribeEvent
    public void buildFromTweetsQueue(TickEvent.ServerTickEvent event) {
        if (!BuildingReference.tweetsToBuild.isEmpty()) {
            int endIndex = BuildingReference.tweetsToBuild.size();
            int fromIndex = Math.max(endIndex - BuildingReference.tweetsPerTick, 0);
            List<Tweet> sublistToBuild = BuildingReference.tweetsToBuild.subList(fromIndex, endIndex);
            ConstructionOrchestration.getInstance().build(sublistToBuild);
            sublistToBuild.clear();
        }
    }

    @SubscribeEvent
    public void checkForNewTweets(TickEvent.WorldTickEvent event) {
        if (ConfigurationManager.buildingOptions.pauseNewTweetsCheck.isEnabled()) {
            return;
        }

        if (event.side == Side.CLIENT && !BuildingReference.tweetsToBuild.isEmpty()) {
            return;
        }

        searchDatabaseTimer++;
        if (ConfigurationManager.buildingOptions.minutesBetweenCheckingForNewTweets * TICKS_TO_MINUTES <= searchDatabaseTimer) {
            final WorldServer twitterWorld = DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID);
            final int latestCityStateId = StateData.get(twitterWorld).getCurrentStateId();
            final int latestRetrievedTweetId = StateData.get(twitterWorld).getLatestTweetIdForStateId(latestCityStateId);

            ExecutorProvider.getExecutorService().execute(
                    new GetTweetsRunnable(taskBlocker, (WorldServer) event.world, latestRetrievedTweetId, latestCityStateId));
            searchDatabaseTimer = 0;
        }
    }
}
