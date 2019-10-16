package org.twittercity.twittercitymod.city.lazyblockspawn;

import java.util.List;

import org.twittercity.twittercitymod.util.BlockData;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class LazyBlockSpawnQueue {
	
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
		LazyBlockSpawnReference.toSpawn.add(blockData);
		LazyBlockSpawnReference.startingSize = LazyBlockSpawnReference.toSpawn.size();
	}
	
	public static void enqeueBlockListForSpawn(List<BlockData> blockList) {
		if(blockList.isEmpty()) {
			return;
		}
		LazyBlockSpawnReference.toSpawn.addAll(blockList);
		LazyBlockSpawnReference.startingSize = LazyBlockSpawnReference.toSpawn.size();
	}
	
	public static void enqeueBlockForDestroy(BlockData blockData) {
		if(blockData == null) {
			return;
		}
		LazyBlockDestroyReference.toDestroy.add(blockData);
		LazyBlockDestroyReference.destroyStartingSize = LazyBlockDestroyReference.toDestroy.size();
		LazyBlockDestroyReference.destroyInProgress = true;
	}
	
	public static void enqeueBlockForDestroy(BlockPos pos) {
		enqeueBlockForDestroy(new BlockData(pos, Blocks.AIR.getDefaultState()));
	}
}
