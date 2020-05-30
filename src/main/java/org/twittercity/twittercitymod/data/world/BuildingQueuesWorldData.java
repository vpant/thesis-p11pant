package org.twittercity.twittercitymod.data.world;

import java.util.LinkedList;
import java.util.List;

import org.twittercity.twittercitymod.Reference;
import org.twittercity.twittercitymod.city.BuildingReference;
import org.twittercity.twittercitymod.util.BlockData;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class BuildingQueuesWorldData extends WorldSavedData {
	private static final String DATA_NAME = Reference.MOD_ID + "_BlockQueueData";
	private static BuildingQueuesWorldData instance;

	private LinkedList<BlockData> toDestroy = new LinkedList<>();
	private LinkedList<BlockData> toSpawn = new LinkedList<>();

	public BuildingQueuesWorldData() {
		super(DATA_NAME);
	}
	
	public BuildingQueuesWorldData(String name) {
		super(name);
	}
	
	public static BuildingQueuesWorldData get(World world) {
        MapStorage storage = world.getMapStorage();
        instance = (BuildingQueuesWorldData) storage.getOrLoadData(BuildingQueuesWorldData.class, DATA_NAME);
        if (instance == null) {
        	instance = new BuildingQueuesWorldData();
            storage.setData(DATA_NAME, instance);
        }
        if(!instance.toDestroy.isEmpty()) {
        	BuildingReference.cityPreparationActive = true;
        }

        return instance;
    }
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList destroyNBTList = (NBTTagList)nbt.getTag("destroyList");
		NBTTagList spawnNBTList = (NBTTagList)nbt.getTag("spawnList");
		
		for(int i = 0; i < destroyNBTList.tagCount(); i++) {
			BlockData bd = new BlockData(destroyNBTList.getCompoundTagAt(i));
			this.toDestroy.add(bd);
		}
		
		for(int i = 0; i < spawnNBTList.tagCount(); i++) {
			BlockData bd = new BlockData(spawnNBTList.getCompoundTagAt(i));
			this.toSpawn.add(bd);
		}	
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagList destroyNBTList = new NBTTagList();
		NBTTagList spawnNBTList = new NBTTagList();
		
		for(BlockData destoyBlock : this.toDestroy) {
			destroyNBTList.appendTag(destoyBlock.writeToNBT());
		}
		for(BlockData spawnBlock : this.toSpawn) {
			spawnNBTList.appendTag(spawnBlock.writeToNBT());
		}
		
		compound.setTag("destroyList", destroyNBTList);
		compound.setTag("spawnList", spawnNBTList);
		
		return compound;
	}
	
	public void addToList(BlockData element, boolean toSpawnList) {
		if(toSpawnList) {
			this.toSpawn.add(element);
		}
		else {
			this.toDestroy.add(element);
		}
		this.markDirty();
	}
	
	public void addAllToList(List<BlockData> list, boolean toSpawnList) {
		if(toSpawnList) {
			this.toSpawn.addAll(list);
		}
		else {
			this.toDestroy.addAll(list);
		}
		this.markDirty();
	}
	
	public BlockData pollFromList(boolean fromSpawnList) {
		BlockData data;
		if(fromSpawnList) {
			data = this.toSpawn.poll();
		}
		else {
			data = this.toDestroy.poll();
		}
		this.markDirty();
		return data;
	}

	public boolean isListEmpty(boolean spawnList) {
		if(spawnList) {
			return this.toSpawn.isEmpty();
		}
		else {
			return this.toDestroy.isEmpty();
		}
	}
}
