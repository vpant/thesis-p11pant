package org.twittercity.twittercitymod.city;

public class Building {
	private String name;
	private int sourceX;
	private int sourceStartY;
	private int sourceZ;
	private Frequency frequency;
	
	private int ID;
	
	private int sizeX;
	private int sizeZ;
	private int posX;
	private int posZ;
	
	
	public Building(String name, int sourceX, int sourceStartY, int sourceZ, Frequency frequency, int ID, int sizeX, int sizeZ, int posX,int posZ)
	{
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
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSourceX() {
		return sourceX;
	}
	public void setSourceX(int sourceX) {
		this.sourceX = sourceX;
	}
	public int getSourceStartY() {
		return sourceStartY;
	}
	public void setSourceStartY(int sourceStartY) {
		this.sourceStartY = sourceStartY;
	}
	public int getSourceZ() {
		return sourceZ;
	}
	public void setSourceZ(int sourceZ) {
		this.sourceZ = sourceZ;
	}
	public Frequency getFrequency() {
		return frequency;
	}
	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getSizeX() {
		return sizeX;
	}
	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}
	public int getSizeZ() {
		return sizeZ;
	}
	public void setSizeZ(int sizeZ) {
		this.sizeZ = sizeZ;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosZ() {
		return posZ;
	}
	public void setPosZ(int posZ) {
		this.posZ = posZ;
	}
}
