package org.twittercity.twittercitymod.data.world;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import org.twittercity.twittercitymod.city.EnumCityBuildDirection;

@Data
@NoArgsConstructor
public class ConstructionInfo {

    private int latestTweetID = 0;
    private int currentCityLength;
    private boolean isCurrentCityFinished;
    private int currentBuildingId, areaArrayFirstLoopCounter = 0, areaArraySecondLoopCounter = 0;
    private int currentBuildingRotation;
    private int currentCityBuildingsCount = 0;
    private BlockPos constructingBuildingBlockPos = null;
    private EnumCityBuildDirection buildDirection = null;
    private BlockPos citiesSquareNorthWestCorner = BlockPos.ORIGIN;

    public ConstructionInfo(NBTTagCompound nbt) {
        this.readFromNBT(nbt);
    }

//    private ConstructionInfo() {
//        this.isCurrentCityFinished = false;
//        this.currentBuildingId = -1;
//        this.areaArrayFirstLoopCounter = 0;
//        this.areaArraySecondLoopCounter = 0;
//        this.currentBuildingRotation = -1;
//        this.currentCityBuildingsCount = 0;
//        this.constructingBuildingBlockPos = null;
//        this.currentCityLength = 0;
//        this.latestTweetID = 0;
//    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {


        nbt.setBoolean("isCityFinished", this.isCurrentCityFinished);
        nbt.setInteger("currentBuildingID", this.currentBuildingId);
        nbt.setInteger("areaArrayFirstLoopCounter", this.areaArrayFirstLoopCounter);
        nbt.setInteger("areaArraySecondLoopCounter", this.areaArraySecondLoopCounter);
        nbt.setInteger("currentBuildingRotation", this.currentBuildingRotation);
        nbt.setInteger("currentCityBuildingsCount", this.currentCityBuildingsCount);
        nbt.setInteger("currentCityLength", this.currentCityLength);
        nbt.setInteger("latestTweetID", this.latestTweetID);
        nbt.setLong("constructingBuildingBlockPos", constructingBuildingBlockPos == null ?
                0 : this.constructingBuildingBlockPos.toLong());
        nbt.setLong("citiesSquareNorthWestCorner", citiesSquareNorthWestCorner == null ?
                0 : this.citiesSquareNorthWestCorner.toLong());
        if (this.buildDirection != null) {
            nbt.setInteger("buildDirectionIndex", this.buildDirection.getIndex());
        }

        return nbt;
    }

    private void readFromNBT(NBTTagCompound nbt) {

        this.isCurrentCityFinished = nbt.getBoolean("isCityFinished");
        this.currentBuildingId = nbt.getInteger("currentBuildingID");
        this.areaArrayFirstLoopCounter = nbt.getInteger("areaArrayFirstLoopCounter");
        this.areaArraySecondLoopCounter = nbt.getInteger("areaArraySecondLoopCounter");
        this.currentBuildingRotation = nbt.getInteger("currentBuildingRotation");
        this.currentCityBuildingsCount = nbt.getInteger("currentCityBuildingsCount");
        this.constructingBuildingBlockPos = BlockPos.fromLong(nbt.getLong("constructingBuildingBlockPos"));
        this.citiesSquareNorthWestCorner = BlockPos.fromLong(nbt.getLong("citiesSquareNorthWestCorner"));
        this.currentCityLength = nbt.getInteger("currentCityLength");
        this.latestTweetID = nbt.getInteger("latestTweetID");
        this.buildDirection = nbt.hasKey("buildDirectionIndex") ?
                EnumCityBuildDirection.getCityDirectionByIndex(nbt.getInteger("buildDirectionIndex")) : null;
    }

    public void increaseCurrentCityBuildingsCount() {
        currentCityBuildingsCount++;
    }

}
