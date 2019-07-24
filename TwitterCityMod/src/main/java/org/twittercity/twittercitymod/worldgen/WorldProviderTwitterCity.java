package org.twittercity.twittercitymod.worldgen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;

public class WorldProviderTwitterCity extends WorldProvider {

	@Override
	public DimensionType getDimensionType() {
		return TwitterCityWorldGen.DIM_TYPE;
	}
	
	@Override
	public boolean isSurfaceWorld() {
		return true;
	}

	@Override
	public boolean canDoLightning(Chunk chunk) {
		return true;
	}

	@Override
	public boolean canDoRainSnowIce(Chunk chunk) {
		return false;
	}

	@Override
	public boolean canSnowAt(BlockPos pos, boolean checkLight) {
		return false;
	}

	@Override
	public boolean canRespawnHere() {
		return true;
	}
}
