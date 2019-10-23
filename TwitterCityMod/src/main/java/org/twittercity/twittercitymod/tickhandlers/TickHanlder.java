package org.twittercity.twittercitymod.tickhandlers;

import java.util.List;

import org.twittercity.twittercitymod.city.BuildingReference;
import org.twittercity.twittercitymod.city.CitiesManager;
import org.twittercity.twittercitymod.concurrency.ExecutorProvider;
import org.twittercity.twittercitymod.concurrency.GetTweetsRunnable;
import org.twittercity.twittercitymod.concurrency.ITaskBlocker;
import org.twittercity.twittercitymod.concurrency.ReentrantLockTaskBlocker;
import org.twittercity.twittercitymod.config.ConfigurationManager;
import org.twittercity.twittercitymod.data.db.Tweet;
import org.twittercity.twittercitymod.data.world.ConstructionWorldData;

import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class TickHanlder {	
	// 20 ticks per second
	private final int TICKS_TO_MINUTES = 20 * 60;
	private int searchDatabaseTimer = 1000;
	private final ITaskBlocker taskBlocker = new ReentrantLockTaskBlocker();
	
	@SubscribeEvent
	public void buildFromTweetsQueue(TickEvent.ServerTickEvent event) {
		if(!BuildingReference.cityPreparationActive && !BuildingReference.tweetsToBuild.isEmpty()) {
			int endIndex = BuildingReference.tweetsToBuild.size();
			int fromIndex = endIndex - BuildingReference.tweetsPerTick > 0 ? endIndex - BuildingReference.tweetsPerTick : 0 ;
			List<Tweet> sublistTobuild = BuildingReference.tweetsToBuild.subList(fromIndex, endIndex);
			boolean success = CitiesManager.getInstance().startBuilding(sublistTobuild);
			if(success) { sublistTobuild.clear(); }
		} 	
	}
	
	@SubscribeEvent
	public void checkForNewTweets(TickEvent.WorldTickEvent event) {
		if(event.side == Side.CLIENT && !BuildingReference.tweetsToBuild.isEmpty()) {
			
			return;
		}
		WorldServer worldServer = (WorldServer)event.world; 
		searchDatabaseTimer++;
		if(ConfigurationManager.buildingOptions.minutesBetweenCheckingForNewTweets * TICKS_TO_MINUTES <= searchDatabaseTimer) {
			int tweetId = ConstructionWorldData.get(worldServer).getLatestTweetID();
			ExecutorProvider.getExecutorService().execute(new GetTweetsRunnable(taskBlocker, worldServer, tweetId));
			searchDatabaseTimer = 0;
		}
	}
	
}
