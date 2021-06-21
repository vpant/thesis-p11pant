package org.twittercity.twittercitymod.city.chunkpregen;

import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.city.chunkpregen.chunk.ChunkGenerationUtils;
import org.twittercity.twittercitymod.city.chunkpregen.chunk.ChunkPosition;
import org.twittercity.twittercitymod.city.chunkpregen.chunk.ChunkPreGenReference;
import org.twittercity.twittercitymod.data.world.CityWorldData;
import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PreGenTickHandler {

	private double chunkQueue = 0;
	private int chunksGenerated = 0;

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {
		final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

		if (!ChunkPreGenReference.toGenerate.isEmpty()) {
			chunkQueue += ChunkPreGenReference.numChunksPerTick;
			while (chunkQueue > 1) {
				chunkQueue--;
				chunksGenerated++;
				ChunkPosition cp = ChunkPreGenReference.toGenerate.poll();
				if (cp != null) {
					if (ChunkGenerationUtils.generateChunk(server, cp.getX(), cp.getZ(), cp.getDimensionID())) {
						double completedPercentage = 100
								- ((double) ChunkPreGenReference.toGenerate.size() / (double) ChunkPreGenReference.startingSize) * 100;
						if (chunksGenerated % ChunkPreGenReference.updateDelay == 0) {
							TwitterCity.logger.info(String.format("Generation [x:%3d z:%3d][DIM:%s] %.3f%% completed",
									cp.getX(), cp.getZ(), getChunkDescription(cp), completedPercentage));
						}
						if (ChunkPreGenReference.toGenerate.peek() == null) {
							TextComponentTranslation chatTranslation = new TextComponentTranslation("chunkgen.success");
							server.sendMessage(chatTranslation);
							CityWorldData.get(DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID))
									.getCity(cp.getCityID()).areChunksPregenerated(true);
							server.saveAllWorlds(true);
							ChunkPreGenReference.chunkGenerationInProgress = false;
						}
					}
				} else {
					ChunkPreGenReference.chunkGenerationInProgress = false;
				}
			}
		}
	}

	private Object getChunkDescription(ChunkPosition cp) {
		return DimensionManager.getProviderType(cp.getDimensionID()) != null
				? DimensionManager.getProviderType(cp.getDimensionID()).getName()
				: cp.getDimensionID();
	}

}
