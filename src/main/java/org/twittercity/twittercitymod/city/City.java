package org.twittercity.twittercitymod.city;

import lombok.Data;
import lombok.experimental.Accessors;
import net.minecraft.nbt.NBTTagCompound;

/* Data object represents a city. Cities are always square. */
@Data
public final class City {
	private final CitySettings settings;
	private final ConstructionInfo constructionInfo;
	private Integer buildingsCount = 0;
	// Representation of the city in a 2D array
	private int[][] cityArea;
	private final int firstDimSize;
	private final int secondDimSize;
	
	// When buildings construction is finished but not city construction
	// we need to do the last things (make streetlights, connect paths to roads) etc
	@Accessors(fluent = true)
	private boolean areChunksPregenerated = false;
	@Accessors(fluent = true)
	private boolean isCityCompleted = false;

	public City(CitySettings citySettings, ConstructionInfo cInfo, int[][] area) {
		settings = citySettings;
		constructionInfo = cInfo;
		cityArea = area;
		firstDimSize = area.length;
		secondDimSize = area[1].length;
	}

	public City(NBTTagCompound nbt) {			
		settings = new CitySettings(nbt);

		isCityCompleted = nbt.getBoolean("isCityConstructionCompleted");

		firstDimSize = nbt.hasKey("firstDimSize") ? nbt.getInteger("firstDimSize") : -1;
		secondDimSize = nbt.hasKey("secondDimSize") ? nbt.getInteger("secondDimSize") : -1;
		
		cityArea = new int[firstDimSize][secondDimSize];
		for (int i = 0; i < firstDimSize; i++) {
			for (int j = 0; j < secondDimSize; j++) {
				cityArea[i][j] = nbt.hasKey(i + "," + j) ? nbt.getInteger(i + "," + j) : 0;
			}
		}
		constructionInfo = new ConstructionInfo(nbt);
	}

	public NBTTagCompound writeToNBT() {		
		NBTTagCompound nbt = settings.writeToNBT();

		nbt.setBoolean("isCityConstructionCompleted", isCityCompleted);

		if(cityArea != null ) {
			nbt.setInteger("firstDimSize", cityArea.length);
			nbt.setInteger("secondDimSize", cityArea[1].length);
			for (int i = 0; i < cityArea.length; i++) {
				for (int j = 0; j < cityArea[1].length; j++) {
					nbt.setInteger(i + "," + j, cityArea[i][j]);
				}
			}
		}
		constructionInfo.writeToNBT(nbt);
		return nbt;
	}

	public static City readFromNbt(NBTTagCompound nbt) {
		return new City(nbt);
	}

	public void increaseBuildingCount() {
		buildingsCount += 1;
	}
}
