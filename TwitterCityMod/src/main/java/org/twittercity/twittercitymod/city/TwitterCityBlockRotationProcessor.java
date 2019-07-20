package org.twittercity.twittercitymod.city;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;

public class TwitterCityBlockRotationProcessor implements ITwitterCityTemplateProcessor {
	private final float chance;
	private final Random random;

	public TwitterCityBlockRotationProcessor(BlockPos pos, PlacementSettings settings) {
		this.chance = settings.getIntegrity();
		this.random = settings.getRandom(pos);
	}

	@Nullable
	public TwitterCityTemplate.BlockInfo processBlock(World worldIn, BlockPos pos, TwitterCityTemplate.BlockInfo blockInfoIn) {
		return this.chance < 1.0F && this.random.nextFloat() > this.chance ? null : blockInfoIn;
	}
}
