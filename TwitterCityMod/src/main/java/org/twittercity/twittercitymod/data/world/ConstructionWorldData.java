package org.twittercity.twittercitymod.data.world;

import org.twittercity.twittercitymod.Reference;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class ConstructionWorldData extends WorldSavedData {

	private static final String DATA_NAME = Reference.MOD_ID + "_ConstructionData";
	private static ConstructionWorldData instance = null;

	private static ConstructionInfo cInfo = null;
	
	public ConstructionWorldData() {
		super(DATA_NAME);
	}
	
	public static ConstructionWorldData get(World world) {
        MapStorage storage = world.getMapStorage();
        instance = (ConstructionWorldData) storage.getOrLoadData(ConstructionWorldData.class, DATA_NAME);
        if (instance == null) {
        	cInfo = new ConstructionWorldData.ConstructionInfo();
        	instance = new ConstructionWorldData();
            storage.setData(DATA_NAME, instance);
        }
        //cInfo = cInfo == null ? new ConstructionWorldData.ConstructionInfo() : cInfo;
        return instance;
    }
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		cInfo.readFromNBT(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		return cInfo.writeToNBT();
	}
	
	public void setCurrentConstructingCityId(int currentConstructingCityId) {
		cInfo.currentConstructingCityId = currentConstructingCityId;
		this.markDirty();
	}


	public void setCurrentCityFinished(boolean isCurrentCityFinished) {
		cInfo.isCurrentCityFinished = isCurrentCityFinished;
		this.markDirty();
	}


	public void setCurrentBuildingId(int currentBuildingId) {
		cInfo.currentBuildingId = currentBuildingId;
		this.markDirty();
	}

	public void setAreaArrayFirstLoopCounter(int areaArrayFirstLoopCounter) {
		cInfo.areaArrayFirstLoopCounter = areaArrayFirstLoopCounter;
		this.markDirty();
	}

	public void setAreaArraySecondLoopCounter(int areaArraySecondLoopCounter) {
		cInfo.areaArraySecondLoopCounter = areaArraySecondLoopCounter;
		this.markDirty();
	}

	public void setCurrentBuildingRotation(int currentBuildingRotation) {
		cInfo.currentBuildingRotation = currentBuildingRotation;
		this.markDirty();
	}

	public void increaseCurrentCityBuildingsCount() {
		cInfo.currentCityBuildingsCount++;
		this.markDirty();
	}
	
	public void setCurrentConstructingBlockPos(BlockPos pos) {
		cInfo.constructingBuildingBlockPos = pos;
		this.markDirty();
	}
	
	public BlockPos getConstructingBuildingBlockPos() {
		return cInfo.constructingBuildingBlockPos;
	}
	
	public int getCurrentConstructingCityId() {
		return cInfo.currentConstructingCityId;
	}

	public boolean isCurrentCityFinished() {
		return cInfo.isCurrentCityFinished;
	}

	public int getCurrentBuildingId() {
		return cInfo.currentBuildingId;
	}

	public int getAreaArrayFirstLoopCounter() {
		return cInfo.areaArrayFirstLoopCounter;
	}

	public int getAreaArraySecondLoopCounter() {
		return cInfo.areaArraySecondLoopCounter;
	}

	public int getCurrentBuildingRotation() {
		return cInfo.currentBuildingRotation;
	}

	public int getCurrentCityBuildingsCount() {
		return cInfo.currentCityBuildingsCount;
	}
	
	public void updateInfo(int cityId, int x, int z, int currentBuildingId, boolean isCurrentCityFinished) {
		//Mark dirty after this function call
		cInfo.currentConstructingCityId = cityId;
		cInfo.areaArrayFirstLoopCounter = x;
		cInfo.areaArraySecondLoopCounter = z;
		cInfo.currentBuildingId = currentBuildingId;
		//cInfo.constructingBuildingBlockPos = currentConstructingBlockPos;
		cInfo.isCurrentCityFinished = isCurrentCityFinished;
		this.markDirty();
	}
	
	@Override
	public String toString() {
		return "ConstructionWorldData: cityID: " + cInfo.currentConstructingCityId 
				+ " currentBuildingID: " + cInfo.currentBuildingId
				+ " currentBuildingRotation: " + cInfo.currentBuildingRotation
				+ " currentCityBuildingsCount: " + cInfo.currentCityBuildingsCount
				+ " areaArrayFirstLoopCounter: " + cInfo.areaArrayFirstLoopCounter
				+ " areaArraySecondLoopCounter: " + cInfo.areaArraySecondLoopCounter
				+ " constructingBuildingBlockPos: " + cInfo.constructingBuildingBlockPos.toString();
	}
	
	private static class ConstructionInfo {
	
		private int currentConstructingCityId;
		private boolean isCurrentCityFinished;
		private int currentBuildingId, areaArrayFirstLoopCounter, areaArraySecondLoopCounter;
		private int currentBuildingRotation;
		private int currentCityBuildingsCount = 0;

		private BlockPos constructingBuildingBlockPos;

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
		
		private NBTTagCompound writeToNBT() {

			NBTTagCompound nbt = new NBTTagCompound();
			
			nbt.setInteger("currentCityID", this.currentConstructingCityId);
			nbt.setBoolean("isCityFinished", this.isCurrentCityFinished);
			nbt.setInteger("currentBuildingID", this.currentBuildingId);
			nbt.setInteger("areaArrayFirstLoopCounter", this.areaArrayFirstLoopCounter);
			nbt.setInteger("areaArraySecondLoopCounter", this.areaArraySecondLoopCounter);
			nbt.setInteger("currentBuildingRotation", this.currentBuildingRotation);
			nbt.setInteger("currentCityBuildingsCount", this.currentCityBuildingsCount);
			nbt.setLong("constructingBuildingBlockPos", constructingBuildingBlockPos.toLong());
			
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
			this.constructingBuildingBlockPos = BlockPos.fromLong(nbt.getLong("constructingBuildingBlockPos"));
		}
	}
}
