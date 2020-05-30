package org.twittercity.twittercitymod.data.world;

import java.util.HashMap;
import java.util.Map;

import org.twittercity.twittercitymod.Reference;
import org.twittercity.twittercitymod.city.City;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class CityWorldData extends WorldSavedData {

	private static final String DATA_NAME = Reference.MOD_ID + "_CityData";
	private static CityWorldData instance;
	
	private Map<Integer,City> cities = new HashMap<Integer,City>();
	
	
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
			cities.put(Integer.valueOf(city.getId()), city);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		
		NBTTagList citiesTag = new NBTTagList();
		
		for(Map.Entry<Integer,City> citySet : cities.entrySet()) {
			citiesTag.appendTag(citySet.getValue().writeToNBT());
		}
		
		compound.setTag("Cities", citiesTag);
		
		return compound;
	}

	public void setCity(City city) {
		cities.put(Integer.valueOf(city.getId()), city);
		markDirty();
	}
	
	public void setCity(HashMap<Integer,City> cities) {
		this.cities = cities;
		markDirty();
	}
	
	public City getCity(int id) {
		return getCity(Integer.valueOf(id));
	}
	
	public City getCity(Integer id) {
		return cities.get(id);
	}
	
	public HashMap<Integer,City> getCities() {
		return (HashMap<Integer, City>) this.cities;
	}

	public City getFirstCity() {
		return getCity(0);
	}
}
