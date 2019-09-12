package org.twittercity.twittercitymod.city;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

/* Data object represents a city */
public class City {

	private int id;
	// private int x;
	// private int z;
	BlockPos startingPos;
	private int chunkLength; // city_size in theme.xml and its used to calculate x and z
	// worldCities[CityID].x = RNG.Next(2, (MapChunksLength - worldCities[CityID].ChunkLength) - 1);
	// worldCities[CityID].z = RNG.Next(2, (MapChunksLength - worldCities[CityID].ChunkLength) - 1);

	private String outsideLightType;
	private String streetLightType;

	private int cityLength; // Vgainei random apo 5 mexri city_size sto xml kai meta pollaplasiazete me 16
	private int edgeLength;
	private int mapLength;
	private int pathExtends;

	private Block groundBlock;
	private Block pathBlock;

	private boolean hasMainStreets;
	private boolean hasPaths;

	private int blockStart = 0;

	int[][] cityArea = null;
	private int firstDimSize = -1;
	private int secondDimSize = -1;
	
	// When buildings construction is finished but not city construction
	// we need to do the last things (make street lights, connect paths to roads) etc
	private boolean isBuildingsConstructionCompleted = false;
	private boolean isCityConstructionCompleted = false;
	
	public City() {
		this.id = 0;

		this.outsideLightType = "";
		this.streetLightType = "";

		this.cityLength = 0 * 16;
		this.edgeLength = 0;
		this.mapLength = 0;

		this.hasMainStreets = true;
		this.hasPaths = true;

		this.groundBlock = Blocks.OBSIDIAN;
		this.pathBlock = Blocks.DIAMOND_BLOCK;

		this.blockStart = this.getEdgeLength() + 13;
	}

	public City(int id, String outsideLightType, String streetLightType, int cityLength, int edgeLenght, int mapLength,
			Block groundBlock, Block pathBlock) {
		this.id = id;

		this.outsideLightType = outsideLightType;
		this.streetLightType = streetLightType;

		this.cityLength = cityLength;
		this.edgeLength = edgeLenght;
		this.mapLength = mapLength;

		this.groundBlock = groundBlock;
		this.pathBlock = pathBlock;

	}

