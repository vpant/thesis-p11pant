package org.twittercity.twittercitymod.tickhandlers;

import java.util.Comparator;
import java.util.List;

import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.city.BuildingReference;
import org.twittercity.twittercitymod.city.CitiesManager;
import org.twittercity.twittercitymod.city.City;
import org.twittercity.twittercitymod.concurrency.ExecutorProvider;
import org.twittercity.twittercitymod.concurrency.GetTweetsRunnable;
import org.twittercity.twittercitymod.concurrency.ITaskBlocker;
import org.twittercity.twittercitymod.concurrency.ReentrantLockTaskBlocker;
import org.twittercity.twittercitymod.config.ConfigurationManager;
import org.twittercity.twittercitymod.data.db.Tweet;
import org.twittercity.twittercitymod.data.db.USStateDAO;
import org.twittercity.twittercitymod.data.world.CityWorldData;
import org.twittercity.twittercitymod.data.world.ConstructionWorldData;

import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
			boolean withRemainingBlocks = CitiesManager.getInstance().startBuilding(sublistToBuild);
			if(withRemainingBlocks) { // means that city is finished before run out of tweets
				TwitterCity.logger.info("Emptying tweetsToBuild");
				BuildingReference.tweetsToBuild.clear();
				//sublistToBuild.clear();
			}
		} else if (BuildingReference.emptyResultList) {
			// logic to support returning to unfinished cities to fill with new tweets from that state
//			ConstructionWorldData cWorldData = ConstructionWorldData.get(DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID));
//			cWorldData.setStateID(getNextStateId(cWorldData.getLatestStateID()));
//			BuildingReference.emptyResultList = false;
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

			//City city = findLastBuiltCity(worldServer);

			int latestCityStateId = ConstructionWorldData.get(worldServer).getLatestStateID();
			int latestRetrievedTweetId = ConstructionWorldData.get(worldServer).getLatestTweetID(latestCityStateId);

			ExecutorProvider.getExecutorService().execute(new GetTweetsRunnable(taskBlocker, worldServer, latestRetrievedTweetId, latestCityStateId));
			searchDatabaseTimer = 0;
		}
	}

	private City findLastBuiltCity(World world) {
		City city = CityWorldData.get(world).getUnfinishedCities().stream().findFirst().orElse(null);
		if(city == null) {
			city = CityWorldData.get(world).getCities().stream().min(Comparator.comparing(cityElement -> cityElement.getSettings().getId())).orElse(null);
		}

		return city;
	}


	private int getNextStateId(int currentStateId) {
		final int lastStateId = USStateDAO.getInstance().getLastStateId();
		final int nextStateId = currentStateId + 1;
		return lastStateId >= nextStateId ? nextStateId : 1;
	}
}
