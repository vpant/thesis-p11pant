package org.twittercity.twittercitymod.proxy;

import org.twittercity.twittercitymod.Reference;
import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.city.BuildingReference;
import org.twittercity.twittercitymod.city.chunkpregen.PreGenTickHandler;
import org.twittercity.twittercitymod.commands.TwitterCityCmdTeleport;
import org.twittercity.twittercitymod.concurrency.ExecutorProvider;
import org.twittercity.twittercitymod.concurrency.InitRunnable;
import org.twittercity.twittercitymod.data.db.Tweet;
import org.twittercity.twittercitymod.registrationhandlers.TCBlocksRegistrationHandler;
import org.twittercity.twittercitymod.registrationhandlers.TCItemsRegistrationHandler;
import org.twittercity.twittercitymod.tickhandlers.TickHandler;
import org.twittercity.twittercitymod.tileentity.TileEntityTwitter;
import org.twittercity.twittercitymod.worldgen.TwitterCityBiomes;
import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;
import org.twittercity.twittercitymod.worldgen.WorldTypeTwitterCity;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Code executes Both Client and Server side.
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
    	TCBlocksRegistrationHandler.init();
    	TCItemsRegistrationHandler.init();
		TwitterCity.logger = e.getModLog();
		TwitterCityWorldGenReference.registerDimensions();
		GameRegistry.registerTileEntity(TileEntityTwitter.class, new ResourceLocation(Reference.MOD_ID, "twitter_tile_entity"));
		
		// Tick Handlers
		MinecraftForge.EVENT_BUS.register(new PreGenTickHandler());
		MinecraftForge.EVENT_BUS.register(new TickHandler());
    }

    public void init(FMLInitializationEvent e) {
		TwitterCityWorldGenReference.registerWorldGenerators();
		TwitterCityBiomes.initBiomeManagerAndDictionary();
		new WorldTypeTwitterCity();
    }

    public void postInit(FMLPostInitializationEvent e) {
    	ExecutorProvider.getExecutorService().execute(new InitRunnable());
    }
    
  
	public void serverStarting (FMLServerStartingEvent e){
		e.registerServerCommand(new TwitterCityCmdTeleport());
	}

	public void serverStopping(FMLServerStoppedEvent e) {
		TwitterCity.logger.info("Clearing tweets list.");
		BuildingReference.tweetsToBuild.clear();
	}
	
	public void registerItemRenderer(Item item, int meta, String id) {
		
	}
	
	// Proxied method to open tweet GUI. Opening a GUI is client side code so we override this method to ClientProxy
	public Runnable openTweetGUI(Tweet tweet) {
		return null;
	}

	public void openTweetLoadingGUI(int tweetID) {
		
	}
}
