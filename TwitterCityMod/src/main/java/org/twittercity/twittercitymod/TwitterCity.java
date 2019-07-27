package org.twittercity.twittercitymod;

import org.apache.logging.log4j.Logger;
import org.twittercity.twittercitymod.blocks.ModBlocks;
import org.twittercity.twittercitymod.items.ModItems;
import org.twittercity.twittercitymod.proxy.CommonProxy;
import org.twittercity.twittercitymod.worldgen.TwitterCityBiomes;
import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;
import org.twittercity.twittercitymod.worldgen.WorldTypeTwitterCity;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 * This class is the main class of our mod. When minecraft is executed the methods {@link TwitterCity#preInit}, {@link TwitterCity#init} and {@link TwitterCity#postInit}
 * are executed as well to initialize our mod to FML (Forge Mod Loader).
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class TwitterCity {
	
	public static Logger logger;
	
	/** The instance of our mod. */
	@Instance(Reference.MOD_ID)
	public static TwitterCity instance;
	
	/** Instance of our proxy. */
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	/** Calls the preInit in our proxy package to execute the code needed when minecraft is loading, in the side (Client or Server) is should to execute. */
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		logger = e.getModLog();
		proxy.preInit(e);
		
		TwitterCityWorldGenReference.registerDimensions();
	}
	/** Calls the init in our proxy package to execute the code needed when minecraft is loading, in the side (Client or Server) is should to execute. */
	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
		
		TwitterCityWorldGenReference.registerWorldGenerators();
		TwitterCityBiomes.initBiomeManagerAndDictionary();
		new WorldTypeTwitterCity();
	}
	
	/** Calls the postInit in our proxy package to execute the code needed when minecraft is loading, in the side (Client or Server) is should to execute. */
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
	    proxy.postInit(e);
	}
	
	/** This method executes when a server is loaded. */
	@EventHandler
	public void serverStarting (FMLServerStartingEvent e)
	{
		proxy.serverStarting(e);
	}
	
}
