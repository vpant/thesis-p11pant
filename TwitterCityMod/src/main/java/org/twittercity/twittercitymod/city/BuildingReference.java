package org.twittercity.twittercitymod.city;

import org.twittercity.twittercitymod.data.db.Tweet;

/*
 * Everything the tick handlers needs to successfully build all the cities
 */
public class BuildingReference {
	public static Tweet[] tweetsToBuild; // Maybe a list to manipulate it easier
	public static boolean cityPreparationActive = false;
}
