package org.twittercity.twittercitymod.city;

import net.minecraft.util.math.Vec3i;

public enum EnumCityBuildDirection {
	CENTER(0, new Vec3i(0, 0, 0)),
	NORTH_WEST(1, new Vec3i(-1, 0, -1)),
	NORTH(2, new Vec3i(0, 0, -1)),
	NORTH_EAST(3, new Vec3i(1, 0, -1)),
	EAST(4, new Vec3i(1, 0, 0)),
	SOUTH_EAST(5, new Vec3i(1, 0, 1)),
	SOUTH(6, new Vec3i(0, 0, 1)),
	SOUTH_WEST(7, new Vec3i(-1, 0, 1)),
	WEST(8, new Vec3i(-1, 0, 0));
	
	private final int id;
	private final Vec3i directionVector;
	
	private EnumCityBuildDirection(int index, Vec3i directionVec) {
		id = index;
		directionVector = directionVec;
	}
	
	public int getIndex() {
		return id;
	}
	
	public Vec3i getDirectionVector() {
		return directionVector;
	}
	
	/**
	 * Get the direction the next city will built.
	 * @param currentDirection Current city's building direction
	 * @return Next city's direction or default {@link EnumCityBuildDirection#NORTH_WEST}
	 */
	public static EnumCityBuildDirection getNextCityDirection(EnumCityBuildDirection currentDirection) {
		if(currentDirection == null) {
			return CENTER;
		}
		else {
			return getCityDirectionByIndex(currentDirection.getIndex() + 1);
		}
		
	}

	public static EnumCityBuildDirection getCityDirectionByIndex(int index) {
		for(EnumCityBuildDirection nextDirection: values()) {
            if (index == nextDirection.getIndex()) {
            	return nextDirection;
            }          
        }
		return NORTH_WEST;
	}
}
