package org.twittercity.twittercitymod.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityTwitter extends TileEntity{
	private int tweetID = 1;
	private Feeling feeling = Feeling.NO_FEELING;
	
	public int getTweetID() {
		return this.tweetID;
	}
	
	public Feeling getFeeling() {
		return feeling;
	}
	
	public void setTileData(int tweetID, Feeling feeling) {
		this.tweetID = tweetID;
		this.feeling = feeling;
		this.markDirty();
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if(compound.hasKey("tweetID")) {
			tweetID = compound.getInteger("tweetID");
		}
		feeling = compound.hasKey("feeling") ? 
				Feeling.forFeelingID(compound.getInteger("feeling")) : Feeling.NO_FEELING;

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("tweetID", tweetID);
		compound.setInteger("feeling", feeling.getFeelingID());
		return compound;
	}
}
