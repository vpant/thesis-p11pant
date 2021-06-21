package org.twittercity.twittercitymod.data.world;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.twittercity.twittercitymod.Reference;
import org.twittercity.twittercitymod.tickhandlers.ConstructionPriority;
import org.twittercity.twittercitymod.util.BlockData;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class BuildingQueuesWorldData extends WorldSavedData {
	private static final String DATA_NAME = Reference.MOD_ID + "_BlockQueueData";
	private static BuildingQueuesWorldData instance;

	private Map<ConstructionPriority, LinkedList<BlockData>> toDestroy = new TreeMap<>();
	private Map<ConstructionPriority, LinkedList<BlockData>> toSpawn = new TreeMap<>();
	private Map<Integer, LinkedList<BlockData>> toBuildLast = new TreeMap<>();

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

        return instance;
    }
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList destroyNBTList = (NBTTagList)nbt.getTag("destroyList");
		NBTTagList spawnNBTList = (NBTTagList)nbt.getTag("spawnList");
		
		LinkedList<BlockData> toSpawnBlocks = new LinkedList<>();
		LinkedList<BlockData> toDestroyBlocks = new LinkedList<>();
		for(int i = 0; i < destroyNBTList.tagCount(); i++) {
			BlockData bd = new BlockData(destroyNBTList.getCompoundTagAt(i));
			toDestroyBlocks.add(bd);
		}
		
		for(int i = 0; i < spawnNBTList.tagCount(); i++) {
			BlockData bd = new BlockData(spawnNBTList.getCompoundTagAt(i));
			toSpawnBlocks.add(bd);
		}

		toDestroy = mapByConstructionPriority(toDestroyBlocks);
		toSpawn = mapByConstructionPriority(toSpawnBlocks);
		LinkedList<BlockData> lastToBeBuilt = toSpawn.remove(ConstructionPriority.BUILD_LAST);
		toBuildLast = lastToBeBuilt != null ?
				lastToBeBuilt.stream()
				.collect(Collectors.groupingBy(blockData -> blockData.cityId, Collectors.toCollection(LinkedList::new)))
				: null;
	}


	private Map<ConstructionPriority, LinkedList<BlockData>> mapByConstructionPriority(LinkedList<BlockData> blockDataCollection) {
		return blockDataCollection.stream()
				.collect(Collectors.groupingBy(blockData -> blockData.constructionPriority, Collectors.toCollection(LinkedList::new)));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagList destroyNBTList = new NBTTagList();
		NBTTagList spawnNBTList = new NBTTagList();
		
		addBuildLastToSpawnBlocks();

		for(BlockData destoyBlock : mapToList(this.toDestroy)) {
			destroyNBTList.appendTag(destoyBlock.writeToNBT());
		}
		for(BlockData spawnBlock : mapToList(this.toSpawn)) {
			spawnNBTList.appendTag(spawnBlock.writeToNBT());
		}
		
		compound.setTag("destroyList", destroyNBTList);
		compound.setTag("spawnList", spawnNBTList);
		
		return compound;
	}
	
	private void addBuildLastToSpawnBlocks() {
		this.toBuildLast.forEach((key, value) -> {
			if (this.toSpawn.containsKey(ConstructionPriority.BUILD_LAST)) {
				this.toSpawn.get(ConstructionPriority.BUILD_LAST).addAll(value);
			} else {
				this.toSpawn.put(ConstructionPriority.BUILD_LAST, value);
			}
		});
	}

	private LinkedList<BlockData> mapToList(Map<ConstructionPriority, LinkedList<BlockData>> blockDataByCityIdMap) {
		LinkedList<BlockData> blockDataList = new LinkedList<>();
		blockDataByCityIdMap.forEach((key, value) -> blockDataList.addAll(value));
		return blockDataList;
	}

	public void addToSpawnList(BlockData element) {
		if(toSpawn.containsKey(element.constructionPriority)) {
			toSpawn.get(element.constructionPriority).add(element);
		} else {
			LinkedList<BlockData> blockDataList = new LinkedList<>();
			blockDataList.add(element);
			toSpawn.put(element.constructionPriority, blockDataList);
		}
	}

	public void addToDestroyList(BlockData element) {
		if(toDestroy.containsKey(element.constructionPriority)) {
			toDestroy.get(element.constructionPriority).add(element);
		} else {
			LinkedList<BlockData> blockDataList = new LinkedList<>();
			blockDataList.add(element);
			toDestroy.put(element.constructionPriority, blockDataList);
		}
	}

	public BlockData pollFromList(boolean fromSpawnList) {
		BlockData data = getFromMapByPriority(fromSpawnList);
		this.markDirty();
		return data;
	}
	
	private BlockData getFromMapByPriority(boolean fromSpawnList) {
		if(fromSpawnList) {
			if(toSpawn.containsKey(ConstructionPriority.BUILD_FIRST) && !toSpawn.get(ConstructionPriority.BUILD_FIRST).isEmpty()) {
				return toSpawn.get(ConstructionPriority.BUILD_FIRST).poll();
			}
			else if(toSpawn.containsKey(ConstructionPriority.BUILD_NORMAL)) {
				return toSpawn.get(ConstructionPriority.BUILD_NORMAL).poll();
			}
		} else {
			if(toDestroy.containsKey(ConstructionPriority.BUILD_FIRST) && !toDestroy.get(ConstructionPriority.BUILD_FIRST).isEmpty()) {
				return toDestroy.get(ConstructionPriority.BUILD_FIRST).poll();
			}
			else if(toDestroy.containsKey(ConstructionPriority.BUILD_NORMAL)) {
				return toDestroy.get(ConstructionPriority.BUILD_NORMAL).poll();
			}
		}
		return null;
	}

	public BlockData pollFromBuildLastForCityId(List<Integer> citiesIds) {
		Integer cityId = firstAvailableCityId(citiesIds);
		BlockData bd = cityId != null ? toBuildLast.get(cityId).poll() : null;
		this.markDirty();
		return bd;
	}

	/**
	 * From a list of finished cities get the first found id that is available in the buildLast list
	 */
	private Integer firstAvailableCityId(List<Integer> citiesIds) {
		return citiesIds.stream().filter(cityId -> toBuildLast.get(cityId) != null).findFirst().orElse(null);
	}

	public boolean isListEmpty(boolean spawnList) {
		if(spawnList) {
			return toSpawn.isEmpty();
		}
		else {
			return toDestroy.isEmpty();
		}
	}
}
