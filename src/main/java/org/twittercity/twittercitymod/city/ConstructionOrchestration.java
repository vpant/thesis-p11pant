package org.twittercity.twittercitymod.city;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.data.db.Tweet;
import org.twittercity.twittercitymod.data.db.USState;
import org.twittercity.twittercitymod.data.db.USStateDAO;
import org.twittercity.twittercitymod.data.world.CityWorldData;
import org.twittercity.twittercitymod.data.world.StateData;
import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;

import java.util.Comparator;
import java.util.List;

public class ConstructionOrchestration {
    private World twitterWorld;
    private CityWorldData cityWData;
    private static ConstructionOrchestration instance;

    private ConstructionOrchestration() {
        initializeFields();
    }

    private void initializeFields() {
        twitterWorld = DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID);
        cityWData = CityWorldData.get(twitterWorld);
    }

    public void build(final List<Tweet> tweets) {
        final int stateId = tweets.stream().findFirst().map(tweet -> tweet.getState().getId()).orElse(1);
        final City city = findBuildableCity(stateId);

        int remainingBlocks = city.getSettings().getState().getId().equals(stateId) ?
                Buildings.makeInsideCity(twitterWorld, city, tweets)
                : tweets.size();

        // extreme lag because for stateId =1  has to create 54 cities in order to build the remaining blocks
        // maybe just the StateData#currentStateId to the next one
        // prepare the next city and empty the tweets to be built queue
        if (remainingBlocks > 0) {
            build(tweets.subList(tweets.size() - remainingBlocks, tweets.size()));
        }
    }

    /**
     * Checks if there is an unfinished city for a given stateId and returns it else returns a new created city
     * for the next stateId (not necessary the one given as parameter).
     *
     * @param stateId id of US state.
     *
     * @return instance of {@link City}
     */
    private City findBuildableCity(final Integer stateId) {
        final City firstUnfinishedBuiltCityForStateId = findFirstUnfinishedBuiltCityForStateId(stateId);
        City validCity;
        if (isCityNotBuildable(firstUnfinishedBuiltCityForStateId)) {
            validCity = createNewCity();
            // should check if new city state id == stateId
            if (!stateId.equals(validCity.getSettings().getState().getId())) {
                validCity = findBuildableCity(stateId);
            }
        } else {
            validCity = firstUnfinishedBuiltCityForStateId;
        }

        return validCity;
    }

    private boolean isCityNotBuildable(City city) {
        return city == null || city.isCityCompleted();
    }

    private City createNewCity() {
        final City lastBuiltCity = findLastBuiltCity();
        final CitySettings citySettings = getNewCitySettings(lastBuiltCity);
        final ConstructionInfo constructionInfo = getNewCityConstructionInfo(lastBuiltCity);
        final City newCity = new City(citySettings, constructionInfo, Paths.createCityArea(citySettings));

        prepareCity(twitterWorld, newCity);
        //Save city
        cityWData.addCity(newCity);
        TwitterCity.logger.info("New city created: {}", newCity.toString());
        return newCity;
    }

    private CitySettings getNewCitySettings(final City lastBuiltCity) {
        int lastCityId = lastBuiltCity != null ? lastBuiltCity.getSettings().getId() : -1;
        // Initial values for first city
        int citySize, edgeLength, pathExtends = 2;
        int nextCityLength;
        BlockPos startingPos;
        final Block groundBlock = Blocks.OBSIDIAN;
        final Block pathBlock = Blocks.DIAMOND_BLOCK;

        EnumCityBuildDirection lastCityDirection =
                lastBuiltCity != null ? lastBuiltCity.getConstructionInfo().getBuildDirection() : null;
        EnumCityBuildDirection newCityBuildDirection = EnumCityBuildDirection.getNextCityDirection(lastCityDirection);

        BlockPos squareCornerPos =
                lastBuiltCity != null ? lastBuiltCity.getConstructionInfo().getCitiesSquareNorthWestCorner() : new BlockPos(0, 63, 0);

        final int cityStateId;

        // Not the first city
        if (lastCityId >= 0) {
            nextCityLength = lastBuiltCity.getSettings().getCityLength();
            if (lastCityDirection == EnumCityBuildDirection.WEST) {
                squareCornerPos = squareCornerPos.add(
                        (nextCityLength + 1) * newCityBuildDirection.getDirectionVector().getX(),
                        0,
                        (nextCityLength + 1) * newCityBuildDirection.getDirectionVector().getZ());
                nextCityLength *= 3;
            }

            cityStateId = StateData.get(twitterWorld).getNextStateId(lastBuiltCity.getSettings().getState().getId());

            //Calculate new city's starting position
            startingPos = CitySettings.getNewCityPosition(squareCornerPos.add(newCityBuildDirection.getDirectionVector()), newCityBuildDirection, nextCityLength);
            edgeLength = CitySettings.getValidEdgeLengthFromCityLength(nextCityLength);
            citySize = CitySettings.getCitySizeFromCityLength(nextCityLength, edgeLength);
        } else { // First city
            citySize = 6;
            edgeLength = 3;
            startingPos = new BlockPos(0, 63, 0);
            cityStateId = 1;
        }

        final USState cityState = USStateDAO.getInstance().getState(cityStateId);

        return CitySettings.builder()
                .id(++lastCityId)
                .startingPos(startingPos)
                .chunkLength(citySize)
                .edgeLength(edgeLength)
                .pathExtends(pathExtends)
                .groundBlock(groundBlock)
                .pathBlock(pathBlock)
                .hasMainStreets(true)
                .hasPaths(true)
                .state(cityState)
                .mapLength((citySize * 16) + (edgeLength * 2))
                .cityLength(((citySize * 16) + (edgeLength * 2)) + edgeLength * 2)
                .build();
    }

    private ConstructionInfo getNewCityConstructionInfo(final City lastBuiltCity) {
        int lastCityId = lastBuiltCity != null ? lastBuiltCity.getSettings().getId() : -1;

        EnumCityBuildDirection lastCityDirection =
                lastBuiltCity != null ? lastBuiltCity.getConstructionInfo().getBuildDirection() : null;
        EnumCityBuildDirection newCityBuildDirection = EnumCityBuildDirection.getNextCityDirection(lastCityDirection);

        BlockPos squareCornerPos =
                lastBuiltCity != null ? lastBuiltCity.getConstructionInfo().getCitiesSquareNorthWestCorner() : new BlockPos(0, 63, 0);
        // Not the first city
        if (lastCityId >= 0) {
            final int nextCityLength = lastBuiltCity.getSettings().getCityLength();
            if (lastCityDirection == EnumCityBuildDirection.WEST) {
                squareCornerPos = squareCornerPos.add((nextCityLength + 1) * newCityBuildDirection.getDirectionVector().getX(), 0, (nextCityLength + 1) * newCityBuildDirection.getDirectionVector().getZ());
            }
            squareCornerPos.add(newCityBuildDirection.getDirectionVector());
        } else { // First city
            squareCornerPos = new BlockPos(0, 63, 0);
        }

        final ConstructionInfo cInfo = new ConstructionInfo();
        cInfo.setCitiesSquareNorthWestCorner(squareCornerPos);
        cInfo.setBuildDirection(newCityBuildDirection);
        return cInfo;
    }

    private City findFirstUnfinishedBuiltCityForStateId(final int stateId) {
        List<City> unfinishedCities = cityWData.getCities();
        return unfinishedCities.stream()
                .filter(unfinishedCity -> unfinishedCity.getSettings().getState().getId().equals(stateId) && !unfinishedCity.isCityCompleted())
                .min(Comparator.comparing(city -> city.getSettings().getId())).orElse(null);
    }


    public City findLastBuiltCity() {
        return cityWData.getCities().stream()
                .max(Comparator.comparing(cityElement -> cityElement.getSettings().getId())).orElse(null);
    }

    public void prepareCity(final World world, final City city) {
        ChunkEditor.makeFlatAreaForCity(world, city);
        Paths.makePaths(world, city);
    }

    public static ConstructionOrchestration getInstance() {
        if (instance == null) {
            instance = new ConstructionOrchestration();
        } else {
            instance.initializeFields();
        }
        return instance;
    }
}
