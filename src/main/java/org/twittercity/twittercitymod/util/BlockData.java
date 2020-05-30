package org.twittercity.twittercitymod.util;

import org.twittercity.twittercitymod.data.db.Tweet;
import org.twittercity.twittercitymod.tileentity.Feeling;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
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
	public final Tweet tweet;

	public BlockData(BlockPos posIn, IBlockState stateIn, int flags, boolean shouldNotifyNeighbors, Tweet tweet) {
		this.pos = posIn;
		this.blockState = stateIn;
		this.flags = flags;
		this.shouldNotifyNeighbors = shouldNotifyNeighbors;
		this.tweet = tweet;
	}
	
	public BlockData(NBTTagCompound nbt) {
		this.pos = nbt.hasKey("blockPosLong") ? BlockPos.fromLong(nbt.getLong("blockPosLong")) : BlockPos.ORIGIN;
		this.blockState = Block.getStateById(nbt.getInteger("blockStateID"));
		this.flags = nbt.hasKey("flags") ? nbt.getInteger("flags") : 3;
		this.shouldNotifyNeighbors = nbt.hasKey("notifyNeighbors") ? nbt.getBoolean("notifyNeighbors") : false;
		
		int id = nbt.hasKey("tweetID") ? nbt.getInteger("tweetID") : -1;
		Feeling feeling = nbt.hasKey("tweetFeeling") ? Feeling.forFeelingID(nbt.getInteger("tweetFeeling")) : Feeling.NO_FEELING;
		
		this.tweet = id >= 0 ? new Tweet(id, feeling) : null;	
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
	
	public NBTTagCompound writeToNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		
		nbt.setLong("blockPosLong", this.pos.toLong());
		nbt.setInteger("blockStateID", Block.getStateId(this.blockState));
		nbt.setInteger("flags", this.flags);
		nbt.setBoolean("notifyNeighbors", this.shouldNotifyNeighbors);
		if(this.tweet != null) {
			nbt.setInteger("tweetID", this.tweet.getID());
			nbt.setInteger("tweetFeeling", this.tweet.getFeeling().getFeelingID());
		}
		
		return nbt;
	}
	
}
