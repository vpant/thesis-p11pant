package org.twittercity.twittercitymod.city;

import java.util.ArrayList;

import org.twittercity.twittercitymod.util.BlockData;

import net.minecraft.nbt.NBTTagCompound;

public class ConstructionInfo {
	
	private static ConstructionInfo instance = null;
	
	private int currentConstructingCityId;
	private boolean isCurrentCityFinished;
	private int currentBuildingId, areaArrayFirstLoopCounter, areaArraySecondLoopCounter;
	private int currentBuildingRotation;
	private int currentCityBuildingsCount = 0;
	
	// Check BlockPos#toLong and BlockPos#fromLong to serialize building current construciting BlockPos
	// Should we encapsulate these fields to an inner class for organizations purposes?
	//private long constructingBuildingBlockPos;
	//private int constructingBuildinctRotation;
	//
	
	// This a helper list and does not need to be saved hence why it is static
	public static ArrayList<BlockData> buildLast = new ArrayList<BlockData>();
	
	private ConstructionInfo(NBTTagCompound nbt) {
		this.readFromNBT(nbt);
	}

	private ConstructionInfo() {
		this.currentConstructingCityId = -1;
		this.isCurrentCityFinished = false;
		this.currentBuildingId = -1;
		this.areaArrayFirstLoopCounter = -1;
		this.areaArraySecondLoopCounter = -1;
		this.currentBuildingRotation = -1;
		this.currentCityBuildingsCount = 0;
	}
	
	public void updateInfo(int cityId, int x, int z, int currentBuildingId, boolean isCurrentCityFinished) {
		//Mark dirty after this function call
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
	
	public void readFromNBT(NBTTagCompound nbt) {
		
		this.currentConstructingCityId = nbt.getInteger("currentCityID");
		this.isCurrentCityFinished = nbt.getBoolean("isCityFinished");
		this.currentBuildingId = nbt.getInteger("currentBuildingID");
		this.areaArrayFirstLoopCounter = nbt.getInteger("areaArrayFirstLoopCounter");
		this.areaArraySecondLoopCounter = nbt.getInteger("areaArraySecondLoopCounter");
		this.currentBuildingRotation = nbt.getInteger("currentBuildingRotation");
		this.currentCityBuildingsCount = nbt.getInteger("currentCityBuildingsCount");
	}
	
	public static ConstructionInfo getInstance() {
		return (instance != null) ? instance : new ConstructionInfo();
	}

	public int getCurrentConstructingCityId() {
		return currentConstructingCityId;
	}

	public void setCurrentConstructingCityId(int currentConstructingCityId) {
		this.currentConstructingCityId = currentConstructingCityId;
	}

	public boolean isCurrentCityFinished() {
		return isCurrentCityFinished;
	}

	public void setCurrentCityFinished(boolean isCurrentCityFinished) {
		this.isCurrentCityFinished = isCurrentCityFinished;
	}

	public int getCurrentBuildingId() {
		return currentBuildingId;
	}

	public void setCurrentBuildingId(int currentBuildingId) {
		this.currentBuildingId = currentBuildingId;
	}

	public int getAreaArrayFirstLoopCounter() {
		return areaArrayFirstLoopCounter;
	}

	public void setAreaArrayFirstLoopCounter(int areaArrayFirstLoopCounter) {
		this.areaArrayFirstLoopCounter = areaArrayFirstLoopCounter;
	}

	public int getAreaArraySecondLoopCounter() {
		return areaArraySecondLoopCounter;
	}

	public void setAreaArraySecondLoopCounter(int areaArraySecondLoopCounter) {
		this.areaArraySecondLoopCounter = areaArraySecondLoopCounter;
	}

	public int getCurrentBuildingRotation() {
		return currentBuildingRotation;
	}

	public void setCurrentBuildingRotation(int currentBuildingRotation) {
		this.currentBuildingRotation = currentBuildingRotation;
	}

	public int getCurrentCityBuildingsCount() {
		return currentCityBuildingsCount;
	}

	public void setCurrentCityBuildingsCount(int currentCityBuildingsCount) {
		this.currentCityBuildingsCount = currentCityBuildingsCount;
	}
}