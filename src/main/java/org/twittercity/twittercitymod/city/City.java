package org.twittercity.twittercitymod.city;

import lombok.Data;
import lombok.experimental.Accessors;
import net.minecraft.nbt.NBTTagCompound;

/* Data object represents a city. Cities are always square. */
@Data
public final class City {
	private final CitySettings settings;

	// Representation of the city in a 2D array
	private int[][] cityArea;
	private final int firstDimSize;
	private final int secondDimSize;
	
	// When buildings construction is finished but not city construction
	// we need to do the last things (make street lights, connect paths to roads) etc
	@Accessors(fluent = true)
	private boolean areChunksPregenerated = false;
	@Accessors(fluent = true)
	private boolean isCityCompleted = false;
	@Accessors(fluent = true)
	private boolean areBuildingsFinished = false;

	public City(CitySettings settings, int[][] area) {
		this.settings = settings;
		this.cityArea = area;
		this.firstDimSize = area.length;
		this.secondDimSize = area[1].length;
	}

	public City(NBTTagCompound nbt) {			
		this.settings = new CitySettings(nbt);

		this.isCityCompleted = nbt.getBoolean("isCityConstructionCompleted");
		this.areBuildingsFinished = nbt.getBoolean("areBuildingsFinished");

		this.firstDimSize = nbt.hasKey("firstDimSize") ? nbt.getInteger("firstDimSize") : -1;
		this.secondDimSize = nbt.hasKey("secondDimSize") ? nbt.getInteger("secondDimSize") : -1;
		
		this.cityArea = new int[firstDimSize][secondDimSize];
		for (int i = 0; i < firstDimSize; i++) {
			for (int j = 0; j < secondDimSize; j++) {
				this.cityArea[i][j] = nbt.hasKey(i + "," + j) ? nbt.getInteger(i + "," + j) : 0;
			}
		}
	}

	public NBTTagCompound writeToNBT() {		
		NBTTagCompound nbt = settings.writeToNBT();


		nbt.setBoolean("isCityConstructionCompleted", this.isCityCompleted);
		nbt.setBoolean("areBuildingsFinished", this.isCityCompleted);

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
}
