package org.twittercity.twittercitymod.city;

import java.util.List;

import org.twittercity.twittercitymod.data.db.Tweet;
import org.twittercity.twittercitymod.data.world.CityWorldData;
import org.twittercity.twittercitymod.data.world.ConstructionWorldData;
import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class CitiesManager {
	
	private static CitiesManager instance = null;
	
	private ConstructionWorldData constrWorldData = null;
	private CityWorldData cityWData = null;
	private World twitterWorld = null;

	
	private CitiesManager() {
		updateFields();
	}
	
	private void updateFields() {
		twitterWorld = DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID);
		cityWData = CityWorldData.get(twitterWorld);
		constrWorldData = ConstructionWorldData.get(twitterWorld);
	}
	
	public boolean startBuilding(List<Tweet> tweets) {
		//Get latest city's construction info 
		City currentConstructingCity = getCity(constrWorldData.getCurrentConstructingCityId());
		if(currentConstructingCity == null) {
			currentConstructingCity = createNewCity();
		}
		
		if(tweets.isEmpty() || BuildingReference.cityPreparationActive) {
			return false;
		}
		Buildings.makeInsideCity(twitterWorld, currentConstructingCity, tweets);
		return true;
	}
	
	public City createNewCity() {
		CitySettings citySettings = getNewCitySettings();
		City newCity = new City(citySettings, Paths.createCityArea(citySettings));
	
		prepareCity(newCity);
		//Save city
		cityWData.setCity(newCity);
		
		return newCity;
	}
	
	
	private CitySettings getNewCitySettings() {
		// Initial values for first city
		int id = constrWorldData.getCurrentConstructingCityId(), citySize, edgeLength, pathExtends = 2;
		int nextCityLength = 0;
		BlockPos startingPos;
		Block groundBlock = Blocks.OBSIDIAN, pathBlock = Blocks.DIAMOND_BLOCK;
		EnumCityBuildDirection lastCityDirection = constrWorldData.getCityDirection();
		EnumCityBuildDirection newCityBuildDirection = constrWorldData.setNextCityDirection().getCityDirection();
		BlockPos squareCornerPos = constrWorldData.getCitiesSquareNorthWestCorner();
		// Not the first city
		if(id >= 0) {
			nextCityLength = constrWorldData.getCityLength();
			if(lastCityDirection == EnumCityBuildDirection.WEST) {
				squareCornerPos = squareCornerPos.add((nextCityLength + 1) * newCityBuildDirection.getDirectionVector().getX(), 0, (nextCityLength + 1) * newCityBuildDirection.getDirectionVector().getZ());
				nextCityLength *= 3;
				constrWorldData.setCitiesSquareNorthWestCorner(squareCornerPos);
			}

			//Calculate new city's starting position
			startingPos = CitySettings.getNewCityPosition(squareCornerPos.add(newCityBuildDirection.getDirectionVector()), newCityBuildDirection, nextCityLength);
			edgeLength = CitySettings.getValidEdgeLengthFromCityLength(nextCityLength);
			citySize = CitySettings.getCitySizeFromCityLength(nextCityLength, edgeLength); 
		}
		else { // First city
			citySize = 6;
			edgeLength = 3;
			startingPos = new BlockPos(0, 63, 0);
			constrWorldData.setCitiesSquareNorthWestCorner(startingPos);
		}

		CitySettings citySettings = new CitySettings(++id, startingPos, citySize,
				edgeLength, pathExtends, groundBlock, pathBlock, true, true);
		constrWorldData.setCityLength(citySettings.getCityLength());
		constrWorldData.setCurrentConstructingCityId(id);

		return citySettings;
	}


	public void prepareCity(City city) {
		ChunkEditor.makeFlatAreaForCity(twitterWorld, city);
		Paths.makePaths(twitterWorld, city);
	}
	
	
	public City getCity(int id) {
		return cityWData.getCity(id);
	}
	
	public static CitiesManager getInstance() {
		if(instance == null) {
			instance = new CitiesManager();
		} else {
			instance.updateFields();
		}
		return instance;
	}

}
