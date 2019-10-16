package org.twittercity.twittercitymod.city;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.util.ArrayUtils;
import org.twittercity.twittercitymod.util.BlockHelper;
import org.twittercity.twittercitymod.util.RandomHelper;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Paths {
	//private static int street = 0;
	
	public static class District{
		public int x1;
		public int x2;
		public int z1;
		public int z2;
		
		public District() {
			
		}
	}
	
	private static List<Paths.District> lstDistricts = new ArrayList<Paths.District>();
	private static List<String> lstStreetsUsed = new ArrayList<String>();
	private static List<Integer> lstAllBuildings = new ArrayList<Integer>();
	
	// Here is where every city starts. Calculates the city area as a 2D array then we are using 
	// the array to build up everything (paths, roads, lights, buildings etc.)
	public static int[][] createCityArea(CitySettings citySettings) {
		
		lstDistricts.clear();
		lstStreetsUsed.clear();
		lstAllBuildings.clear();
		
		int plotBlocks = (1 + citySettings.getMapLength());// - (city.getEdgeLength() * 2);
		int[][] area = new int[plotBlocks][plotBlocks];
		
		if (citySettings.hasMainStreets()) {
			//make the main streets
			for (int a = 0; a <= (area.length - 1); a++){
				for (int c = -citySettings.getPathExtends(); c <= citySettings.getPathExtends(); c++){
					area[a][((area.length - 1) / 2) + c ] = 1;
					area[((area.length - 1) / 2) + c][a] = 1;
				}
			}
			
			//make the districts
			//top right
			splitArea(area, 0, 0, (area.length / 2 ) - (citySettings.getPathExtends() +1), (area.length / 2) - (citySettings.getPathExtends() + 1));
		
			//bottom left
			splitArea(area, (area.length / 2) + (citySettings.getPathExtends() + 1), (area.length / 2) + (citySettings.getPathExtends() + 1), (area.length - 1) , (area.length - 1));
			
			//bottom right
			splitArea(area, (area.length / 2) + (citySettings.getPathExtends() + 1), 0, (area.length - 1), (area.length / 2) - (citySettings.getPathExtends() + 1));
			
			//top left
			splitArea (area, 0, (area.length / 2) + (citySettings.getPathExtends() + 1), (area.length / 2) - (citySettings.getPathExtends() + 1), (area.length - 1));
		
			shuffleDistricts(lstDistricts);
		}
		else {
			splitArea (area, 0, 0, (area.length - 1),(area.length - 1));
		}
		
		for (District disCurrent : lstDistricts ) {
			int[][] district = fillArea(area, (disCurrent.x2 - disCurrent.x1),(disCurrent.z2 - disCurrent.z1), disCurrent.x1, disCurrent.z1, citySettings.getChunkLength() > 5);
			for (int x = 0; x < (district.length - 2); x++) {
				for (int y = 0; y < (district[1].length - 2); y++) {
					area[disCurrent.x1 + x + 1][disCurrent.z1 + y + 1] = district[x + 1][y + 1];
				}
			}
		}
		return area;
	}

	private static void splitArea(int[][] area, int x1, int z1, int x2, int z2) {
		for (int x = x1; x <= x2; x++)
		{
			for (int y = z1; y <= z2; y++)
			{
				if (x == x1 || x == x2 || y == z1 || y == z2)
				{
					if (area[x][y] < 500)
					{
						area[x][y] = 1;
					}
				}
			}
		}
		
		boolean possibleToSplit = true;
		boolean splitByX = false;
		if(Math.abs(x1 - x2) > 50 && Math.abs(z1 - z2) > 50)
		{
			splitByX = RandomHelper.nextDouble() > 0.5;
		}
		else if(Math.abs(x1 - x2) > 50)
		{
			splitByX = true;
		}
		else if(Math.abs (z1 - z2) <= 50)
		{
			possibleToSplit = false;
		}
		
		//city.hasPaths
		if (possibleToSplit) {
			if (splitByX) {
				int splitPoint = RandomHelper.nextInt(x1 + 20, x2 - 20);
				splitArea (area, x1, z1, splitPoint, z2);
				splitArea (area, splitPoint, z1, x2, z2);
				//city.hasPaths
				if(true) {
					//MakeStreetSign
				}
			}
			else {
				int splitPoint = RandomHelper.nextInt(z1 + 20, z2 - 20);
				splitArea(area, x1, z1, x2, splitPoint);
				splitArea(area, x1, splitPoint, x2, z2);
				//city.hasPaths
				if(true) {
					//MakeStreetSign
				}
				
			}
		}
		else {
			District disCurrent = new District();
			disCurrent.x1 = x1;
			disCurrent.x2 = x2;
			disCurrent.z1 = z1;
			disCurrent.z2 = z2;
			lstDistricts.add(disCurrent);
		}
	}
	
	private static void shuffleDistricts(List<District> lstShuffle) {
		int a = lstShuffle.size();
		
		while (a > 1 ) {
			a--;
			int b = RandomHelper.nextInt(a + 1);
			District disCurrent = lstShuffle.get(a);
			lstShuffle.set(a, lstShuffle.get(b));
			lstShuffle.set(b, disCurrent);
		}
	}
	
	private static int[][] fillArea(int[][] area, int sizeX, int sizeZ, int startX, int startZ, boolean booUniqueBonus) {
		int[][] district = new int[sizeX][sizeZ];
		int[][] intFinal = new int[sizeX][sizeZ];
		
		int wasted = sizeX * sizeZ, bonus = 0, attempts = 15, fail = 0;
		ArrayList<Integer> buildings = new ArrayList<Integer>();
		ArrayList<Integer> acceptedBuildings = new ArrayList<Integer>();
		
		do {
			buildings.clear();
			bonus = 0;
			
			do  {
				Building currentBuilding;
				
				do {
					currentBuilding = Buildings.selectRandomBuilding(Buildings.getAllBuildings());
				} while (!isValidBuilding(currentBuilding, buildings, area, startX, startZ, sizeX, sizeZ));
				
				boolean found = false;
				if (RandomHelper.nextDouble() > 0.5) {
					district = ArrayUtils.rotateArray(district, RandomHelper.nextInt(4));
				}
				int x, z=0;
				for (x = 0; x < district.length - currentBuilding.getSizeX() && !found; x++) {
					for(z = 0; z < (district[1].length - currentBuilding.getSizeZ()) && !found; z++) {
						found = ArrayUtils.isArraySectionAllZeros2D (district, x, z, x + currentBuilding.getSizeX(), z + currentBuilding.getSizeZ());
					}
				}
				x--;
				z--;
				if (found) {
					for (int a = x + 1; a <= (x + currentBuilding.getSizeX() - 1); a++) {
						for (int b = z + 1; b <= (z + currentBuilding.getSizeZ() - 1); b++) {
							district[a][b] = 2;
						}
					}
					buildings.add(currentBuilding.getID());
					district[x + 1][z + 1] = 100 + currentBuilding.getID();
					district[x + currentBuilding.getSizeX() - 1][z + currentBuilding.getSizeZ() - 1] = 100 + currentBuilding.getID();
					fail = 0;		
				}
				else {
					fail++;
				}
			} while(fail < 10);
			
			int curWasted = ArrayUtils.zerosInArray2D(district) - bonus;
			if(curWasted < wasted) {
				intFinal = new int[district.length][district[1].length];
				for (int i = 0; i < district.length; i++) {
					System.arraycopy(district[i], 0, intFinal[i], 0, district[0].length); // <- TEST - ITAN -> System.arraycopy(district[i], 0, intFinal[i], 0, district[0].length); //elegxos to district.length
				}
				wasted = curWasted;
				attempts = 10;
				acceptedBuildings.clear();
				acceptedBuildings.addAll(buildings);
			}
			//Clear district
			for(int[] row : district) {
				Arrays.fill(row, 0);
			}
			
			attempts--;
		} while(attempts > 0);
		
		if(sizeX == intFinal[1].length) {
			intFinal = ArrayUtils.rotateArray(intFinal, 1);
		}
		lstAllBuildings.addAll(lstAllBuildings);
		return intFinal;
	}
	
	// Build the paths for the city
	public static void makePaths(World world, City city) {
		int[][] area = city.getCityArea();
		for (int x = 0; x < area.length; x++) {
			for (int z = 0; z < area[1].length; z++) {
				if (area[x][z] == 1) {
					if (Math.abs(x - (area.length / 2)) == city.getPathExtends() + 1 &&
						Math.abs(z - (area[1].length /2)) == city.getPathExtends() + 1) {
						//do nothing for now
					}
					else if(Math.abs(x - (area.length / 2)) == (city.getPathExtends() + 1) ||
                            Math.abs(z - (area[1].length / 2)) == (city.getPathExtends() + 1)) {
						if(city.hasMainStreets() && multipleNeighbouringPaths(area, x, z)) {
							BlockPos pos = new BlockPos(city.getEdgeLength() + x, 0, city.getEdgeLength() + z ).add(city.getStartingPos());
							BlockHelper.spawnOrEnqueue(pos, city.getPathBlock().getDefaultState());
						}
					}
					else {
						BlockPos pos = new BlockPos(city.getEdgeLength() + x, 0, city.getEdgeLength() + z ).add(city.getStartingPos());
						BlockHelper.spawnOrEnqueue(pos, city.getPathBlock().getDefaultState());
					}	
				}
			}
		}
		
		if (city.hasMainStreets()) {
			for (int a = 0; a < area.length; a++) {
				if (a % 8 == 0) {
					//npcs?
				}
				for (int c = -city.getPathExtends(); c <= city.getPathExtends(); c++) {
					area[a][((area.length - 1) / 2) + c] = 1;
					area[((area.length - 1) / 2) + c][a] = 1;
				}
			}
		}
	}

	private static boolean multipleNeighbouringPaths(int[][] area, int x, int z) {
		int paths = 0;
		
		if (x == 0 || area[x-1][z] == 1) {
			paths++;
		}
		if (z == 0 || area[x][z-1] == 1) {
			paths++;
		}
		if(x == (area.length - 1) || area[x+1][z] == 1) {
			paths++;
		}
		if (z == (area[1].length - 1) || area[x][z+1] == 1) {
			paths++;
		}
		
		return paths == 4;
	}
	
	private static boolean isValidBuilding(Building buildingCheck, ArrayList<Integer> buildings, int[][] area,
			int startX, int startZ, int sizeX, int sizeZ) {
		
		switch (buildingCheck.getFrequency()) {
			case VERY_COMMON:
			case COMMON:
				return true;
			case AVERAGE:
			case RARE:
			case VERY_RARE:
				if(RandomHelper.nextDouble() > 0.975) {
					return true;
				}
				if (buildings.contains(buildingCheck.getID())) {
					return false;
				}
				else {
					int distance = 0;
					switch (buildingCheck.getFrequency()) {
						case AVERAGE:
							distance = 12;
							break;
						case RARE:
							distance = 25;
							break;
						case VERY_RARE:
							distance = 50;
							break;
						default:
							break;
					}
					for (int x = startX - distance; x < startX + sizeX + distance; x++) {
                        for (int z = startZ - distance; z < startZ + sizeZ + distance; z++) {
                            if (x >= 0 && z >= 0 && x <= (area.length - 1) && z <= (area[1].length - 1)) {
                                if (area[x][z] - 100 == buildingCheck.getID()) {
                                    return false;
                                }
                            }
                        }
                    }
					return true;
				}
			default:
				TwitterCity.logger.debug("Unknown frequency type encountered");
		}
		return false;
	}
}