	public City(NBTTagCompound nbt) {
		
		this.id = nbt.getInteger("id");
		this.startingPos = new BlockPos(nbt.getInteger("startingPosX"), nbt.getInteger("startingPosY"), nbt.getInteger("startingPosZ"));
		
		this.outsideLightType = nbt.getString("outsideLightType");
		this.streetLightType = nbt.getString("streetLightType");

		this.cityLength = nbt.getInteger("cityLength");
		this.edgeLength = nbt.getInteger("edgeLength");
		this.mapLength = nbt.getInteger("mapLength");

		this.groundBlock = Block.getBlockFromName(nbt.getString("groundBlock"));
		this.pathBlock = Block.getBlockFromName(nbt.getString("pathBlock"));
		
		this.hasMainStreets = nbt.getBoolean("hasMainStreets");
		this.hasPaths = nbt.getBoolean("hasPaths");
		
		this.chunkLength = nbt.getInteger("chunkLength");
		
		this.blockStart = nbt.getInteger("blockStart");
		
		this.isBuildingsConstructionCompleted = nbt.getBoolean("isBuildingsConstructionCompleted");
		this.isCityConstructionCompleted = nbt.getBoolean("isCityConstructionCompleted");
		
		
		this.firstDimSize = nbt.hasKey("firstDimSize") ? nbt.getInteger("firstDimSize") : -1;
		this.secondDimSize = nbt.hasKey("secondDimSize") ? nbt.getInteger("secondDimSize") : -1;
		
		if(this.cityArea == null) {
			if(this.firstDimSize >= 0 && this.secondDimSize >= 0) {
				this.cityArea = new int[firstDimSize][secondDimSize];
				for (int i = 0; i < firstDimSize; i++) {
					for (int j = 0; j < secondDimSize; j++) {
						this.cityArea[i][j] = nbt.hasKey(i + "," + j) ? nbt.getInteger(i + "," + j) : 0;
					}
				}
			}
		}	
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setStartingPos(BlockPos startingPos) {
		this.startingPos = startingPos;
	}

	public BlockPos getStartingPos() {
		return startingPos;
	}

	public int getChunkLength() {
		return chunkLength;
	}

	public void setChunkLength(int chunkLength) {
		this.chunkLength = chunkLength;
	}

	public String getOutsideLightType() {
		return outsideLightType;
	}

	public void setOutsideLightType(String outsideLightType) {
		this.outsideLightType = outsideLightType;
	}

	public String getStreetLightType() {
		return streetLightType;
	}

	public void setStreetLightType(String streetLightType) {
		this.streetLightType = streetLightType;
	}

	public int getCityLength() {
		return cityLength;
	}

	public void setCityLength(int cityLength) {
		this.cityLength = cityLength * 16;
	}

	public int getEdgeLength() {
		return edgeLength;
	}

	public void setEdgeLength(int edgeLength) {
		this.edgeLength = edgeLength;
	}

	public int getMapLength() {
		return mapLength;
	}

	public void setMapLength(int mapLength) {
		this.mapLength = mapLength;
	}

	public int getBlockStart() {
		return blockStart;
	}

	public void setBlockStart(int blockStart) {
		this.blockStart = blockStart;
	}

	public Block getGroundBlock() {
		return groundBlock;
	}

	public Block getPathBlock() {
		return pathBlock;
	}

	public void setGroundBlock(Block groundBlock) {
		this.groundBlock = groundBlock;
	}

	public void setPathBlock(Block pathBlock) {
		this.pathBlock = pathBlock;
	}

	public boolean hasMainStreets() {
		return hasMainStreets;
	}

	public void setHasMainStreets(boolean hasMainStreets) {
		this.hasMainStreets = hasMainStreets;
	}

	public void hasPaths(boolean hasPaths) {

		this.hasPaths = hasPaths;
	}

	public boolean hasPaths() {

		return hasPaths;
	}
	
	public int getPathExtends() {
		return pathExtends;
	}

	public void setPathExtends(int pathExtends) {
		this.pathExtends = pathExtends;
	}
	
	public void setCityArea(int[][] area) {
		this.cityArea = area;
	}
	
	public int[][] getCityArea() {
		return this.cityArea;
	}

	public void setIsBuildingsConstructionCompleted(boolean itIs) {
		this.isBuildingsConstructionCompleted = itIs;
	}

	public boolean setIsBuildingsConstructionCompleted() {
		return this.isBuildingsConstructionCompleted;
	}
	
	public void setIsCityConstructionCompleted(boolean itIs) {
		this.isCityConstructionCompleted = itIs;
	}

	public boolean getIsCityConstructionCompleted() {
		return this.isCityConstructionCompleted;
	}
	
	public NBTTagCompound writeToNBT() {

		NBTTagCompound nbt = new NBTTagCompound();
		
		nbt.setInteger("id", this.id);
		nbt.setInteger("startingPosX", this.startingPos.getX());
		nbt.setInteger("startingPosY", this.startingPos.getY());
		nbt.setInteger("startingPosZ", this.startingPos.getZ());
		nbt.setInteger("chunkLength", this.chunkLength);
		nbt.setString("outsideLightType", this.outsideLightType);
		nbt.setString("streetLightType", this.streetLightType);
		nbt.setInteger("cityLength", this.cityLength);
		nbt.setInteger("edgeLength", this.edgeLength);
		nbt.setInteger("mapLength", this.mapLength);
		nbt.setBoolean("hasPaths", this.hasPaths);
		nbt.setString("groundBlock", this.groundBlock.getUnlocalizedName());
		nbt.setString("pathBlock", this.pathBlock.getUnlocalizedName());
		nbt.setBoolean("hasMainStreets", this.hasMainStreets);
		nbt.setBoolean("hasPaths", this.hasPaths);
		nbt.setInteger("blockStart", this.blockStart);
		
		nbt.setBoolean("isCityConstructionCompleted", this.isCityConstructionCompleted);
		nbt.setBoolean("isBuildingsConstructionCompleted", this.isBuildingsConstructionCompleted);
		
		nbt.setInteger("firstDimSize", this.firstDimSize);
		nbt.setInteger("secondDimSize", this.secondDimSize);
		for (int i = 0; i < cityArea.length; i++) {
			for (int j = 0; j < cityArea[1].length; j++) {
				nbt.setInteger(i + "," + j, cityArea[i][j]);
			}
		}
		
		return nbt;
	}

	public static City readFromNbt(NBTTagCompound nbt) {
		return new City(nbt);
	}

	@Override
	public String toString() {
		return "City id: " + id;
	}
}
