package org.twittercity.twittercitymod.city;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// This class will manipulate the chunks our cities will built on (flatten the world etc)
// A chunk is minecraft term and represents an area of 16 x 16 blocks
public class ChunksEditor {
	
	public static void editCityChunks(World world, City city) {
		for(int x = 0; x < city.getCityLength() / 16; x++) {
			for (int z = 0; z < city.getCityLength() / 16; z++) {
				
				makeChunkFlat(world, city, x * 16, z * 16);
			}
		}

	}

	private static void makeChunkFlat(World world, City city, int chunkXi, int chunkZi) {
		for(int x = chunkXi + 0; x < chunkXi + 16; x++) {
			for(int z = chunkZi + 0; z < chunkZi + 16; z++) {
				for(int y = 63; y < 64; y++) {
					world.setBlockState(new BlockPos(city.getBlockStart() + x, y, city.getBlockStart() + z), city.getGroundBlock().getDefaultState());
				}

				for(int y = 64; y <= 128; y++) {
					if(world.getBlockState(new BlockPos(city.getBlockStart() + x, y, city.getBlockStart() + z)).getBlock() != Blocks.AIR) {
						world.destroyBlock(new BlockPos(city.getBlockStart() + x, y, city.getBlockStart() + z), false);
					}
				}
			}
		}
	}
	
}
