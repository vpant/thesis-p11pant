package org.twittercity.twittercitymod.worldgen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;


public class WorldProviderTwitterCity extends WorldProviderSurface {

	@Override
	public DimensionType getDimensionType() {
		return TwitterCityWorldGenReference.DIM_TYPE;
	}

	
	
	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkGeneratorTwitterCity(this.world,this.world.getSeed(),this.world.getWorldInfo().isMapFeaturesEnabled(),this.world.getWorldInfo().getGeneratorOptions());
	}

	
	
	@Override
	protected void init() {
		this.hasSkyLight = true;
        this.biomeProvider = new BiomeProviderTwitterCity(); // This is a reason for a single biome world
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
