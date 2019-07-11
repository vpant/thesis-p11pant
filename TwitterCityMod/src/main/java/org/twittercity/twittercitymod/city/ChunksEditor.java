package org.twittercity.twittercitymod.city;

import org.twittercity.twittercitymod.TwitterCity;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// This class will manipulate the chunks our cities will built on (flatten the world etc)
// A chunk is minecraft term and represents an area of 16 x 16 blocks
public class ChunksEditor {
	private static void makeChunkFlat(World world, Block baseBlock, int chunkSourceX, int chunkSourceZ) {
		for(int x = chunkSourceX; x < chunkSourceX + 16; x++) {
			for(int z = chunkSourceZ; z < chunkSourceZ + 16; z++) {
				for(int y = 63; y < 64; y++) {
					world.setBlockState(new BlockPos(x, y, z), baseBlock.getDefaultState());
				}

				for(int y = 64; y <= 128; y++) {
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
		TwitterCity.logger.info("X is: " + city.getX() + "and Z is: " + city.getZ());
		makeChunksFlat(world, city.getGroundBlock(), city.getX(), city.getZ(), (city.getCityLength() / 16));
	}
	
	
	/**
	 * Flattens the world in specified coordinates (chunkSourceX, chunkSourceZ) and for numberOfChunks in X and Z axis
	 * @param world
	 * @param baseBlock
	 * @param chunkSourceX
	 * @param chunkSourceZ
	 * @param numberOfChunks
	 */
	public static void makeChunksFlat(World world, Block baseBlock, int chunkSourceX, int chunkSourceZ, int numberOfChunks) {
		for(int x = 0; x <= numberOfChunks; x++) {
			for (int z = 0; z <=  numberOfChunks; z++) {
				makeChunkFlat(world, baseBlock, chunkSourceX + (x * 16), chunkSourceZ + (z * 16));
			}
		}
	}	
}
