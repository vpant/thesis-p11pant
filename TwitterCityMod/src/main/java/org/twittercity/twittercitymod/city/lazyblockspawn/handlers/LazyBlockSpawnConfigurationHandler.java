package org.twittercity.twittercitymod.city.lazyblockspawn.handlers;

import java.io.File;

import org.twittercity.twittercitymod.city.lazyblockspawn.LazyBlockSpawnReference;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LazyBlockSpawnConfigurationHandler {
	public static Configuration configuration;
	
	public static void init(File configFile) {
		if(configuration == null) { 
			configuration = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	@SubscribeEvent
	public void onConfigurationChangedEvent(OnConfigChangedEvent event) {
		if(event.getModID().equalsIgnoreCase("chunkgen")) {
			loadConfiguration();
		}
	}
	
	private static void loadConfiguration() {
		LazyBlockSpawnReference.blocksPerTick = configuration.get(Configuration.CATEGORY_GENERAL, "blocksPerTick", 10, "Number of blocks spawned per tick").getInt();
		//LazyBlockSpawnReference.maxChunksLoaded = configuration.get(Configuration.CATEGORY_GENERAL, "maxChunksLoaded", 3000, "Pause chunk generation if more chunks than this are in memory").getInt();
		//LazyBlockSpawnReference.numChunksPerTick = configuration.get(Configuration.CATEGORY_GENERAL, "numChunksPerTick", 1.0, "Number of chunks loaded per tick").getDouble();
		//LazyBlockSpawnReference.updateDelay = configuration.get(Configuration.CATEGORY_GENERAL, "updateDelay", 40, "Number of chunks inbetween percentage updates").getInt();
		
		if(configuration.hasChanged()) {
			configuration.save();
		}
	}
}
