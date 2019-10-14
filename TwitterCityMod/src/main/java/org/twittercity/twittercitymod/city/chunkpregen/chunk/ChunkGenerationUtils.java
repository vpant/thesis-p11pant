package org.twittercity.twittercitymod.city.chunkpregen.chunk;

import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.city.City;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.storage.RegionFileCache;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.DimensionManager;

public class ChunkGenerationUtils {

	@SuppressWarnings("deprecation")
	private static boolean chunksExist(MinecraftServer server, int x, int z, int dimensionID) {
		WorldServer world = DimensionManager.getWorld(dimensionID);
		ChunkProviderServer chunkProvider = server.getWorld(dimensionID).getChunkProvider();
		return chunkProvider.chunkExists(x, z) || RegionFileCache
				.createOrLoadRegionFile(world.getChunkSaveLocation(), x, z).chunkExists(x & 0x1F, z & 0x1F);
	}

	public static boolean generateChunk(MinecraftServer server, int x, int z, int dimensionID) {
		ChunkProviderServer chunkProvider = server.getWorld(dimensionID).getChunkProvider();
		if (chunkProvider.chunkExists(x, z)) {
			return false;
		}
		if (chunkProvider.getLoadedChunkCount() > ChunkPreGenReference.maxChunksLoaded) {
			chunkProvider.saveChunks(true);
			chunkProvider.queueUnloadAll();
		}
		chunkProvider.provideChunk(x, z);
		return true;
	}

	public static int queueChunkGeneration(MinecraftServer server, int x, int z, int xSize, int zSize, int dimensionID,
			boolean logToChat) {
		final int xmax = x + xSize;
		final int xmin = x - xSize;
		final int zmax = z + zSize;
		final int zmin = z - zSize;
		for (int xPos = xmin; xPos < xmax; xPos++) {
			for (int zPos = zmin; zPos < zmax; zPos++) {
				if (!chunksExist(server, xPos, zPos, dimensionID)) {
					ChunkPreGenReference.toGenerate.add(new ChunkPosition(xPos, zPos, dimensionID, logToChat));
				} else {
					TwitterCity.logger.info("Chunk exists");
				}
			}
		}
		ChunkPreGenReference.toGenerate.sort(ChunkPosition.byAngleComparator(new ChunkPosition(x, z, dimensionID, logToChat)));
		return ChunkPreGenReference.startingSize = ChunkPreGenReference.toGenerate.size();
	}
	
	public static int queueCityChunkGeneration(MinecraftServer server, City city, int dimensionID, boolean logToChat) {
		return queueChunkGeneration(server, city.getStartingPos().getX(), city.getStartingPos().getZ(), city.getChunkLength(), city.getChunkLength(), dimensionID, logToChat);
	}
}
