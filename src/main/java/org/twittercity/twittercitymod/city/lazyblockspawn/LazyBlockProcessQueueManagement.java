package org.twittercity.twittercitymod.city.lazyblockspawn;

import java.util.List;

import org.twittercity.twittercitymod.city.BuildingReference;
import org.twittercity.twittercitymod.data.world.BuildingQueuesWorldData;
import org.twittercity.twittercitymod.util.BlockData;
import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.DimensionManager;

public class LazyBlockProcessQueueManagement {
	
	/**
	 * Enqueue a block to spawn
	 */
	public static void enqueueBlockForSpawn(BlockPos pos, IBlockState state) {
		if(pos == null || state == null) {
			return;
		}
		enqueueBlockForSpawn(new BlockData(pos, state, 3));
	}
	
	/**
	 * Enqueue a block to spawn with flags
	 */
	public static void enqueueBlockForSpawn(BlockPos pos, IBlockState state, int flags) {
		if(pos == null || state == null) {
			return;
		}
		enqueueBlockForSpawn(new BlockData(pos, state, flags));
	}
	
	public static void enqueueBlockForSpawn(BlockData blockData) {
		if(blockData == null) {
			return;
		}
		BuildingQueuesWorldData
			.get(DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID))
				.addToList(blockData, true);
	}
	
	public static void enqeueBlockListForSpawn(List<BlockData> blockList) {
		if(blockList.isEmpty()) {
			return;
		}
		BuildingQueuesWorldData
			.get(DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID))
				.addAllToList(blockList, true);
	}
	
	public static void enqeueBlockForDestroy(BlockData blockData) {
		if(blockData == null) {
			return;
		}
		BuildingQueuesWorldData
			.get(DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID))
				.addToList(blockData, false);
		BuildingReference.cityPreparationActive = true;
	}
	
	public static void enqeueBlockForDestroy(BlockPos pos) {
		enqeueBlockForDestroy(new BlockData(pos, Blocks.AIR.getDefaultState()));
	}
}
