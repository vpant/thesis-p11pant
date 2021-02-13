package org.twittercity.twittercitymod.tickhandlers;

import java.util.List;
import java.util.stream.Collectors;

import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.blocks.TCBlock;
import org.twittercity.twittercitymod.config.ConfigurationManager;
import org.twittercity.twittercitymod.data.world.BuildingQueuesWorldData;
import org.twittercity.twittercitymod.data.world.CityWorldData;
import org.twittercity.twittercitymod.util.BlockData;
import org.twittercity.twittercitymod.util.BlockHelper;
import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;

import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class LazyBlockProcessTickHandler {

	private int blocksToProcess = 0;
	
	@SubscribeEvent
	public void onServerTickProcessBlock(TickEvent.ServerTickEvent event) {
		World twitterWorld = DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID);
		BuildingQueuesWorldData data = BuildingQueuesWorldData.get(twitterWorld);
		// Priority to blocks than needs to be destroyed
		if(!data.isListEmpty(false)) {
			TwitterCity.logger.info("MESA STO !IS LIST EMPTY gia destroy blocks!");
			processBlocks(twitterWorld, data, false);
		} 
		else if(!data.isListEmpty(true)) {
			TwitterCity.logger.info("MESA STO !IS LIST EMPTY gia spawn blocks!");
			processBlocks(twitterWorld, data, true);
		} 
		else {
			TwitterCity.logger.info("MESA STO ELSE");
			CityWorldData citiesData = CityWorldData.get(twitterWorld);
			processBuildLast(twitterWorld, data, citiesData);
		}
	}
	
	private void processBuildLast(World world, BuildingQueuesWorldData worldData, CityWorldData citiesData) {
		blocksToProcess += ConfigurationManager.buildingOptions.blocksPerTick;
		while(blocksToProcess > 1) {
			blocksToProcess--;
			List<Integer> citiesId = getFinishedCitiesIds(citiesData);
			BlockData bd = worldData.pollFromBuildLastForCityId(citiesId);
			if(bd == null) break;
			spawnBlock(world, bd);
		}
	}

	private List<Integer> getFinishedCitiesIds(CityWorldData citiesData) {
		return citiesData.getCities().entrySet().stream()
				.filter(entry -> entry.getValue().getIsCityCompleted()).map(entry -> entry.getValue().getId())
				.collect(Collectors.toList());
	}

	private void processBlocks(World world, BuildingQueuesWorldData worldData, boolean toSpawn) {
		blocksToProcess += ConfigurationManager.buildingOptions.blocksPerTick;
		while(blocksToProcess > 1) {
			blocksToProcess--;
			
			BlockData bd = worldData.pollFromList(toSpawn);
			if(bd != null) {
				if(toSpawn) {
					spawnBlock(world, bd);
				}
				else {
					destroyBlock(world, bd);
				}
			}
		}
		
	}


	private void destroyBlock(World world, BlockData bd) {
		if(bd != null) {
			System.out.println("MESA STO DESTROYBLOCK");
			world.destroyBlock(bd.pos, false);
		}
	}

	private void spawnBlock(World world, BlockData bd) {
		System.out.println("MESA STO SPAWNBLOCK");
		world.setBlockState(bd.pos, bd.blockState, bd.flags);
		if(bd.shouldNotifyNeighbors) {
			world.notifyNeighborsRespectDebug(bd.pos, bd.blockState.getBlock(), false);
		}
		if(bd.blockState.getBlock() instanceof TCBlock) {
			BlockHelper.setBlockTileData(bd, world);
		}
	}
}
