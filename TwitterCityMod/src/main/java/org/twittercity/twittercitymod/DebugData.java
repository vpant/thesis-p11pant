package org.twittercity.twittercitymod;

import org.twittercity.twittercitymod.city.Building;
import org.twittercity.twittercitymod.city.Frequency;
import org.twittercity.twittercitymod.util.RandomHelper;

public class DebugData {
	private static Building building1,building2,building3,building4,building5,building6,building7,building8,building9,building10;
	public static Building[] buildings = new Building[10];
	public static void setupData()
	{
		//name, sourceX, sourceStartY, sourceZ, frequency, ID, sizeX, sizeZ, pattern, posX, posZ
		building1 = new Building("coau14's House",89,63,99,Frequency.AVERAGE,0,11,11,0,0);
		building2 = new Building("Brick House",62,58,41,Frequency.VERY_COMMON,1,8,8,0,0);
		building3 = new Building("Poor House",49,63,33,Frequency.VERY_COMMON,2,6,6,0,0);
		building4 = new Building("Garden Hous",100,57,69,Frequency.VERY_COMMON,3,12,12,0,0);
		building5 = new Building("Tall House",79,63,53,Frequency.VERY_COMMON,4,10,10,0,0);
		building6 = new Building("Wooden House",100,63,105,Frequency.RARE,5,12,12,0,0);
		building7 = new Building("Tavern",100,40,57,Frequency.RARE,6,12,12,0,0);
		building8 = new Building("Blacksmith",79,63,93,Frequency.RARE,7,10,10,0,0);
		building9 = new Building("Rich House 2",79,63,33,Frequency.VERY_COMMON,8,10,10,0,0);
		building10 = new Building("Rich House 1",100,63,45,Frequency.VERY_COMMON,9,12,12,0,0);
		
		buildings[0] = building1;
		buildings[1] = building1;
		buildings[2] = building1;
		buildings[3] = building1;
		buildings[4] = building1;
		buildings[5] = building1;
		buildings[6] = building1;
		buildings[7] = building1;
		buildings[8] = building1;
		buildings[9] = building1;
		
		//listBuildings.add(building1);
		//listBuildings.add(building2);
		//listBuildings.add(building3);
		//listBuildings.add(building4);
		//listBuildings.add(building5);
		//listBuildings.add(building6);
		//listBuildings.add(building7);
		//listBuildings.add(building8);
		//listBuildings.add(building9);
		//listBuildings.add(building10);
		
	}
	
	public static Building selectRandomBuilding ()
	{
		int fail = 0;
		Frequency[] frequencies = Frequency.values();
		int[] frequencyWeights = new int[] { 9, 8, 7, 6, 5 };
		
		int frequency = RandomHelper.randomWeightedNumber(frequencyWeights);
		int building = 0;
		boolean valid;
		
		do {
			if (++fail >= 100) {
				frequency = RandomHelper.randomWeightedNumber(frequencyWeights);
				fail = 0;
			}
			building = RandomHelper.nextInt(buildings.length);
			
			valid = buildings[building].getFrequency() == frequencies[frequency];
		} while(!valid);
		
		return buildings[building];
	}
}
