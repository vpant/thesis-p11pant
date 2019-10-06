package org.twittercity.twittercitymod.city;

import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.data.db.Tweet;
import org.twittercity.twittercitymod.data.world.CityWorldData;
import org.twittercity.twittercitymod.data.world.ConstructionWorldData;
import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class CitiesManager {
	
	private static CitiesManager instance = null;
	
	private ConstructionWorldData constrWorldData = null;
	private CityWorldData cityWData = null;
	private World twitterWorld = null;
	
	private CitiesManager( ) {
		updateFields();
	}
	
	private void updateFields() {
		twitterWorld = DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID);
		cityWData = CityWorldData.get(twitterWorld);
		constrWorldData = ConstructionWorldData.get(twitterWorld);
	}
	
	public void startBuilding(Tweet[] tweets) {
		
		//Get latest cities construction info 
		City currentConstructingCity;
		if(constrWorldData.getCurrentConstructingCityId() < 0) {
			currentConstructingCity = createNewCity();
			prepareCity(currentConstructingCity);
		} else {
			TwitterCity.logger.info("Current constructing id is: {}", constrWorldData.getCurrentConstructingCityId());
			currentConstructingCity = getCity(constrWorldData.getCurrentConstructingCityId());
		}
		TwitterCity.logger.info("The mapLength is: {}", currentConstructingCity.getMapLength());
		TwitterCity.logger.info("The startingPos is: {}", currentConstructingCity.getStartingPos().toString());
		Buildings.makeInsideCity(twitterWorld, currentConstructingCity, tweets);
	}
	
	public City createNewCity() {
		TwitterCity.logger.info("Creating new city!");
		int id = constrWorldData.getCurrentConstructingCityId();
		
		//Calculate new city's starting position
		BlockPos startingPos = new BlockPos(1000,63,1000);
		
		// Should make random
		City newCity = new City(++id, startingPos, 5, 8, 4, Blocks.OBSIDIAN, Blocks.DIAMOND_BLOCK, true, true);
		
		newCity.setCityArea(Paths.createCityArea(newCity));
		
		//Save city
		cityWData.setCity(newCity);
		constrWorldData.setCurrentConstructingCityId(id);
		return newCity;
	}
	
	public void prepareCity(City city) {
		//if(ChunkPreGenReference.isPreGenFinished) {}
		//ChunkGenerationUtils.queueCityChunkGeneration(twitterWorld.getMinecraftServer(), city, TwitterCityWorldGenReference.DIM_ID, true);
		
		ChunkEditor.makeFlatChunksForCity(twitterWorld, city);
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
