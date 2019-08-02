package org.twittercity.twittercitymod.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityTwitter extends TileEntity{
	private int tweetID = -1;
	
	public int getTweetID() {
		return this.tweetID;
	}
	
	public void setTweetID(int tweetID) {
		this.tweetID = tweetID;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if(compound.hasKey("tweetID")) {
			tweetID = compound.getInteger("tweetID");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("tweetID", tweetID);
		
		return compound;
	}
}
