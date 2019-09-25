package org.twittercity.twittercitymod.blocks;

import java.awt.Color;
import java.util.Random;

import org.twittercity.twittercitymod.config.ConfigurationManager;
import org.twittercity.twittercitymod.util.RandomHelper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class TCBlockColor implements IBlockColor {

    public static final IBlockColor INSTANCE = new TCBlockColor();
    public static Random rand = new Random();
	
	
	@Override
	public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
		if (ConfigurationManager.buildingOptions.coloredBlocks.isEnabled()) {
			return new Color(RandomHelper.nextInt(0, 255),RandomHelper.nextInt(0, 255),RandomHelper.nextInt(0, 255),0).getRGB();	
		}
		return -1;
	}
	
    /**
     * Register block colors.
     */
    public static void registerBlockColors()
    {
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(INSTANCE, TCBlocks.STONE);
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(INSTANCE, TCBlocks.WOOL);
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(INSTANCE, TCBlocks.PLANKS);
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(INSTANCE, TCBlocks.LOG);
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(INSTANCE, TCBlocks.LOG2);
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(INSTANCE, TCBlocks.COBBLESTONE);
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(INSTANCE, TCBlocks.MOSSY_COBBLESTONE);
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(INSTANCE, TCBlocks.STONEBRICK);
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(INSTANCE, TCBlocks.BRICK_BLOCK);
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(INSTANCE, TCBlocks.SANDSTONE);
    }
}
