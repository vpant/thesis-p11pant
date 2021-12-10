package org.twittercity.twittercitymod.tickhandlers;

import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.twittercity.twittercitymod.city.BuildingReference;
import org.twittercity.twittercitymod.city.City;
import org.twittercity.twittercitymod.city.ConstructionOrchestration;
import org.twittercity.twittercitymod.concurrency.ExecutorProvider;
import org.twittercity.twittercitymod.concurrency.GetTweetsRunnable;
import org.twittercity.twittercitymod.concurrency.ITaskBlocker;
import org.twittercity.twittercitymod.concurrency.ReentrantLockTaskBlocker;
import org.twittercity.twittercitymod.config.ConfigurationManager;
import org.twittercity.twittercitymod.data.db.Tweet;

import java.util.List;

public class TickHandler {
	// 20 ticks per second
	private static final int TICKS_TO_MINUTES = 20 * 60;
	private int searchDatabaseTimer = 0;
	private final ITaskBlocker taskBlocker = new ReentrantLockTaskBlocker();

	//@SubscribeEvent
	public void buildFromTweetsQueue(TickEvent.ServerTickEvent event) {
		if(!BuildingReference.tweetsToBuild.isEmpty()) {
			int endIndex = BuildingReference.tweetsToBuild.size();
			int fromIndex = Math.max(endIndex - BuildingReference.tweetsPerTick, 0);
			List<Tweet> sublistToBuild = BuildingReference.tweetsToBuild.subList(fromIndex, endIndex);
			ConstructionOrchestration.getInstance().build(sublistToBuild);
			sublistToBuild.clear();
		}
	}
	
	//@SubscribeEvent
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

			final City city = ConstructionOrchestration.getInstance().findLastBuiltCity();

			final int latestRetrievedTweetId = city != null ? city.getConstructionInfo().getLatestTweetID() : 0;
			final int latestCityStateId = city != null ? city.getSettings().getState().getId() : 1;

			ExecutorProvider.getExecutorService().execute(new GetTweetsRunnable(taskBlocker, worldServer, latestRetrievedTweetId, latestCityStateId));
			searchDatabaseTimer = 0;
		}
	}
}
