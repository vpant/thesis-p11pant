package org.twittercity.twittercitymod.tickhandlers;

import java.util.Comparator;
import java.util.List;

import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.twittercity.twittercitymod.city.BuildingReference;
import org.twittercity.twittercitymod.city.CitiesManager;
import org.twittercity.twittercitymod.concurrency.ExecutorProvider;
import org.twittercity.twittercitymod.concurrency.GetTweetsRunnable;
import org.twittercity.twittercitymod.concurrency.ITaskBlocker;
import org.twittercity.twittercitymod.concurrency.ReentrantLockTaskBlocker;
import org.twittercity.twittercitymod.config.ConfigurationManager;
import org.twittercity.twittercitymod.data.db.Tweet;
import org.twittercity.twittercitymod.data.world.CityWorldData;
import org.twittercity.twittercitymod.data.world.ConstructionWorldData;

import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;

public class TickHandler {
	// 20 ticks per second
	private static final int TICKS_TO_MINUTES = 20 * 60;
	private int searchDatabaseTimer = 0;
	private final ITaskBlocker taskBlocker = new ReentrantLockTaskBlocker();
	
	@SubscribeEvent
	public void buildFromTweetsQueue(TickEvent.ServerTickEvent event) {
		if(!BuildingReference.tweetsToBuild.isEmpty()) {
			int endIndex = BuildingReference.tweetsToBuild.size();
			int fromIndex = Math.max(endIndex - BuildingReference.tweetsPerTick, 0);
			List<Tweet> sublistToBuild = BuildingReference.tweetsToBuild.subList(fromIndex, endIndex);
			boolean success = CitiesManager.getInstance().startBuilding(sublistToBuild);

			if(success) {
				int latestTweetId = sublistToBuild.stream().max(Comparator.comparing(Tweet::getID)).get().getID();
				World twitterWorld = DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID);
				ConstructionWorldData.get(twitterWorld).setLatestTweetID(latestTweetId);
				sublistToBuild.clear();
			}
		} 	
	}
	
	@SubscribeEvent
	public void checkForNewTweets(TickEvent.WorldTickEvent event) {
		if(ConfigurationManager.buildingOptions.pauseNewTweetsCheck.isEnabled()) {
			return;
		}
		
		if(event.side == Side.CLIENT && !BuildingReference.tweetsToBuild.isEmpty()) {
			return;
		}
		WorldServer worldServer = (WorldServer)event.world; 
		searchDatabaseTimer++;
		if(ConfigurationManager.buildingOptions.minutesBetweenCheckingForNewTweets * TICKS_TO_MINUTES <= searchDatabaseTimer) {
			int latestRetrievedTweetId = ConstructionWorldData.get(worldServer).getLatestTweetID();
			ExecutorProvider.getExecutorService().execute(new GetTweetsRunnable(taskBlocker, worldServer, latestRetrievedTweetId));
			searchDatabaseTimer = 0;
		}
	}
}
