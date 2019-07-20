package org.twittercity.twittercitymod.city;

import javax.annotation.Nullable;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ITwitterCityTemplateProcessor {
	@Nullable
    TwitterCityTemplate.BlockInfo processBlock(World worldIn, BlockPos pos, TwitterCityTemplate.BlockInfo blockInfoIn);
}
