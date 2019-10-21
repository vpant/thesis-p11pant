package org.twittercity.twittercitymod.tickhandlers;

import java.util.List;

import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.city.BuildingReference;
import org.twittercity.twittercitymod.city.CitiesManager;
import org.twittercity.twittercitymod.config.ConfigurationManager;
import org.twittercity.twittercitymod.data.db.Tweet;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TickHanlder {
	
	// 20 ticks per second
	private int ticksToMinutes = 20 * 60;
	private int searchDatabaseTimer = 0;
	
	@SubscribeEvent
	public void buildFromTweetsQueue(TickEvent.ServerTickEvent event) {
		if(!BuildingReference.cityPreparationActive && !BuildingReference.tweetsToBuild.isEmpty()) {
			int endIndex = BuildingReference.tweetsToBuild.size();
			int fromIndex = endIndex - BuildingReference.tweetsPerTick > 0 ? endIndex - BuildingReference.tweetsPerTick : 0 ;
			List<Tweet> sublistTobuild = BuildingReference.tweetsToBuild.subList(fromIndex, endIndex);
			TwitterCity.logger.info("Building... fromIndex: {}, endIndex: {}", fromIndex, endIndex);
			boolean success = CitiesManager.getInstance().startBuilding(sublistTobuild);
			if(success) { sublistTobuild.clear(); }
		} 	
	}
	
	@SubscribeEvent
	public void checkForNewTweets(TickEvent.ServerTickEvent event) {
		searchDatabaseTimer++;
		if(ConfigurationManager.buildingOptions.minutesBetweenCheckingForNewTweets * ticksToMinutes == searchDatabaseTimer) {
			
			
			searchDatabaseTimer = 0;
		}
	}
	
}
