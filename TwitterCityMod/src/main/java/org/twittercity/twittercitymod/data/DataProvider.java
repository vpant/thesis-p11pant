package org.twittercity.twittercitymod.data;

import java.util.HashMap;

import javax.annotation.Nullable;

import org.twittercity.twittercitymod.data.db.Tweet;

public class DataProvider {

	private HashMap<Integer,Tweet> cachedTweets = null; // Should be this have fixed size and work as stack?
	
	@Nullable
	public Tweet getTweet(int tweetID) {
		Tweet tweet = null;
		if(cachedTweets != null) {
			tweet = cachedTweets.get(Integer.valueOf(tweetID));
		}
		
		if(tweet == null) {
			//get the tweet from database if exists
			//add it to cachedTweets before returning
		}
		return tweet;
	}
	
	
	public Tweet[] getTweetsAfter(int currentTweetID) {
		Tweet[] tweets = null;
		
		// Check in db for new tweets (seperate function returning true false)
		
		// If there new tweets maybe cache them and return the array of the new tweets
		
		return tweets;
	}
	
}
