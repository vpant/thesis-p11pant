package org.twittercity.twittercitymod;

import org.twittercity.twittercitymod.city.Building;
import org.twittercity.twittercitymod.city.Buildings;
import org.twittercity.twittercitymod.city.City;
import org.twittercity.twittercitymod.city.Frequency;
import org.twittercity.twittercitymod.util.RandomHelper;

public class DebugData {
	public static Building[] buildings = new Building[11];
	public static City firstCity = new City();
	
	// This is the equivalent SourceWorld.GetBuildings (returns the array of buildings) that in mace are read from xml file. The selectRandomBuilding
	// should encapsulated to Buildings class.
	public static void setupData() {
		//name, sourceX, sourceStartY, sourceZ, frequency, ID, sizeX, sizeZ, posX, posZ		
		buildings[0] = new Building("Bill's House",89,63,99,Frequency.AVERAGE,0,11,11,0,0,"bills_house"); // TELEIO NO SIGNS
		buildings[1] = new Building("Brick House",62,58,41,Frequency.VERY_COMMON,1,8,8,0,0,"brick_house"); // TELEIO NO SIGNS
		buildings[2] = new Building("Poor House",49,63,33,Frequency.VERY_COMMON,2,6,6,0,0,"poor_house"); // TELEIO NO SIGNS
		buildings[3] = new Building("Garden House",100,58,69,Frequency.VERY_COMMON,3,12,12,0,0,"garden_house"); // TELEIO NO SIGNS
		buildings[4] = new Building("Tall House",79,63,53,Frequency.VERY_COMMON,4,10,10,0,0,"tall_house"); // TELEIO NO SIGNS
		buildings[5] = new Building("Wooden House",100,63,105,Frequency.RARE,5,12,12,0,0,"wooden_house"); // TELEIO NO SIGNS
		buildings[6] = new Building("Tavern",100,58,57,Frequency.RARE,6,12,12,0,0,"tavern"); // TELEIO NO SIGNS 
		buildings[7] = new Building("Blacksmith",79,63,93,Frequency.RARE,7,10,10,0,0,"blacksmith");// TELEIO NO SIGNS 
		buildings[8] = new Building("Rich House 2",79,63,33,Frequency.VERY_COMMON,8,10,10,0,0,"rich_house_2"); // TELEIO NO SIGNS
		buildings[9] = new Building("Rich House 1",100,63,45,Frequency.VERY_COMMON,9,12,12,0,0,"rich_house_1"); // TELEIO NO SIGNS 
		buildings[10] = new Building("Library",112,63,46,Frequency.AVERAGE,10,13,13,0,0,"library"); // TELEIO NO SIGNS 
		
		firstCity.setId(1);
		firstCity.setEdgeLength(8);
		firstCity.setCityLength(17); // * 16 internally
		firstCity.setMapLength(firstCity.getCityLength() + (firstCity.getEdgeLength() * 2));
		firstCity.setPathExtends(2);
		firstCity.setX(1000);
		firstCity.setZ(1000);
	}
	
}
