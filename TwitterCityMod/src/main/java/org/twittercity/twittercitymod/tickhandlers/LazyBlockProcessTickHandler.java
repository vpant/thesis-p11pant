package org.twittercity.twittercitymod.tickhandlers;

import org.twittercity.twittercitymod.blocks.TCBlock;
import org.twittercity.twittercitymod.city.BuildingReference;
import org.twittercity.twittercitymod.config.ConfigurationManager;
import org.twittercity.twittercitymod.data.world.BuildingQueuesWorldData;
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
		if(!data.isListEmpty(true) && !BuildingReference.cityPreparationActive) {
			processBlocks(twitterWorld, data, true);
		} 
		else if(!data.isListEmpty(false)) {
			processBlocks(twitterWorld, data, false);
		}
		else {
			BuildingReference.cityPreparationActive = false;
		}
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
			world.destroyBlock(bd.pos, false);
		}
	}

	private void spawnBlock(World world, BlockData bd) {
		world.setBlockState(bd.pos, bd.blockState, bd.flags);
		if(bd.shouldNotifyNeighbors) {
			world.notifyNeighborsRespectDebug(bd.pos, bd.blockState.getBlock(), false);
		}
		if(bd.blockState.getBlock() instanceof TCBlock) {
			BlockHelper.setBlockTileData(bd, world);
			//tcBlocksSpawned++;
		}
	}
}
