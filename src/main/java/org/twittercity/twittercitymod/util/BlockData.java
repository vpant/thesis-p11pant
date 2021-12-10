package org.twittercity.twittercitymod.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import org.twittercity.twittercitymod.data.db.Tweet;
import org.twittercity.twittercitymod.tickhandlers.ConstructionPriority;
import org.twittercity.twittercitymod.tileentity.Feeling;

/**
 * Data object to hold data for to be spawned block
 */
@Data
@AllArgsConstructor
@Builder
public class BlockData {

	/** the position the block is to be generated to */
	private final BlockPos pos;
	/** The type of block. */
	private final IBlockState blockState;
	/** Argument passed to World#setblockstate flags */
	private int flags;
	/** Some blocks(Beds etc.) need to notify neighbors upon spawning */
	private boolean shouldNotifyNeighbors;
	/** ConstructionPriority of this block */
	private ConstructionPriority constructionPriority;
	/** City ID the block belongs to */
	private Integer cityId;
	/** The tweet that holds this block */
	private final Tweet tweet;

	public BlockData(NBTTagCompound nbt) {
		pos = nbt.hasKey("blockPosLong") ? BlockPos.fromLong(nbt.getLong("blockPosLong")) : BlockPos.ORIGIN;
		blockState = Block.getStateById(nbt.getInteger("blockStateID"));
		flags = nbt.hasKey("flags") ? nbt.getInteger("flags") : 3;
		shouldNotifyNeighbors = nbt.hasKey("notifyNeighbors") ? nbt.getBoolean("notifyNeighbors") : false;
		
		constructionPriority = ConstructionPriority.forID(nbt.getInteger("constructionPriority"));
		cityId = nbt.hasKey("cityId") ? nbt.getInteger("cityId") : 0;

		int id = nbt.hasKey("tweetID") ? nbt.getInteger("tweetID") : -1;
		Feeling feeling = nbt.hasKey("tweetFeeling") ? Feeling.forFeelingID(nbt.getInteger("tweetFeeling")) : Feeling.NO_FEELING;
		
		tweet = id >= 0 ? new Tweet(id, feeling) : null;	
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

    public NBTTagCompound writeToNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		
		nbt.setLong("blockPosLong", pos.toLong());
		nbt.setInteger("blockStateID", Block.getStateId(blockState));
		nbt.setInteger("flags", flags);
		nbt.setBoolean("notifyNeighbors", shouldNotifyNeighbors);
		if(tweet != null) {
			nbt.setInteger("tweetID", tweet.getID());
			nbt.setInteger("tweetFeeling", tweet.getFeeling().getFeelingID());
		}
		nbt.setInteger("constructionPriority", constructionPriority.getID());
		nbt.setInteger("cityId", cityId);

		return nbt;
	}

	@Override
	public String toString() {
		return "BlockData[pos: "+ pos.toString()+", blockState: ]" + blockState.toString();
	}
}
