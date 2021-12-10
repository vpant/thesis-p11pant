package org.twittercity.twittercitymod.city;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.data.db.USState;
import org.twittercity.twittercitymod.util.RandomHelper;

@Data
@AllArgsConstructor
@Builder
public final class CitySettings {
    private final USState state;

    private final int id;
    // BlockPos object to the very first block of this city
    private final BlockPos startingPos;

    // Size in chunks for one city size where the outside paths start
    private final int chunkLength;
    //City expansion at X and Z axis
    private final int edgeLength;

    // Size in blocks of the whole city's side (taking into consideration and edgeLength)
    private final int mapLength;
    // How many the main roads paths will extend left and right
    // e.g. Path has length 1 block by default, with pathExtends = 2 the main roads will have
    // 3 blocks length.
    private final int pathExtends;

    private final int cityLength;

    private final Block groundBlock;
    private final Block pathBlock;
    @Accessors(fluent = true)
    private final boolean hasMainStreets;
    @Accessors(fluent = true)
    private final boolean hasPaths;

    public CitySettings(NBTTagCompound nbt) {
        id = nbt.getInteger("id");
        startingPos = BlockPos.fromLong(nbt.getLong("startingPosLong"));

        edgeLength = nbt.getInteger("edgeLength");
        mapLength = nbt.getInteger("mapLength");
        pathExtends = nbt.getInteger("pathExtends");
        groundBlock = Block.getBlockById(nbt.getInteger("groundBlockID"));
        pathBlock = Block.getBlockById(nbt.getInteger("pathBlockID"));

        cityLength = nbt.getInteger("cityLength");

        hasMainStreets = nbt.getBoolean("hasMainStreets");
        hasPaths = nbt.getBoolean("hasPaths");

        chunkLength = nbt.getInteger("chunkLength");
        state = new USState(nbt);
    }

    public NBTTagCompound writeToNBT() {

        NBTTagCompound nbt = new NBTTagCompound();
        state.writeToNBT(nbt);
        nbt.setInteger("id", id);
        nbt.setLong("startingPosLong", startingPos.toLong());
        nbt.setInteger("chunkLength", chunkLength);
        nbt.setInteger("edgeLength", edgeLength);
        nbt.setInteger("pathExtends", pathExtends);
        nbt.setInteger("mapLength", mapLength);
        nbt.setBoolean("hasPaths", hasPaths);
        nbt.setInteger("groundBlockID", Block.getIdFromBlock(groundBlock));
        nbt.setInteger("pathBlockID", Block.getIdFromBlock(pathBlock));
        nbt.setBoolean("hasMainStreets", hasMainStreets);
        nbt.setBoolean("hasPaths", hasPaths);
        nbt.setInteger("cityLength", cityLength);

        return nbt;
    }

    public static int getValidEdgeLengthFromCityLength(int cityLength) {
        int randomEdgeLength = RandomHelper.nextInt(3, 6);

        int citySize = (cityLength - (randomEdgeLength * 4)) / 16;

        int validEdgeLength = randomEdgeLength;
        boolean towardsNegative = true;
        while (((citySize * 16) + (validEdgeLength * 4)) != cityLength) {
            validEdgeLength = towardsNegative ? (validEdgeLength - 1) : (validEdgeLength + 1);
            if (validEdgeLength < 0) {
                towardsNegative = false;
                validEdgeLength = randomEdgeLength++;
            }
            citySize = (cityLength - (validEdgeLength * 4)) / 16;
            TwitterCity.logger.info("Try to find validEdgeLength for cityLength: {}. "
                    + "The values for now are: citySize = {}, validEdgeLength = {}, towardsNegative = {}", cityLength, citySize, validEdgeLength, towardsNegative);
        }
        TwitterCity.logger.info("Found it. A valid edgeLength is: {}", validEdgeLength);
        return validEdgeLength;
    }

    /**
     * Calculates the citySize using this formula <b>citySize = (cityLength - (preferredEdgeLength * 4)) / 16;</b>
     *
     * @param cityLength Length of the desired city
     * @param edgeLength of the city
     *
     * @return City's size
     */
    public static int getCitySizeFromCityLength(int cityLength, int edgeLength) {
        return (cityLength - (edgeLength * 4)) / 16;
    }

    public static BlockPos getNewCityPosition(BlockPos corner, EnumCityBuildDirection buildDirection, int distanceFromStartingBlock) {

        int x = corner.getX() + (distanceFromStartingBlock * buildDirection.getDirectionVector().getX());
        int y = corner.getY();
        int z = corner.getZ() + (distanceFromStartingBlock * buildDirection.getDirectionVector().getZ());
        BlockPos pos = new BlockPos(x, y, z);
        TwitterCity.logger.debug("New cities starting pos is: {}", pos.toString());
        return pos;
    }
}
