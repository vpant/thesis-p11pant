package org.twittercity.twittercitymod.city;

import org.twittercity.twittercitymod.util.BlockHelper;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// This class will manipulate the chunks our cities will built on (flatten the world etc)
public class ChunkEditor {
	private static void makeChunkFlat(World world, Block baseBlock, int chunkSourceX,int chunkSourceY, int chunkSourceZ) {
		makeAreaFlat(world, baseBlock, chunkSourceX, chunkSourceY, chunkSourceZ, 16, 16);
	}
	
	private static void makeAreaFlat(World world, Block baseBlock, int sourceX, int sourceY, int sourceZ, int endX, int endZ) {
		for(int x = sourceX; x <= sourceX + endX; x++) {
			for(int z = sourceZ; z <= sourceZ + endZ; z++) {
				for(int y = sourceY; y <= sourceY; y++) {
					BlockHelper.spawnOrEnqueue(new BlockPos(x, y, z), baseBlock.getDefaultState(), world);
				}

				for(int y = sourceY + 1; y <= 128; y++) {
					if(world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.AIR) {
						BlockHelper.destroyOrEnqueue(new BlockPos(x, y, z), world);
					}
				}
			}
		}
	}
	
	private static void makeAreaFlat(World world, Block baseBlock, BlockPos startingPos, int endXandZ) {
		makeAreaFlat(world, baseBlock, startingPos.getX(), startingPos.getY(), startingPos.getZ(), endXandZ, endXandZ);
	}
	
	/**
	 * Make as many chunks necessary flat to fit the city
	 * @param world
	 * @param city
	 */
	public static void makeFlatAreaForCity(World world, City city) {
		makeAreaFlat(world, city.getGroundBlock(), city.getStartingPos(), city.getCityLength());
	}
	
	
	/**
	 * Flattens the world starting at the specified coordinates (chunkSourceX, chunkSourceZ) and for numberOfChunks in X and Z axis
	 * @param world
	 * @param baseBlock
	 * @param chunkSourceX
	 * @param chunkSourceZ
	 * @param numberOfChunks
	 */
	public static void makeChunksFlat(World world, Block baseBlock, int chunkSourceX, int chunkSourceY, int chunkSourceZ, int numberOfChunks) {
		for(int x = 0; x <= numberOfChunks; x++) {
			for (int z = 0; z <=  numberOfChunks; z++) {
				makeChunkFlat(world, baseBlock, chunkSourceX + (x * 16), chunkSourceY, chunkSourceZ + (z * 16));
			}
		}
	}
}
