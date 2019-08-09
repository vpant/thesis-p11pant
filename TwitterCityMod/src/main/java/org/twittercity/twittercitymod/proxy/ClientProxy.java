package org.twittercity.twittercitymod.proxy;

import org.twittercity.twittercitymod.Reference;
import org.twittercity.twittercitymod.data.Tweet.Tweet;
import org.twittercity.twittercitymod.gui.TCGuiTweet;

import net.minecraft.client.Minecraft;
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
	public void serverStarting(FMLServerStartingEvent e)
	{
		super.serverStarting(e);
	}
    
    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
    	ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Reference.MOD_ID + ":" + id, "inventory"));
    }

	@Override
	public void openTweetGUI() {
		Minecraft.getMinecraft().displayGuiScreen(new TCGuiTweet(new Tweet()));
		//Minecraft.getMinecraft().displayGuiScreen(new TCPopupMessage("ola good", "kati"));
	}
    
    
}
