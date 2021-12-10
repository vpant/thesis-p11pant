package org.twittercity.twittercitymod.concurrency;

import net.minecraft.world.WorldServer;
import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.city.BuildingReference;
import org.twittercity.twittercitymod.data.db.Tweet;
import org.twittercity.twittercitymod.data.db.TweetManager;

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
			TwitterCity.logger.info("Taking tweets after id: {}, the Tweets list size is: {} and stateId is: {}", id, tweets.size(), stateId);
			Collections.sort(tweets);
			server.addScheduledTask(() -> {
				BuildingReference.tweetsToBuild.addAll(tweets);
			});
		} finally {
			taskBlocker.release();
		}
	}
}
