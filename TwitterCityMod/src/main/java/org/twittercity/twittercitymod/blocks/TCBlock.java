package org.twittercity.twittercitymod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TCBlock extends Block {

	public TCBlock(Material material) {
		super(material);
	}

	public TCBlock(Material blockMaterialIn, MapColor blockMapColorIn) {
		super(blockMaterialIn, blockMaterialIn.getMaterialMapColor());
	}

	public TCBlock(String name, Material blockMaterialIn, SoundType sound) {
		super(blockMaterialIn);
		this.setRegistryName(name);
		this.setSoundType(sound);
	}

	public TCBlock(String name, Material blockMaterialIn, MapColor blockMapColorIn, SoundType sound) {
		super(blockMaterialIn, blockMapColorIn);
		this.setRegistryName(name);
		this.setSoundType(sound);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	// Called when the block is placed or loaded client side to get the tile entity
	// for the block
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		// return new TileEntityData();
		return null;
	}
}
