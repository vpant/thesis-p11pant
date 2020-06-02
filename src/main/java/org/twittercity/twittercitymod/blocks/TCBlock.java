package org.twittercity.twittercitymod.blocks;

import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.concurrency.ExecutorProvider;
import org.twittercity.twittercitymod.concurrency.OpenTweetGuiRunnable;
import org.twittercity.twittercitymod.tileentity.TileEntityTwitter;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TCBlock extends Block {

	public TCBlock(Material material) {
		super(material);
		//this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
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

	/** Called when the block is placed or loaded client side to get the tile entity
	* for the block
	*/
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityTwitter();	
	}

	//Open tweet GUI
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity ent = worldIn.getTileEntity(pos);
		if (!worldIn.isRemote && ent instanceof TileEntityTwitter) {
			TwitterCity.proxy.openTweetLoadingGUI(((TileEntityTwitter)ent).getTweetID());
			ExecutorProvider.getExecutorService().execute(new OpenTweetGuiRunnable((TileEntityTwitter)ent));	
		}
		return true;
	}	
}
