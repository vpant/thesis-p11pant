package org.twittercity.twittercitymod.city;

import org.twittercity.twittercitymod.data.db.Tweet;

import java.util.ArrayList;
import java.util.List;

/*
 * Everything the tick handlers needs to successfully build all the cities
 */
public class BuildingReference {
	// Do not need to tweetsToBuild. If is not empty it has not used to calculate additional blocks for the city. We will refetch them from the database.
	public static final List<Tweet> tweetsToBuild = new ArrayList<>();
	public static int tweetsPerTick = 100;
}