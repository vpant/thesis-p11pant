package org.twittercity.twittercitymod.registrationhandlers;

import org.twittercity.twittercitymod.Reference;
import org.twittercity.twittercitymod.items.DebugItem;
import org.twittercity.twittercitymod.items.ItemBase;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class TCItemsRegistrationHandler {
	public static ItemBase debugItem = new DebugItem("debug").setCreativeTab(CreativeTabs.MISC);
	public static void init() {
		debugItem = (ItemBase) new DebugItem("debug").setCreativeTab(CreativeTabs.MISC);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(debugItem);
	}
	
	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event) {
		registerRender(debugItem, 0, new ResourceLocation("tc", "debug"));
	}
	
	public static void registerRender(Item item, int meta, ResourceLocation rs) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(rs, "inventory"));
	}
}
