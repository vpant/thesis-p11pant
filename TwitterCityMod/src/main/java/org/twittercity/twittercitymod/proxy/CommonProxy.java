package org.twittercity.twittercitymod.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

/**
 * Code executes Both Client and Server side.
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {

    }

    /**
     * Calls the {@link RegisterHelper#initRegister()} to register the blocks items. It registers the {@link GuiHandler} class as well.
     */
    public void init(FMLInitializationEvent e) {
    }
    
    /**
     * Registers the {@link EventsHandler} class.
     */
    public void postInit(FMLPostInitializationEvent e) {
    	
    }
    
    /**
     * Register the {@link TeleportCommand} class.
     */
	public void serverLoad (FMLServerStartingEvent e)
	{
		
	}
	
	public void registerItemRenderer(Item item, int meta, String id) {
		
	}
}
