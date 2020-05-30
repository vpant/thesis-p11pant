package org.twittercity.twittercitymod.city;

public enum BuildingType {

	BILLS_HOUSE("Bill's House",89,63,99,Frequency.AVERAGE,0,11,11,0,0,"bills_house"),
	BRICK_HOUSE("Brick House",62,58,41,Frequency.VERY_COMMON,1,8,8,0,0,"brick_house"),
	POOR_HOUSE("Poor House",49,63,33,Frequency.VERY_COMMON,2,6,6,0,0,"poor_house"),
	GARDEN_HOUSE("Garden House",100,58,69,Frequency.VERY_COMMON,3,12,12,0,0,"garden_house"),
	TALL_HOUSE("Tall House",79,63,53,Frequency.VERY_COMMON,4,10,10,0,0,"tall_house"),
	WOODEN_HOUSE("Wooden House",100,63,105,Frequency.RARE,5,12,12,0,0,"wooden_house"),
	TAVERN("Tavern",100,58,57,Frequency.RARE,6,12,12,0,0,"tavern"),
	BLACKSMITH("Blacksmith",79,63,93,Frequency.RARE,7,10,10,0,0,"blacksmith"),
	RICH_HOUSE_2("Rich House 2",79,63,33,Frequency.VERY_COMMON,8,10,10,0,0,"rich_house_2"),
	RICH_HOUSE_1("Rich House 1",100,63,45,Frequency.VERY_COMMON,9,12,12,0,0,"rich_house_1"),
	LIBRARY("Library",112,63,46,Frequency.AVERAGE,10,13,13,0,0,"library");
	
	public String name;
	public int sourceX;
	public int sourceStartY;
	public int sourceZ;
	public Frequency frequency;
	public int ID;
	public int sizeX;
	public int sizeZ;
	public int posX;
	public int posZ;
	
	public String templateFileName;
	
	private BuildingType(String name, int sourceX, int sourceStartY, int sourceZ, Frequency frequency, int ID, int sizeX, int sizeZ, int posX,int posZ, String fileName) {
		this.name = name;
		this.sourceX = sourceX;
		this.sourceStartY = sourceStartY;
		this.sourceZ = sourceZ;
		this.frequency = frequency;
		this.ID = ID;
		this.sizeX = sizeX;
		this.sizeZ = sizeZ;
		this.posX = posX;
		this.posZ = posZ;
		this.templateFileName = fileName;
	}

	public static BuildingType forBuildingID(int buildingID) {
		for(BuildingType buildingType: values()) {
            if (buildingType.ID == buildingID) {
            	return buildingType;
            }          
        }
		return BILLS_HOUSE;
	}
	
}
