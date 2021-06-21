package org.twittercity.twittercitymod.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.twittercity.twittercitymod.blocks.TCBlock;

public class TileEntityTwitter extends TileEntity{
	private int tweetID = 1;
	private Feeling feeling = Feeling.NO_FEELING;

	public TileEntityTwitter() {

	}

	public int getTweetID() {
		return this.tweetID;
	}
	
	public Feeling getFeeling() {
		return feeling;
	}
	
	public void setTileData(int tweetID, Feeling feeling) {
		this.tweetID = tweetID;
		this.feeling = feeling;
		markDirty();
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("tweetID")) {
			tweetID = compound.getInteger("tweetID");
		}
		int i = compound.getInteger("feeling");
		feeling = compound.hasKey("feeling") ?
				Feeling.forFeelingID(compound.getInteger("feeling")) : Feeling.NO_FEELING;
		int y = 0;
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("tweetID", tweetID);
		compound.setInteger("feeling", feeling.getFeelingID());

		return super.writeToNBT(compound);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket(){
		return new SPacketUpdateTileEntity(getPos(), 1, getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt){
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return !(newSate.getBlock() instanceof TCBlock);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}
}
