package org.twittercity.twittercitymod.city;

import java.util.Comparator;
import java.util.List;

import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.data.db.Tweet;
import org.twittercity.twittercitymod.data.db.USState;
import org.twittercity.twittercitymod.data.db.USStateDAO;
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

		int remainingBlocks = Buildings.makeInsideCity(twitterWorld, currentConstructingCity, tweets);
		return remainingBlocks > 0;
	}
	
	public City createNewCity() {
		CitySettings citySettings = getNewCitySettings();
		City newCity = new City(citySettings, Paths.createCityArea(citySettings));
	
		prepareCity(newCity);
		//Save city
		cityWData.addCity(newCity);
		
		return newCity;
	}
	
	
	private CitySettings getNewCitySettings() {
		// Initial values for first city
		int id = constrWorldData.getCurrentConstructingCityId(), cityStateId, citySize, edgeLength, pathExtends = 2;
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

			cityStateId = getNextStateId(constrWorldData.getLatestStateID());

			//Calculate new city's starting position
			startingPos = CitySettings.getNewCityPosition(squareCornerPos.add(newCityBuildDirection.getDirectionVector()), newCityBuildDirection, nextCityLength);
			edgeLength = CitySettings.getValidEdgeLengthFromCityLength(nextCityLength);
			citySize = CitySettings.getCitySizeFromCityLength(nextCityLength, edgeLength); 
		}
		else { // First city
			citySize = 6;
			edgeLength = 3;
			startingPos = new BlockPos(0, 63, 0);
			cityStateId = 1;
			constrWorldData.setCitiesSquareNorthWestCorner(startingPos);
		}

//		CitySettings citySettings = new CitySettings(++cityId, startingPos, citySize,
//				edgeLength, pathExtends, groundBlock, pathBlock, true, true);

		final USState cityState = USStateDAO.getInstance().getState(cityStateId);

        CitySettings citySettings = CitySettings.builder()
                .id(++id)
                .startingPos(startingPos)
                .chunkLength(citySize)
                .edgeLength(edgeLength)
                .pathExtends(pathExtends)
                .groundBlock(groundBlock)
                .pathBlock(pathBlock)
                .hasMainStreets(true)
                .hasPaths(true)
                //.constructionInfo(newCityConstructionInfo)
                .state(cityState)
                .mapLength((citySize * 16) + (edgeLength * 2))
                .cityLength(((citySize * 16) + (edgeLength * 2)) + edgeLength * 2)
                .build();

        constrWorldData.setCityLength(citySettings.getCityLength());
        constrWorldData.setCurrentConstructingCityId(id);

        return citySettings;
    }

	private int getNextStateId(int currentStateId) {
		final int lastStateId = USStateDAO.getInstance().getLastStateId();
		final int nextStateId = currentStateId + 1;
		return lastStateId >= nextStateId ? nextStateId : 1;
	}

    private City findLastBuiltCity() {
        return cityWData.getCities().stream()
                .min(Comparator.comparing(cityElement -> cityElement.getSettings().getId())).orElse(null);
    }


    public void prepareCity(City city) {
        ChunkEditor.makeFlatAreaForCity(twitterWorld, city);
        Paths.makePaths(twitterWorld, city);
    }


    public City getCity(int id) {
        return cityWData.getCity(id);
    }

    public static CitiesManager getInstance() {
        if (instance == null) {
            instance = new CitiesManager();
        } else {
            instance.updateFields();
        }
        return instance;
    }

}
