package org.twittercity.twittercitymod;

import org.twittercity.twittercitymod.city.Building;
import org.twittercity.twittercitymod.city.Buildings;
import org.twittercity.twittercitymod.city.City;
import org.twittercity.twittercitymod.city.Frequency;
import org.twittercity.twittercitymod.util.RandomHelper;

public class DebugData {
	private static Building building1,building2,building3,building4,building5,building6,building7,building8,building9,building10;
	public static Building[] buildings = new Building[10];
	public static City firstCity = new City();
	
	// This is the equivalent SourceWorld.GetBuildings (returns the array of buildings) that in mace are read from xml file. The selectRandomBuilding
	// should encapsulated to Buildings class.
	public static void setupData() {
		//name, sourceX, sourceStartY, sourceZ, frequency, ID, sizeX, sizeZ, pattern, posX, posZ		
		buildings[0] = new Building("coau14's House",89,63,99,Frequency.AVERAGE,0,11,11,0,0);
		buildings[1] = new Building("Brick House",62,58,41,Frequency.VERY_COMMON,1,8,8,0,0);
		buildings[2] = new Building("Poor House",49,63,33,Frequency.VERY_COMMON,2,6,6,0,0);
		buildings[3] = new Building("Garden Hous",100,57,69,Frequency.VERY_COMMON,3,12,12,0,0);
		buildings[4] = new Building("Tall House",79,63,53,Frequency.VERY_COMMON,4,10,10,0,0);
		buildings[5] = new Building("Wooden House",100,63,105,Frequency.RARE,5,12,12,0,0);
		buildings[6] = new Building("Tavern",100,40,57,Frequency.RARE,6,12,12,0,0);
		buildings[7] = new Building("Blacksmith",79,63,93,Frequency.RARE,7,10,10,0,0);
		buildings[8] = new Building("Rich House 2",79,63,33,Frequency.VERY_COMMON,8,10,10,0,0);
		buildings[9] = new Building("Rich House 1",100,63,45,Frequency.VERY_COMMON,9,12,12,0,0);
		
		firstCity.setId(1);
		firstCity.setEdgeLenght(8);
		firstCity.setCityLength(5 * 16);
		firstCity.setMapLength(firstCity.getCityLength() + (firstCity.getEdgeLenght() * 2));
		firstCity.setPathExtends(2);
		
	}
	
}
