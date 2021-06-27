package org.twittercity.twittercitymod.city;

import java.util.Comparator;
import java.util.List;

import org.twittercity.twittercitymod.data.db.Tweet;
import org.twittercity.twittercitymod.data.db.USState;
import org.twittercity.twittercitymod.data.db.USStateDAO;
import org.twittercity.twittercitymod.data.world.CityWorldData;
import org.twittercity.twittercitymod.data.world.ConstructionInfo;
import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class CitiesManager {

    private static CitiesManager instance = null;

    //private ConstructionWorldData constrWorldData = null;
    private CityWorldData cityWData = null;
    private World twitterWorld = null;


    private CitiesManager() {
        updateFields();
    }

    private void updateFields() {
        twitterWorld = DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID);
        cityWData = CityWorldData.get(twitterWorld);
        //constrWorldData = ConstructionWorldData.get(twitterWorld);
    }

    public boolean startBuilding(List<Tweet> tweets, City currentConstructingCity) {
        //Get latest city's construction info
        if (currentConstructingCity == null) {//|| currentConstructingCity.isCityCompleted()) {
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
        City lastBuiltCity = findLastBuiltCity();
        ConstructionInfo newCityConstructionInfo = new ConstructionInfo();
        int cityId = lastBuiltCity != null ? lastBuiltCity.getSettings().getId() : -1;
        // Initial values for first city
        int citySize, edgeLength, pathExtends = 2;
        int nextCityLength = 0;
        BlockPos startingPos;
        Block groundBlock = Blocks.OBSIDIAN, pathBlock = Blocks.DIAMOND_BLOCK;

        EnumCityBuildDirection lastCityDirection =
                lastBuiltCity != null ? lastBuiltCity.getSettings().getConstructionInfo().getBuildDirection() : null;
        EnumCityBuildDirection newCityBuildDirection = EnumCityBuildDirection.getNextCityDirection(lastCityDirection);

        BlockPos squareCornerPos =
                lastBuiltCity != null ? lastBuiltCity.getSettings().getConstructionInfo().getCitiesSquareNorthWestCorner() : new BlockPos(0, 63, 0);

        final int cityStateId;

        // Not the first city
        if (cityId >= 0) {
            nextCityLength = lastBuiltCity.getSettings().getCityLength();
            if (lastCityDirection == EnumCityBuildDirection.WEST) {
                squareCornerPos = squareCornerPos.add((nextCityLength + 1) * newCityBuildDirection.getDirectionVector().getX(), 0, (nextCityLength + 1) * newCityBuildDirection.getDirectionVector().getZ());
                nextCityLength *= 3;
            }

            cityStateId = getNextStateId(lastBuiltCity.getSettings().getState().getId());

            //Calculate new city's starting position
            startingPos = CitySettings.getNewCityPosition(squareCornerPos.add(newCityBuildDirection.getDirectionVector()), newCityBuildDirection, nextCityLength);
            edgeLength = CitySettings.getValidEdgeLengthFromCityLength(nextCityLength);
            citySize = CitySettings.getCitySizeFromCityLength(nextCityLength, edgeLength);
        } else { // First city
            citySize = 6;
            edgeLength = 3;
            startingPos = new BlockPos(0, 63, 0);
            cityStateId = 1;
            squareCornerPos = startingPos;
        }
        newCityConstructionInfo.setCitiesSquareNorthWestCorner(squareCornerPos);
        newCityConstructionInfo.setBuildDirection(newCityBuildDirection);

        final USState cityState = USStateDAO.getInstance().getState(cityStateId);

        CitySettings citySettings = CitySettings.builder()
                .id(++cityId)
                .startingPos(startingPos)
                .chunkLength(citySize)
                .edgeLength(edgeLength)
                .pathExtends(pathExtends)
                .groundBlock(groundBlock)
                .pathBlock(pathBlock)
                .hasMainStreets(true)
                .hasPaths(true)
                .constructionInfo(newCityConstructionInfo)
                .state(cityState)
                .mapLength((citySize * 16) + (edgeLength * 2))
                .cityLength(((citySize * 16) + (edgeLength * 2)) + edgeLength * 2)
                .build();

        return citySettings;
    }

    private int getNextStateId(int currentStateId) {
        final int lastStateId = USStateDAO.getInstance().getLastStateId();
        final int nextStateId = currentStateId + 1;
        return lastStateId >= nextStateId ? nextStateId : 1;
    }

    private City findLastBuiltCity() {
        return cityWData.getCities().stream()
                .max(Comparator.comparing(cityElement -> cityElement.getSettings().getId())).orElse(null);
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
