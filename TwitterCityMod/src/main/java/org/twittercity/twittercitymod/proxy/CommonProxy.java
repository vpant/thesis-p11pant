package org.twittercity.twittercitymod.proxy;

import org.twittercity.twittercitymod.DebugData;
import org.twittercity.twittercitymod.Reference;
import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.commands.TwitterCityCmdTeleport;
import org.twittercity.twittercitymod.registrationhandlers.TCBlocksRegistrationHandler;
import org.twittercity.twittercitymod.registrationhandlers.TCItemsRegistrationHandler;
import org.twittercity.twittercitymod.tileentity.TileEntityTwitter;
import org.twittercity.twittercitymod.worldgen.TwitterCityBiomes;
import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;
import org.twittercity.twittercitymod.worldgen.WorldTypeTwitterCity;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
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
		
		DebugData.setupData(); // Initialize debug data to use throughout the mod
    }

    /**
     * Calls the {@link RegisterHelper#initRegister()} to register the blocks items. It registers the {@link GuiHandler} class as well.
     */
    public void init(FMLInitializationEvent e) {
		TwitterCityWorldGenReference.registerWorldGenerators();
		TwitterCityBiomes.initBiomeManagerAndDictionary();
		new WorldTypeTwitterCity();
    }
    
    /**
     * Registers the {@link EventsHandler} class.
     */
    public void postInit(FMLPostInitializationEvent e) {
    	
    }
    
    /**
     * Register the {@link TeleportCommand} class.
     */
	public void serverStarting (FMLServerStartingEvent e){
		e.registerServerCommand(new TwitterCityCmdTeleport());
	}
	
	public void registerItemRenderer(Item item, int meta, String id) {
		
	}
}
