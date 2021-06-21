package org.twittercity.twittercitymod.data.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import org.twittercity.twittercitymod.Reference;
import org.twittercity.twittercitymod.city.City;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CityWorldData extends WorldSavedData {

	private static final String DATA_NAME = Reference.MOD_ID + "_CityData";
	private static CityWorldData instance;
	
	private List<City> cities = new LinkedList<>();
	
	
	public CityWorldData() {
		super(DATA_NAME);
	}
	
	public CityWorldData(String name) {
		super(name);
	}

    public static CityWorldData get(World world) {
        MapStorage storage = world.getMapStorage();
        instance = (CityWorldData) storage.getOrLoadData(CityWorldData.class, DATA_NAME);
       
        if (instance == null) {
        	instance = new CityWorldData();
            storage.setData(DATA_NAME, instance);
        }
        return instance;
    }
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList citiesTag = (NBTTagList) nbt.getTag("Cities");
		
		for(int i = 0; i < citiesTag.tagCount(); i++) {
			City city = new City((NBTTagCompound) citiesTag.get(i));
			cities.add(city);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		
		NBTTagList citiesTag = new NBTTagList();
		
		for(City city : cities) {
			citiesTag.appendTag(city.writeToNBT());
		}
		
		compound.setTag("Cities", citiesTag);
		
		return compound;
	}

	public void addCity(City city) {
		cities.add(city);
		markDirty();
	}
	
//	public void setCity(List<City> cities) {
//		this.cities = cities;
//		markDirty();
//	}

	public City getCity(Integer id) {
		return cities.stream().filter(city -> id.equals(city.getSettings().getId())).findFirst().orElse(null);
	}
	
	public List<City> getCities() {
		return this.cities;
	}

	public List<City> getUnfinishedCities() {
		return cities.stream()
				.filter(city -> !city.isCityCompleted())// && city.areBuildingsFinished())
				.collect(Collectors.toList());
	}
}
