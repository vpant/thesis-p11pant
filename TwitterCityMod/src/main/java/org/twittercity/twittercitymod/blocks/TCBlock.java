package org.twittercity.twittercitymod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TCBlock extends Block {

	public TCBlock(Material material) {
		super(material);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	// Called when the block is placed or loaded client side to get the tile entity for the block
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		//return new TileEntityData();
		return null;
	}
}
