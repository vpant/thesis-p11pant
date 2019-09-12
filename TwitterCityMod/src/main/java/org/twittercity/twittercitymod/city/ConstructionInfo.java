package org.twittercity.twittercitymod.city;

import java.util.ArrayList;

import org.twittercity.twittercitymod.util.BlockData;

import net.minecraft.nbt.NBTTagCompound;

public class ConstructionInfo {
	public int currentConstructingCityId;
	public boolean isCurrentCityFinished;
	public int currentBuildingId, areaArrayFirstLoopCounter, areaArraySecondLoopCounter;
	public int currentBuildingRotation;
	public int currentCityBuildingsCount = 0;
	
	public static ArrayList<BlockData> buildLast = new ArrayList<BlockData>();
	
	public ConstructionInfo(NBTTagCompound nbt) {
		this.currentConstructingCityId = nbt.getInteger("currentCityID");
		this.isCurrentCityFinished = nbt.getBoolean("isCityFinished");
		this.currentBuildingId = nbt.getInteger("currentBuildingID");
		this.areaArrayFirstLoopCounter = nbt.getInteger("areaArrayFirstLoopCounter");
		this.areaArraySecondLoopCounter = nbt.getInteger("areaArraySecondLoopCounter");
		this.currentBuildingRotation = nbt.getInteger("currentBuildingRotation");
		this.currentCityBuildingsCount = nbt.getInteger("currentCityBuildingsCount");
	}

	public ConstructionInfo() {
		this.currentConstructingCityId = -1;
		this.isCurrentCityFinished = false;
		this.currentBuildingId = -1;
		this.areaArrayFirstLoopCounter = -1;
		this.areaArraySecondLoopCounter = -1;
		this.currentBuildingRotation = -1;
		this.currentCityBuildingsCount = 0;
	}
	
	public void updateInfo(int cityId, int x, int z, int currentBuildingId, boolean isCurrentCityFinished) {
		//Mark dirty when implement writeToNBT
		this.currentConstructingCityId = cityId;
		this.areaArrayFirstLoopCounter = x;
		this.areaArraySecondLoopCounter = z;
		this.currentBuildingId = currentBuildingId;
	}
	
	public NBTTagCompound writeToNBT() {

		NBTTagCompound nbt = new NBTTagCompound();
		
		nbt.setInteger("currentCityID", this.currentConstructingCityId);
		nbt.setBoolean("isCityFinished", this.isCurrentCityFinished);
		nbt.setInteger("currentBuildingID", this.currentBuildingId);
		nbt.setInteger("areaArrayFirstLoopCounter", this.areaArrayFirstLoopCounter);
		nbt.setInteger("areaArraySecondLoopCounter", this.areaArraySecondLoopCounter);
		nbt.setInteger("currentBuildingRotation", this.currentBuildingRotation);
		nbt.setInteger("currentCityBuildingsCount", this.currentCityBuildingsCount);
	
		return nbt;
	}
	
	public static ConstructionInfo readFromNbt(NBTTagCompound nbt) {
		return new ConstructionInfo(nbt);
	}
}