package org.twittercity.twittercitymod.concurrency;

import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.city.BuildingReference;
import org.twittercity.twittercitymod.data.db.Tweet;
import org.twittercity.twittercitymod.data.db.TweetManager;
import org.twittercity.twittercitymod.data.world.StateData;
import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;

import java.util.Collections;
import java.util.List;

public class GetTweetsRunnable implements Runnable {
	private final int id;
	private final int stateId;
	private final WorldServer server;
	private final ITaskBlocker taskBlocker;
	
	public GetTweetsRunnable(ITaskBlocker taskBlocker, WorldServer worldServer, int id, int stateId) {
		this.id = id;
		this.stateId = stateId;
		this.server = worldServer;
		this.taskBlocker = taskBlocker;
	}
	
	@Override
	public void run() {
		taskBlocker.acquire();
		try {
			if(!BuildingReference.tweetsToBuild.isEmpty()) {
				return;
			}
			List<Tweet> tweets = TweetManager.getInstance().getTweetsAfterIdAndUsState(id, stateId, 10000);
			Collections.sort(tweets);
			server.addScheduledTask(() -> {
				TwitterCity.logger.info("Taking tweets after id: {}, the Tweets list size is: {} and stateId is: {}, tweetsToBeBuilt list size: {}"
						, id, tweets.size(), stateId, BuildingReference.tweetsToBuild.size());
				if(!tweets.isEmpty()) {
					BuildingReference.tweetsToBuild.addAll(tweets);
				} else {
					World twitterWorld = DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID);
					final int nextStateId = StateData.get(twitterWorld).getNextStateId(stateId);
					TwitterCity.logger.info("No tweets found for stateId: {} moving to next state id: {}", stateId, nextStateId);
				}
			});
		} finally {
			taskBlocker.release();
		}
	}
}
