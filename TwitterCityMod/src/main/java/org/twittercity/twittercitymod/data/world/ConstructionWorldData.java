package org.twittercity.twittercitymod.data.world;

import org.twittercity.twittercitymod.Reference;
import org.twittercity.twittercitymod.city.ConstructionInfo;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class ConstructionWorldData extends WorldSavedData {

	private static final String DATA_NAME = Reference.MOD_ID + "_ConstructionData";
	private static ConstructionWorldData instance;

	private static ConstructionInfo cInfo;
	
	public ConstructionWorldData() {
		super(DATA_NAME);
	}
	
	public static ConstructionWorldData get(World world) {
        MapStorage storage = world.getMapStorage();
        instance = (ConstructionWorldData) storage.getOrLoadData(ConstructionWorldData.class, DATA_NAME);
        cInfo = ConstructionInfo.getInstance();
        if (instance == null) {
            instance = new ConstructionWorldData();
            storage.setData(DATA_NAME, instance);
        }
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
		cInfo.setCurrentConstructingCityId(currentConstructingCityId);
		this.markDirty();
	}


	public void setCurrentCityFinished(boolean isCurrentCityFinished) {
		cInfo.setCurrentCityFinished(isCurrentCityFinished);
		this.markDirty();
	}


	public void setCurrentBuildingId(int currentBuildingId) {
		cInfo.setCurrentBuildingId(currentBuildingId);
		this.markDirty();
	}

	public void setAreaArrayFirstLoopCounter(int areaArrayFirstLoopCounter) {
		cInfo.setAreaArrayFirstLoopCounter(areaArrayFirstLoopCounter);
		this.markDirty();
	}

	public void setAreaArraySecondLoopCounter(int areaArraySecondLoopCounter) {
		cInfo.setAreaArraySecondLoopCounter(areaArraySecondLoopCounter);
		this.markDirty();
	}

	public void setCurrentBuildingRotation(int currentBuildingRotation) {
		cInfo.setCurrentBuildingRotation(currentBuildingRotation);
		this.markDirty();
	}

	public void setCurrentCityBuildingsCount(int currentCityBuildingsCount) {
		cInfo.setCurrentCityBuildingsCount(currentCityBuildingsCount);
		this.markDirty();
	}
}
