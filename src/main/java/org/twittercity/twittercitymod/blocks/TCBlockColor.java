package org.twittercity.twittercitymod.blocks;

import java.awt.Color;

import org.twittercity.twittercitymod.config.ConfigurationManager;
import org.twittercity.twittercitymod.tileentity.TileEntityTwitter;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class TCBlockColor implements IBlockColor {

    private static final IBlockColor INSTANCE = new TCBlockColor();
	
	@Override
	public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
		if (ConfigurationManager.buildingOptions.coloredBlocks.isEnabled()) {
			TileEntity ent = worldIn.getTileEntity(pos);;
			if (ent instanceof TileEntityTwitter) {
				return new Color(((TileEntityTwitter) ent).getFeeling().getFeelingColor()).getRGB();
			}	
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
