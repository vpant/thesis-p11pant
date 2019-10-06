package org.twittercity.twittercitymod.city;

import org.twittercity.twittercitymod.TwitterCity;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// This class will manipulate the chunks our cities will built on (flatten the world etc)
public class ChunkEditor {
	private static void makeChunkFlat(World world, Block baseBlock, int chunkSourceX,int chunkSourceY, int chunkSourceZ) {
		for(int x = chunkSourceX; x <= chunkSourceX + 16; x++) {
			for(int z = chunkSourceZ; z <= chunkSourceZ + 16; z++) {
				for(int y = chunkSourceY; y <= chunkSourceY; y++) {
					world.setBlockState(new BlockPos(x, y, z), baseBlock.getDefaultState());
				}

				for(int y = chunkSourceY + 1; y <= 128; y++) {
					if(world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.AIR) {
						world.destroyBlock(new BlockPos(x, y, z), false);
					}
				}
			}
		}
	}
	
	/**
	 * Make as many chunks necessary flat to fit the city
	 * @param world
	 * @param city
	 */
	public static void makeFlatChunksForCity(World world, City city) {
		TwitterCity.logger.info("X is: {}, Y is: {} and Z is: {}", city.getStartingPos().getX(),city.getStartingPos().getY(), city.getStartingPos().getZ());
		makeChunksFlat(world, city.getGroundBlock(), city.getStartingPos().getX(), city.getStartingPos().getY(), city.getStartingPos().getZ(), city.getChunkLength());
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
