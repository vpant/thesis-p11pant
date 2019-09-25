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

	private int blocksToSpawn = 0;
	private int blocksSpawned = 0;
	
	private int tcBlocksSpawned = 0;
	
	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {
		if(LazyBlockSpawnReference.isLazySpawnPaused) return;
		
		World twitterWorld = DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID);
		
		if(!LazyBlockSpawnReference.toSpawn.isEmpty()) {
			blocksToSpawn += ConfigurationManager.buildingOptions.blocksPerTick;
			double completedPercentage = 100 - ((double) LazyBlockSpawnReference.toSpawn.size() / (double) LazyBlockSpawnReference.startingSize) * 100;
			while(blocksToSpawn > 1) {
				blocksToSpawn--;
				blocksSpawned++;
				
				BlockData bd = LazyBlockSpawnReference.toSpawn.poll();
				
				if(bd != null) {
					twitterWorld.setBlockState(bd.pos, bd.blockState, bd.flags);
					if(bd.shouldNotifyNeighbors) {
						twitterWorld.notifyNeighborsRespectDebug(bd.pos, bd.blockState.getBlock(), false);
					}
					BlockHelper.setBlockTileData(bd, twitterWorld);
					if(bd.blockState.getBlock() instanceof TCBlock) {
						tcBlocksSpawned++;
					}
					if (blocksSpawned % LazyBlockSpawnReference.updateDelay == 0) {
                        TwitterCity.logger.info(String.format("Spawn completed [x:%3d z:%3d] %.3f%% completed", bd.pos.getX(), bd.pos.getZ(), completedPercentage));  
                    }
				}
			}
			//TwitterCity.logger.info("Number of blocks spawned: {}", tcBlocksSpawned);
		}
	}
	
}
