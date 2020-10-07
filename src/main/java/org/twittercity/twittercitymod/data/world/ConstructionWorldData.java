package org.twittercity.twittercitymod.data.world;

import java.util.ArrayList;
import java.util.List;

import org.twittercity.twittercitymod.Reference;
import org.twittercity.twittercitymod.city.EnumCityBuildDirection;
import org.twittercity.twittercitymod.util.BlockData;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class ConstructionWorldData extends WorldSavedData {

	private static final String DATA_NAME = Reference.MOD_ID + "_ConstructionData";
	private static ConstructionWorldData instance;

	private static ConstructionInfo cInfo = null;
	
	public ConstructionWorldData() {
		super(DATA_NAME);
	}
	
	public ConstructionWorldData(String name) {
		super(name);
	}
	
	public static ConstructionWorldData get(World world) {
        MapStorage storage = world.getMapStorage();
        instance = (ConstructionWorldData) storage.getOrLoadData(ConstructionWorldData.class, DATA_NAME);
        if (instance == null) {
        	cInfo = new ConstructionWorldData.ConstructionInfo();
        	instance = new ConstructionWorldData();
            storage.setData(DATA_NAME, instance);
        }
        return instance;
    }
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		cInfo = new ConstructionWorldData.ConstructionInfo(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		return cInfo.writeToNBT();
	}
	
	public ConstructionWorldData setCurrentConstructingCityId(int currentConstructingCityId) {
		cInfo.currentConstructingCityId = currentConstructingCityId;
		this.markDirty();
		return this;
	}


	public ConstructionWorldData setCurrentCityFinished(boolean isCurrentCityFinished) {
		cInfo.isCurrentCityFinished = isCurrentCityFinished;
		this.markDirty();
		return this;
	}


	public ConstructionWorldData setCurrentBuildingId(int currentBuildingId) {
		cInfo.currentBuildingId = currentBuildingId;
		this.markDirty();
		return this;
	}

	public ConstructionWorldData setAreaArrayFirstLoopCounter(int areaArrayFirstLoopCounter) {
		cInfo.areaArrayFirstLoopCounter = areaArrayFirstLoopCounter;
		this.markDirty();
		return this;
	}

	public ConstructionWorldData setAreaArraySecondLoopCounter(int areaArraySecondLoopCounter) {
		cInfo.areaArraySecondLoopCounter = areaArraySecondLoopCounter;
		this.markDirty();
		return this;
	}

	public ConstructionWorldData setCurrentBuildingRotation(int currentBuildingRotation) {
		cInfo.currentBuildingRotation = currentBuildingRotation;
		this.markDirty();
		return this;
	}

	/**
	 * Sets the CityBuildDirection to the next index. If there is no CityBuildDirection 
	 * it is set EnumCityBuildingDirection.CENTER. If index is out of bound it is set to 
	 * EnumCityBuildingDirection.NORTH_WEST
	 */
	public ConstructionWorldData setNextCityDirection() {
		cInfo.buildDirection = EnumCityBuildDirection.getNextCityDirection(cInfo.buildDirection);
		this.markDirty();
		return this;
	}
	
	public ConstructionWorldData increaseCurrentCityBuildingsCount() {
		cInfo.currentCityBuildingsCount++;
		this.markDirty();
		return this;
	}
	
	public ConstructionWorldData setCurrentConstructingBlockPos(BlockPos pos) {
		cInfo.constructingBuildingBlockPos = pos;
		this.markDirty();
		return this;
	}
	
	public void setCityLength(int cityLength) {
		cInfo.currentCityLength	= cityLength;
		this.markDirty();
	}
	
	public void setCitiesSquareNorthWestCorner(BlockPos pos) {
		cInfo.citiesSquareNorthWestCorner = pos;
		this.markDirty();
	}
	
	public void setLatestTweetID(int id) {
		cInfo.latestTweetID = id;
	}

	public int getLatestTweetID() {
		return cInfo.latestTweetID;
	}
	
	public BlockPos getConstructingBuildingBlockPos() {
		return cInfo.constructingBuildingBlockPos;
	}
	
	public EnumCityBuildDirection getCityDirection() {
		return cInfo.buildDirection;
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
	
	public int getCityLength() {
		return cInfo.currentCityLength;
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
	
	public BlockPos getCitiesSquareNorthWestCorner() {
		return cInfo.citiesSquareNorthWestCorner;
	}
	
	public void addToBuildLastList(BlockData blockData) {
		cInfo.addToBuildLast(blockData);
		markDirty();
	}
	
	public void clearBuildLast() {
		cInfo.buildLast.clear();
		markDirty();
	}
	
	public List<BlockData> getBuildLast() {
		return cInfo.buildLast;
	}
	
	public void updateInfo(int cityId, int x, int z, int currentBuildingId, boolean isCurrentCityFinished) {
		//Mark dirty after this function call
		cInfo.currentConstructingCityId = cityId;
		cInfo.areaArrayFirstLoopCounter = x;
		cInfo.areaArraySecondLoopCounter = z;
		cInfo.currentBuildingId = currentBuildingId;
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
	
		private int latestTweetID = 0;
		private int currentCityLength;
		private int currentConstructingCityId;
		private boolean isCurrentCityFinished;
		private int currentBuildingId, areaArrayFirstLoopCounter = 0, areaArraySecondLoopCounter = 0;
		private int currentBuildingRotation;
		private int currentCityBuildingsCount = 0;
		private BlockPos constructingBuildingBlockPos = null;
		private EnumCityBuildDirection buildDirection = null;
		private BlockPos citiesSquareNorthWestCorner = BlockPos.ORIGIN;
		public List<BlockData> buildLast;

		private ConstructionInfo(NBTTagCompound nbt) {
			this.readFromNBT(nbt);
		}

		private ConstructionInfo() {
			this.currentConstructingCityId = -1;
			this.isCurrentCityFinished = false;
			this.currentBuildingId = -1;
			this.areaArrayFirstLoopCounter = 0;
			this.areaArraySecondLoopCounter = 0;
			this.currentBuildingRotation = -1;
			this.currentCityBuildingsCount = 0;
			this.constructingBuildingBlockPos = null;
			this.currentCityLength = 0;
			this.buildLast = new ArrayList<>();
			this.latestTweetID = 0;
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
			nbt.setInteger("currentCityLength", this.currentCityLength);
			nbt.setInteger("latestTweetID", this.latestTweetID);
			nbt.setLong("constructingBuildingBlockPos", constructingBuildingBlockPos == null ? 
					0 : this.constructingBuildingBlockPos.toLong());
			nbt.setLong("citiesSquareNorthWestCorner", citiesSquareNorthWestCorner == null ? 
					0 : this.citiesSquareNorthWestCorner.toLong());
			if(this.buildDirection != null) {
				nbt.setInteger("buildDirectionIndex", this.buildDirection.getIndex());
			}
			
			NBTTagList buildLastTag = new NBTTagList();
			for(BlockData blockData : buildLast) {
				buildLastTag.appendTag((new BuildLastBlock(blockData.blockState, blockData.pos)).writeToNBT());
			}
			
			nbt.setTag("buildLastBlocks", buildLastTag);
			return nbt;
		}
		
		private void readFromNBT(NBTTagCompound nbt) {
			
			this.currentConstructingCityId = nbt.getInteger("currentCityID");
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
			buildLast = buildLast == null ? new ArrayList<BlockData>() : buildLast;
			for(int i = 0; i < buildLastBlocks.tagCount(); i++) {
				BuildLastBlock buildLastBlock = new BuildLastBlock((NBTTagCompound) buildLastBlocks.get(i));
				this.buildLast.add(new BlockData(buildLastBlock.pos, buildLastBlock.state));
			}
		}
		
		private void addToBuildLast(BlockData blockData) {
			buildLast.add(blockData);
		}
		
		// Wrapper-helper class for information about blocks that needed to be build last
		private static class BuildLastBlock {
			private IBlockState state;
			private BlockPos pos;
			
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
		}
		
	}
}

