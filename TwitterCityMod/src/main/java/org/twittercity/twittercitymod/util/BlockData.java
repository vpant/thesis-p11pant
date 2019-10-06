package org.twittercity.twittercitymod.util;

import org.twittercity.twittercitymod.data.db.Tweet;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

/**
 * Data object to hold data for to be spawned block
 */
public class BlockData {

	/** the position the block is to be generated to */
	public final BlockPos pos;
	/** The type of block. */
	public final IBlockState blockState;
	/** Argument passed to World#setblockstate flags */
	public int flags;
	/** Some blocks(Beds etc.) need to notify neighbors upon spawning */
	public boolean shouldNotifyNeighbors;
	/** The tweet that holds this block */
	public Tweet tweet;

	public BlockData(BlockPos posIn, IBlockState stateIn, int flags, boolean shouldNotifyNeighbors, Tweet tweet) {
		this.pos = posIn;
		this.blockState = stateIn;
		this.flags = flags;
		this.shouldNotifyNeighbors = shouldNotifyNeighbors;
		this.tweet = tweet;
	}
	
	
	public BlockData(BlockPos posIn, IBlockState stateIn, Tweet tweet) {
		this(posIn, stateIn, 3, false, tweet);
	}
	
	public BlockData(BlockPos posIn, IBlockState stateIn) {
		this(posIn, stateIn, false);
	}
	
	public BlockData(BlockPos posIn, IBlockState stateIn, boolean shouldNotifyNeighbors) {
		this(posIn, stateIn, 3, shouldNotifyNeighbors, null);
	}
	
	public BlockData(BlockPos posIn, IBlockState stateIn, int flags) {
		this(posIn, stateIn, flags, false, null);
	}
	
	public BlockData(BlockPos posIn, IBlockState stateIn, int flags, boolean shouldNotifyNeighbors) {
		this(posIn, stateIn, flags, shouldNotifyNeighbors, null);
	}
}
