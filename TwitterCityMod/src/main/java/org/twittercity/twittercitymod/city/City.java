package org.twittercity.twittercitymod.city;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

/* Data object represents a city */
public class City {
	
	private int id;
	private int x;
	private int z;
	private int chunkLength; // city_size in theme.xml and its used to calculate x and z
	//worldCities[CityID].x = RNG.Next(2, (MapChunksLength - worldCities[CityID].ChunkLength) - 1);
    //worldCities[CityID].z = RNG.Next(2, (MapChunksLength - worldCities[CityID].ChunkLength) - 1);
	
	
	private String outsideLightType;
	private String streetLightType;
	
	private int cityLength; // Vgainei random apo 5 mexri city_size sto xml kai meta pollaplasiazete me 16
	private int edgeLenght;
	private int mapLength;
	private int pathExtends;
	
	// Blocks. For now int ID and not Block object
	private Block groundBlock;
	private Block pathBlock;
	
	private boolean hasMainStreets;
	private boolean hasPaths;
	
	private int blockStart = 0;
	
	public int getPathExtends() {
		return pathExtends;
	}

	public void setPathExtends(int pathExtends) {
		this.pathExtends = pathExtends;
	}
	
	public City(){
		this.id = 0;
		
		this.outsideLightType = "";
		this.streetLightType = "";
		
		this.cityLength = 0;
		this.edgeLenght = 0;
		this.mapLength = 0;
		
		this.hasMainStreets = true;
		this.hasPaths = true;
		
		this.groundBlock = Blocks.OBSIDIAN;
		this.pathBlock = Blocks.DIAMOND_BLOCK;
		
		this.blockStart = this.getEdgeLenght() + 13;
	}
	
	public City(int id, String outsideLightType, String streetLightType, int cityLength,
			int edgeLenght, int mapLength, Block groundBlock, Block pathBlock){
		this.id = id;
		
		this.outsideLightType = outsideLightType;
		this.streetLightType = streetLightType;
		
		this.cityLength = cityLength;
		this.edgeLenght = edgeLenght;
		this.mapLength = mapLength;
		
		this.groundBlock = groundBlock;
		this.pathBlock = pathBlock;

	}
	
	//This function will be use to generate new city. Inside functions that calculate paths, buildings positions etc will be called.
	public void calculateNewCity() {
		// Chunk Editor
		
		// Path generation is currently executed at DebugItem.java
		
		// Make buildings
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
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
		this.cityLength = cityLength;
	}

	public int getEdgeLenght() {
		return edgeLenght;
	}

	public void setEdgeLenght(int edgeLenght) {
		this.edgeLenght = edgeLenght;
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
}
