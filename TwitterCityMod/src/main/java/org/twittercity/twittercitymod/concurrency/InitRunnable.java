package org.twittercity.twittercitymod.concurrency;

import org.twittercity.twittercitymod.data.db.TweetManager;


public class InitRunnable implements Runnable{
	@Override
	public void run() {
		TweetManager.getInstance();
	}
}
