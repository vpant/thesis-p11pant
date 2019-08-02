package org.twittercity.twittercitymod;

import org.twittercity.twittercitymod.city.Building;
import org.twittercity.twittercitymod.city.City;
import org.twittercity.twittercitymod.city.Frequency;

import net.minecraft.util.math.BlockPos;

public class DebugData {
	public static Building[] buildings = new Building[11];
	public static City firstCity = new City();
	
	// This is the equivalent SourceWorld.GetBuildings (returns the array of buildings) that in mace are read from xml file. The selectRandomBuilding
	// should encapsulated to Buildings class.
	public static void setupData() {
		//name, sourceX, sourceStartY, sourceZ, frequency, ID, sizeX, sizeZ, posX, posZ		
		buildings[0] = new Building("Bill's House",89,63,99,Frequency.AVERAGE,0,11,11,0,0,"bills_house");
		buildings[1] = new Building("Brick House",62,58,41,Frequency.VERY_COMMON,1,8,8,0,0,"brick_house");
		buildings[2] = new Building("Poor House",49,63,33,Frequency.VERY_COMMON,2,6,6,0,0,"poor_house");
		buildings[3] = new Building("Garden House",100,58,69,Frequency.VERY_COMMON,3,12,12,0,0,"garden_house");
		buildings[4] = new Building("Tall House",79,63,53,Frequency.VERY_COMMON,4,10,10,0,0,"tall_house");
		buildings[5] = new Building("Wooden House",100,63,105,Frequency.RARE,5,12,12,0,0,"wooden_house");
		buildings[6] = new Building("Tavern",100,58,57,Frequency.RARE,6,12,12,0,0,"tavern");
		buildings[7] = new Building("Blacksmith",79,63,93,Frequency.RARE,7,10,10,0,0,"blacksmith");
		buildings[8] = new Building("Rich House 2",79,63,33,Frequency.VERY_COMMON,8,10,10,0,0,"rich_house_2");
		buildings[9] = new Building("Rich House 1",100,63,45,Frequency.VERY_COMMON,9,12,12,0,0,"rich_house_1");
		buildings[10] = new Building("Library",112,63,46,Frequency.AVERAGE,10,13,13,0,0,"library");
		
		firstCity.setId(1);
		firstCity.setEdgeLength(8);
		firstCity.setCityLength(7); // * 16 internally
		firstCity.setMapLength(firstCity.getCityLength() + (firstCity.getEdgeLength() * 2));
		firstCity.setPathExtends(2);
		firstCity.setStartingPos(new BlockPos(1000,63,1000)); // It should stay 63 because it would cause problems with template structures
	}
	
}
