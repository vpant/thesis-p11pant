package org.twittercity.twittercitymod.city.lazyblockspawn;

import java.util.List;

import org.twittercity.twittercitymod.data.world.BuildingQueuesWorldData;
import org.twittercity.twittercitymod.util.BlockData;
import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;

import net.minecraftforge.common.DimensionManager;

public class LazyBlockProcessQueueManagement {
	
	private LazyBlockProcessQueueManagement() {}
	
	public static void enqueueBlockForSpawn(BlockData blockData) {
		if(blockData == null) {
			return;
		}
		BuildingQueuesWorldData
			.get(DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID))
				.addToSpawnList(blockData);
	}
	
	public static void enqueueBlockListForSpawn(List<BlockData> blockList) {
		if(blockList.isEmpty()) {
			return;
		}
		blockList.forEach(LazyBlockProcessQueueManagement::enqueueBlockForSpawn);
	}
	
	public static void enqueueBlockForDestroy(BlockData blockData) {
		if(blockData == null) {
			return;
		}
		BuildingQueuesWorldData
			.get(DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID))
				.addToDestroyList(blockData);
	}
}
