package org.twittercity.twittercitymod.city;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

/* Data object represents a city. Cities are always square. */
public final class City {	
	private final CitySettings settings;
	
	// Representation of the city in a 2D array
	private final int[][] cityArea;
	private final int firstDimSize;
	private final int secondDimSize;
	
	// When buildings construction is finished but not city construction
	// we need to do the last things (make street lights, connect paths to roads) etc
	private boolean areAllBuildingsCompleted = false;
	private boolean isCityCompleted = false;

	public City(CitySettings settings, int[][] area) {
		this.settings = settings;
		this.cityArea = area;
		this.firstDimSize = area.length;
		this.secondDimSize = area[1].length;
	}

	public City(NBTTagCompound nbt) {			
		this.settings = new CitySettings(nbt);
		this.areAllBuildingsCompleted = nbt.getBoolean("isBuildingsConstructionCompleted");
		this.isCityCompleted = nbt.getBoolean("isCityConstructionCompleted");	
		
		this.firstDimSize = nbt.hasKey("firstDimSize") ? nbt.getInteger("firstDimSize") : -1;
		this.secondDimSize = nbt.hasKey("secondDimSize") ? nbt.getInteger("secondDimSize") : -1;
		
		this.cityArea = new int[firstDimSize][secondDimSize];
		for (int i = 0; i < firstDimSize; i++) {
			for (int j = 0; j < secondDimSize; j++) {
				this.cityArea[i][j] = nbt.hasKey(i + "," + j) ? nbt.getInteger(i + "," + j) : 0;
			}
		}
	}

	public int getId() {
		return settings.getId();
	}

	public BlockPos getStartingPos() {
		return settings.getStartingPos();
	}

	public int getChunkLength() {
		return settings.getChunkLength();
	}

	public int getEdgeLength() {
		return settings.getEdgeLength();
	}

	public int getMapLength() {
		return settings.getMapLength();
	}

	public Block getGroundBlock() {
		return settings.getGroundBlock();
	}

	public Block getPathBlock() {
		return settings.getPathBlock();
	}

	public boolean hasMainStreets() {
		return settings.hasMainStreets();
	}

	public boolean hasPaths() {
		return settings.hasPaths();
	}
	
	public int getPathExtends() {
		return settings.getPathExtends();
	}

	public int getCityLength() {
		return settings.getCityLength();
	}
	
	public int[][] getCityArea() {
		return this.cityArea;
	}
	
	public void setAreAllBuildingsCompleted(boolean itIs) {
		this.areAllBuildingsCompleted = itIs;
	}

	public boolean getAreAllBuildingsCompleted() {
		return this.areAllBuildingsCompleted;
	}
	
	public void setIsCityCompleted(boolean itIs) {
		this.isCityCompleted = itIs;
	}

	public boolean getIsCityCompleted() {
		return this.isCityCompleted;
	}
	
	public NBTTagCompound writeToNBT() {		
		NBTTagCompound nbt = settings.writeToNBT();
	
		nbt.setBoolean("isCityConstructionCompleted", this.isCityCompleted);
		nbt.setBoolean("isBuildingsConstructionCompleted", this.areAllBuildingsCompleted);
		
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
		return "";//"City id: " + settings.id;
	}
}
