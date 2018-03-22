package org.twittercity.twittercitymod.city;

/* Data object represents a city */
public class City {
	
	private int id;
	private int x;
	private int z;
	private int chunkLength;
	
	
	private String outsideLightType;
	private String streetLightType;
	
	private int cityLength;
	private int edgeLenght;
	private int mapLength;
	private int pathExtends;
	
	public int getPathExtends() {
		return pathExtends;
	}

	public void setPathExtends(int pathExtends) {
		this.pathExtends = pathExtends;
	}

	// Blocks. For now int ID and not Block object
	private int groundBlockID;
	
	private boolean hasMainStreets;
	
	public City(){
		this.id = 0;
		
		this.outsideLightType = "";
		this.streetLightType = "";
		
		this.cityLength = 0;
		this.edgeLenght = 0;
		this.mapLength = 0;
		
		this.groundBlockID = 0;
		
	}
	
	public City(int id, String outsideLightType, String streetLightType, int cityLength,
			int edgeLenght, int mapLength, int groundBlockID){
		this.id = id;
		
		this.outsideLightType = outsideLightType;
		this.streetLightType = streetLightType;
		
		this.cityLength = cityLength;
		this.edgeLenght = edgeLenght;
		this.mapLength = mapLength;
		
		this.groundBlockID = groundBlockID;

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

	public int getGroundBlockID() {
		return groundBlockID;
	}

	public void setGroundBlockID(int groundBlockID) {
		this.groundBlockID = groundBlockID;
	}

	public boolean hasMainStreets() {
		return hasMainStreets;
	}

	public void setHasMainStreets(boolean hasMainStreets) {
		this.hasMainStreets = hasMainStreets;
	}
	
	
	
}
