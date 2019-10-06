package org.twittercity.twittercitymod.city;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

/* Data object represents a city. Cities are always square. */
public class City {	
	// worldCities[CityID].x = RNG.Next(2, (MapChunksLength - worldCities[CityID].ChunkLength) - 1);
		// worldCities[CityID].z = RNG.Next(2, (MapChunksLength - worldCities[CityID].ChunkLength) - 1);
		
	private int id;
	// BlockPos object to the very first block of this city
	private BlockPos startingPos;
	
	// Size in chunks for one city size where the outside paths starts
	private int chunkLength;
	// Size in block for one city size where the outside paths starts
	private int cityLength;
	//City expansion at the end of X and Z axis
	private int edgeLength;
	// How many blocks will be skipped before start building.
	private int blockStart;
	
	// Size in blocks of the whole city's side (taking into consideration blockStart and edgeLength)
	private int mapLength;
	// How many the main roads paths will extend left and right
	// e.g Path has length 1 block by default, with pathExtends = 2 the main roads will have 
	// 3 blocks length.
	private int pathExtends;

	private Block groundBlock;
	private Block pathBlock;

	private boolean hasMainStreets;
	private boolean hasPaths;
	
	// Representation of the city in a 2D array
	private int[][] cityArea = null;
	private int firstDimSize = -1;
	private int secondDimSize = -1;
	
	// When buildings construction is finished but not city construction
	// we need to do the last things (make street lights, connect paths to roads) etc
	private boolean isBuildingsConstructionCompleted = false;
	private boolean isCityConstructionCompleted = false;

	public City(int id, BlockPos startingPos, int citySize, int edgeLenght, int pathExtends, Block groundBlock, Block pathBlock, boolean hasMainStreets, boolean hasPaths) {
		this.id = id;
		
		this.startingPos = startingPos;
		
		this.chunkLength = citySize;
		
		this.cityLength = citySize * 16;
		this.edgeLength = edgeLenght;
		this.mapLength = cityLength + edgeLenght + blockStart;

		this.pathExtends = pathExtends;
		
		this.hasMainStreets = hasMainStreets;
		this.hasPaths = hasPaths;

		this.groundBlock = groundBlock;
		this.pathBlock = pathBlock;
		
		this.blockStart = edgeLenght;

	}

	public City(NBTTagCompound nbt) {
		
		this.id = nbt.getInteger("id");
		this.startingPos = BlockPos.fromLong(nbt.getLong("startingPosLong"));

		this.cityLength = nbt.getInteger("cityLength");
		this.edgeLength = nbt.getInteger("edgeLength");
		this.mapLength = nbt.getInteger("mapLength");

		this.groundBlock = Block.getBlockById(nbt.getInteger("groundBlockID"));
		this.pathBlock = Block.getBlockById(nbt.getInteger("pathBlockID"));
		
		this.hasMainStreets = nbt.getBoolean("hasMainStreets");
		this.hasPaths = nbt.getBoolean("hasPaths");
		
		this.chunkLength = nbt.getInteger("chunkLength");
		
		this.blockStart = nbt.getInteger("blockStart");
		
		this.isBuildingsConstructionCompleted = nbt.getBoolean("isBuildingsConstructionCompleted");
		this.isCityConstructionCompleted = nbt.getBoolean("isCityConstructionCompleted");
		
		
		this.firstDimSize = nbt.hasKey("firstDimSize") ? nbt.getInteger("firstDimSize") : -1;
		this.secondDimSize = nbt.hasKey("secondDimSize") ? nbt.getInteger("secondDimSize") : -1;
		
		if(this.firstDimSize >= 0 && this.secondDimSize >= 0) {
			this.cityArea = new int[firstDimSize][secondDimSize];
			for (int i = 0; i < firstDimSize; i++) {
				for (int j = 0; j < secondDimSize; j++) {
					this.cityArea[i][j] = nbt.hasKey(i + "," + j) ? nbt.getInteger(i + "," + j) : 0;
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
		nbt.setLong("startingPosLong", this.startingPos.toLong());
		nbt.setInteger("chunkLength", this.chunkLength);
		nbt.setInteger("cityLength", this.cityLength);
		nbt.setInteger("edgeLength", this.edgeLength);
		nbt.setInteger("mapLength", this.mapLength);
		nbt.setBoolean("hasPaths", this.hasPaths);
		nbt.setInteger("groundBlockID", Block.getIdFromBlock(this.groundBlock));
		nbt.setInteger("pathBlockID", Block.getIdFromBlock(this.pathBlock));
		nbt.setBoolean("hasMainStreets", this.hasMainStreets);
		nbt.setBoolean("hasPaths", this.hasPaths);
		nbt.setInteger("blockStart", this.blockStart);
		
		nbt.setBoolean("isCityConstructionCompleted", this.isCityConstructionCompleted);
		nbt.setBoolean("isBuildingsConstructionCompleted", this.isBuildingsConstructionCompleted);
		
	
		
		if(cityArea != null ) {
			nbt.setInteger("firstDimSize", cityArea.length);
			nbt.setInteger("secondDimSize", cityArea[1].length);
			for (int i = 0; i < cityArea.length; i++) {
				for (int j = 0; j < cityArea[1].length; j++) {
					nbt.setInteger(i + "," + j, cityArea[i][j]);
				}
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
