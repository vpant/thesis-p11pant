package org.twittercity.twittercitymod.city;

import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.tickhandlers.ConstructionPriority;
import org.twittercity.twittercitymod.util.BlockData;
import org.twittercity.twittercitymod.util.BlockHelper;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// This class will manipulate the chunks our cities will built on (flatten the world etc)
public class ChunkEditor {
	private static void makeChunkFlat(World world, Block baseBlock, int chunkSourceX,int chunkSourceY, int chunkSourceZ, int cityId) {
		makeAreaFlat(world, baseBlock, chunkSourceX, chunkSourceY, chunkSourceZ, 16, 16, cityId);
	}
	
	private static void makeAreaFlat(World world, Block baseBlock, int sourceX, int sourceY, int sourceZ, int endX, int endZ, int cityId) {
		TwitterCity.logger.info("MAKING FLAT CITY AT {}, {}, {}, {}, {}", sourceX, sourceY, sourceZ, endX, endZ);
		for(int x = sourceX; x <= sourceX + endX; x++) {
			for(int z = sourceZ; z <= sourceZ + endZ; z++) {
				for(int y = sourceY; y <= sourceY; y++) {
					BlockData bd = new BlockData(new BlockPos(x, y, z), baseBlock.getDefaultState(), ConstructionPriority.BUILD_FIRST, cityId);
					BlockHelper.spawnOrEnqueue(bd, world);
				}

				for(int y = sourceY + 1; y <= 128; y++) {
					if(world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.AIR) {
						BlockData bd = new BlockData(new BlockPos(x, y, z), baseBlock.getDefaultState(), ConstructionPriority.BUILD_FIRST, cityId);
						BlockHelper.destroyOrEnqueue(bd, world);
					}
				}
			}
		}
	}
	
	private static void makeAreaFlat(World world, Block baseBlock, BlockPos startingPos, int endXandZ, int cityId) {
		makeAreaFlat(world, baseBlock, startingPos.getX(), startingPos.getY(), startingPos.getZ(), endXandZ, endXandZ, cityId);
	}
	
	/**
	 * Make as many chunks necessary flat to fit the city
	 * @param world
	 * @param city
	 */
	public static void makeFlatAreaForCity(World world, City city) {
		makeAreaFlat(world, city.getGroundBlock(), city.getStartingPos(), city.getCityLength(), city.getId());
	}
	
	
	/**
	 * Flattens the world starting at the specified coordinates (chunkSourceX, chunkSourceZ) and for numberOfChunks in X and Z axis
	 * @param world
	 * @param baseBlock
	 * @param chunkSourceX
	 * @param chunkSourceZ
	 * @param numberOfChunks
	 */
	public static void makeChunksFlat(World world, Block baseBlock, int chunkSourceX, int chunkSourceY, int chunkSourceZ, int numberOfChunks, int cityId) {
		for(int x = 0; x <= numberOfChunks; x++) {
			for (int z = 0; z <=  numberOfChunks; z++) {
				makeChunkFlat(world, baseBlock, chunkSourceX + (x * 16), chunkSourceY, chunkSourceZ + (z * 16), cityId);
			}
		}
	}
}
