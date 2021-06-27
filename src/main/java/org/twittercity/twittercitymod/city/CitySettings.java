package org.twittercity.twittercitymod.city;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.data.db.USState;
import org.twittercity.twittercitymod.data.world.ConstructionInfo;
import org.twittercity.twittercitymod.util.RandomHelper;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

@Data
@AllArgsConstructor
@Builder
public final class CitySettings {
	private final ConstructionInfo constructionInfo;
	private final USState state;

	private final int id;
	// BlockPos object to the very first block of this city
	private final BlockPos startingPos;
	
	// Size in chunks for one city size where the outside paths starts
	private final int chunkLength;
	//City expansion at X and Z axis
	private final int edgeLength;
	
	// Size in blocks of the whole city's side (taking into consideration and edgeLength)
	private final int mapLength;
	// How many the main roads paths will extend left and right
	// e.g Path has length 1 block by default, with pathExtends = 2 the main roads will have 
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
		this.id = nbt.getInteger("id");
		this.startingPos = BlockPos.fromLong(nbt.getLong("startingPosLong"));

		this.edgeLength = nbt.getInteger("edgeLength");
		this.mapLength = nbt.getInteger("mapLength");
		this.pathExtends = nbt.getInteger("pathExtends");
		this.groundBlock = Block.getBlockById(nbt.getInteger("groundBlockID"));
		this.pathBlock = Block.getBlockById(nbt.getInteger("pathBlockID"));

		this.cityLength = nbt.getInteger("cityLength");

		this.hasMainStreets = nbt.getBoolean("hasMainStreets");
		this.hasPaths = nbt.getBoolean("hasPaths");

		this.chunkLength = nbt.getInteger("chunkLength");

		this.constructionInfo = new ConstructionInfo(nbt);
		this.state = new USState(nbt);
	}

	public NBTTagCompound writeToNBT() {

		NBTTagCompound nbt = new NBTTagCompound();
		constructionInfo.writeToNBT(nbt);
		state.writeToNBT(nbt);
		nbt.setInteger("id", this.id);
		nbt.setLong("startingPosLong", this.startingPos.toLong());
		nbt.setInteger("chunkLength", this.chunkLength);
		nbt.setInteger("edgeLength", this.edgeLength);
		nbt.setInteger("pathExtends", this.pathExtends);
		nbt.setInteger("mapLength", this.mapLength);
		nbt.setBoolean("hasPaths", this.hasPaths);
		nbt.setInteger("groundBlockID", Block.getIdFromBlock(this.groundBlock));
		nbt.setInteger("pathBlockID", Block.getIdFromBlock(this.pathBlock));
		nbt.setBoolean("hasMainStreets", this.hasMainStreets);
		nbt.setBoolean("hasPaths", this.hasPaths);
		nbt.setInteger("cityLength", this.cityLength);

		return nbt;
	}

	public static int getValidEdgeLengthFromCityLength(int cityLength) {
		int randomEdgeLength = RandomHelper.nextInt(3, 6);
		
		int citySize = (cityLength - (randomEdgeLength * 4)) / 16;
		
		int validEdgeLength = randomEdgeLength;
		boolean towardsNegative = true;
		while(((citySize * 16) + (validEdgeLength * 4)) != cityLength) {
			validEdgeLength = towardsNegative ? (validEdgeLength - 1) : (validEdgeLength + 1);
			if(validEdgeLength < 0) {
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
	 * Calculates the citySize using this formula <b>citySize = (cityLength - (preferedEdgeLegth * 4)) / 16;</b>
	 * @param cityLength Length of the desired city
	 * @param edgeLength of the city
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
		TwitterCity.logger.info("New cities starting pos is: {}", pos.toString());
		return pos;
	}
}
