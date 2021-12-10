package org.twittercity.twittercitymod.data.world;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import org.twittercity.twittercitymod.city.EnumCityBuildDirection;
import org.twittercity.twittercitymod.tickhandlers.ConstructionPriority;
import org.twittercity.twittercitymod.util.BlockData;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ConstructionInfo {

    private int latestTweetID = 0;
    private int currentCityLength;
    private boolean isCurrentCityFinished;
    private int currentBuildingId, areaArrayFirstLoopCounter = 0, areaArraySecondLoopCounter = 0;
    private int currentBuildingRotation;
    private int currentCityBuildingsCount = 0;
    private BlockPos constructingBuildingBlockPos;
    private EnumCityBuildDirection buildDirection;
    private BlockPos citiesSquareNorthWestCorner = BlockPos.ORIGIN;
    private List<BlockData> buildLast = new ArrayList<>();

    public ConstructionInfo(NBTTagCompound nbt) {
        this.readFromNBT(nbt);
    }

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

        NBTTagList buildLastTag = new NBTTagList();
        for(BlockData blockData : buildLast) {
            buildLastTag.appendTag((new BuildLastBlock(blockData.getBlockState(), blockData.getPos())).writeToNBT());
        }
        nbt.setTag("buildLastBlocks", buildLastTag);

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

        NBTTagList buildLastBlocks = (NBTTagList) nbt.getTag("buildLastBlocks");
        buildLast = buildLast == null ? new ArrayList<>() : buildLast;
        for(int i = 0; i < buildLastBlocks.tagCount(); i++) {
            BuildLastBlock buildLastBlock = new BuildLastBlock((NBTTagCompound) buildLastBlocks.get(i));
            this.buildLast.add(buildLastBlock.toBlockData());
        }
    }

    public void clearBuildLast() {
        buildLast.clear();
    }

    // Wrapper-helper class for information about blocks that needed to be build last
    private static class BuildLastBlock {
        private final IBlockState state;
        private final BlockPos pos;

        private BuildLastBlock(NBTTagCompound nbt) {
            this.state = Block.getStateById(nbt.getInteger("blockStateID"));
            this.pos = BlockPos.fromLong(nbt.getLong("blockPos"));
        }

        private BuildLastBlock(IBlockState state, BlockPos pos) {
            this.state = state;
            this.pos = pos;
        }

        private NBTTagCompound writeToNBT() {
            NBTTagCompound nbt = new NBTTagCompound();

            nbt.setInteger("blockStateID", Block.getStateId(state));
            nbt.setLong("blockPos", pos.toLong());
            return nbt;
        }

        private BlockData toBlockData() {
            return BlockData.builder().pos(pos).blockState(state)
                    .shouldNotifyNeighbors(false).flags(3).constructionPriority(ConstructionPriority.BUILD_LAST).build();
        }
    }
}
