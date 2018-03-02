package org.twittercity.twittercity.proxy;

import org.twittercity.twittercity.Reference;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

/**
 * Code executes only in Client Side
 */
public class ClientProxy extends CommonProxy {

	@Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
    
    @Override
	public void serverLoad (FMLServerStartingEvent e)
	{
		super.serverLoad(e);
	}
    
    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
    	ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Reference.MOD_ID + ":" + id, "inventory"));
    }
}
