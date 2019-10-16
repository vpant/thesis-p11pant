package org.twittercity.twittercitymod.city.lazyblockspawn;

import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.blocks.TCBlock;
import org.twittercity.twittercitymod.config.ConfigurationManager;
import org.twittercity.twittercitymod.util.BlockData;
import org.twittercity.twittercitymod.util.BlockHelper;
import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;

import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class LazyBlockSpawnTickHandler {

	private int blocksToProcess = 0;
	private int blocksProcessed = 0;
	
	private int tcBlocksSpawned = 0;
	
	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {

		World twitterWorld = DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID);
		if(!LazyBlockSpawnReference.toSpawn.isEmpty()) {
			spawnBlocks(twitterWorld);
		}
	}
	

	public void onServerTickDestroyBlock(TickEvent.ServerTickEvent event) {
		World twitterWorld = DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID);
		if(!LazyBlockDestroyReference.toDestroy.isEmpty()) {
			destroyBlocks(twitterWorld);
		}
		else {
			LazyBlockDestroyReference.destroyInProgress = false;
		}
	}
	
	private void spawnBlocks(World twitterWorld) {
		blocksToProcess += ConfigurationManager.buildingOptions.blocksPerTick;
		double completedPercentage = 100 - ((double) LazyBlockSpawnReference.toSpawn.size()) / ((double) LazyBlockSpawnReference.startingSize) * 100;
		while(blocksToProcess > 1) {
			blocksToProcess--;
			blocksProcessed++;
			
			BlockData bd = LazyBlockSpawnReference.toSpawn.poll();
			if(bd != null) {
				twitterWorld.setBlockState(bd.pos, bd.blockState, 10);
				if(bd.shouldNotifyNeighbors) {
					twitterWorld.notifyNeighborsRespectDebug(bd.pos, bd.blockState.getBlock(), false);
				}
				BlockHelper.setBlockTileData(bd, twitterWorld);
				if(bd.blockState.getBlock() instanceof TCBlock) {
					tcBlocksSpawned++;
				}
			}
			
			if (blocksProcessed % LazyBlockSpawnReference.updateDelay == 0 && bd != null) {
                TwitterCity.logger.info(String.format("Spawn completed [x:%3d z:%3d] %.3f%% completed", bd.pos.getX(), bd.pos.getZ(), completedPercentage));  
                TwitterCity.logger.info("Number of blocks spawned: {}", tcBlocksSpawned);
			}
		}
	}
	
	private void destroyBlocks(World twitterWorld) {
		blocksToProcess += ConfigurationManager.buildingOptions.blocksPerTick;
		double completedPercentage = 100 - ((double) LazyBlockDestroyReference.toDestroy.size()) / (double) LazyBlockDestroyReference.destroyStartingSize * 100;
		while(blocksToProcess > 1) {
			blocksToProcess--;
			blocksProcessed++;
			
			BlockData bd = LazyBlockDestroyReference.toDestroy.poll();
			if(bd != null) {
				twitterWorld.destroyBlock(bd.pos, false);
			}
			
			if (blocksProcessed % LazyBlockDestroyReference.updateDelay == 0 && bd != null) {
                TwitterCity.logger.info(String.format("Destroy completed [x:%3d z:%3d] %.3f%% completed", bd.pos.getX(), bd.pos.getZ(), completedPercentage));  
			}
		}
	}

}
