package org.twittercity.twittercitymod.city.lazyblockspawn;

import java.util.LinkedList;

import org.twittercity.twittercitymod.util.BlockData;

public class LazyBlockDestroyReference {
	
	public static LinkedList<BlockData> toDestroy = new LinkedList<>();
	
	public static int destroyStartingSize = 0;

	public static int updateDelay = 1000;

	public static boolean destroyInProgress = false;
}
