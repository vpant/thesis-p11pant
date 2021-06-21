package org.twittercity.twittercitymod.util;

import org.twittercity.twittercitymod.data.db.Tweet;
import org.twittercity.twittercitymod.tickhandlers.ConstructionPriority;
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
	/** ConstructionPriority of this block */
	public ConstructionPriority constructionPriority;
	/** City ID the block belongs to */
	public Integer cityId;
	/** The tweet that holds this block */
	public final Tweet tweet;

	public BlockData(BlockPos posIn,
			IBlockState stateIn,
			int flags,
			boolean shouldNotifyNeighbors,
			ConstructionPriority constructionPriority,
			int cityId,
			Tweet tweet) {

		this.pos = posIn;
		this.blockState = stateIn;
		this.flags = flags;
		this.shouldNotifyNeighbors = shouldNotifyNeighbors;
		this.constructionPriority = constructionPriority;
		this.cityId = cityId;
		this.tweet = tweet;
	}
	
	public BlockData(NBTTagCompound nbt) {
		this.pos = nbt.hasKey("blockPosLong") ? BlockPos.fromLong(nbt.getLong("blockPosLong")) : BlockPos.ORIGIN;
		this.blockState = Block.getStateById(nbt.getInteger("blockStateID"));
		this.flags = nbt.hasKey("flags") ? nbt.getInteger("flags") : 3;
		this.shouldNotifyNeighbors = nbt.hasKey("notifyNeighbors") ? nbt.getBoolean("notifyNeighbors") : false;
		
		this.constructionPriority = ConstructionPriority.forID(nbt.getInteger("constructionPriority"));
		this.cityId = nbt.hasKey("cityId") ? nbt.getInteger("cityId") : 0;

		int id = nbt.hasKey("tweetID") ? nbt.getInteger("tweetID") : -1;
		Feeling feeling = nbt.hasKey("tweetFeeling") ? Feeling.forFeelingID(nbt.getInteger("tweetFeeling")) : Feeling.NO_FEELING;
		
		this.tweet = id >= 0 ? new Tweet(id, feeling) : null;	
	}
	
	public BlockData(BlockPos posIn, IBlockState stateIn, ConstructionPriority constructionPriority, int cityId, Tweet tweet) {
		this(posIn, stateIn, 3, false, constructionPriority, cityId, tweet);
	}
	
	public BlockData(BlockPos posIn, IBlockState stateIn, ConstructionPriority constructionPriority, int cityId) {
		this(posIn, stateIn, false, constructionPriority, cityId);
	}
	
	public BlockData(BlockPos posIn, IBlockState stateIn, boolean shouldNotifyNeighbors, ConstructionPriority constructionPriority, int cityId) {
		this(posIn, stateIn, 3, shouldNotifyNeighbors,constructionPriority, cityId, null);
	}
	
	public BlockData(BlockPos posIn, IBlockState stateIn, int flags, ConstructionPriority constructionPriority, int cityId) {
		this(posIn, stateIn, flags, false, constructionPriority, cityId, null);
	}
	
	public BlockData(BlockPos posIn, IBlockState stateIn, int flags, boolean shouldNotifyNeighbors, ConstructionPriority constructionPriority, int cityId) {
		this(posIn, stateIn, flags, shouldNotifyNeighbors, constructionPriority, cityId, null);
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
		nbt.setInteger("constructionPriority", this.constructionPriority.getID());
		nbt.setInteger("cityId", this.cityId);

		return nbt;
	}

	@Override
	public String toString() {
		return "BlockData[pos: "+ pos.toString()+", blockState: ]" + blockState.toString();
	}
}
