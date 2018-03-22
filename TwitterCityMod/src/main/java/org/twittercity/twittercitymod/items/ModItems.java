package org.twittercity.twittercitymod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

	public static ItemBase debugItem = new DebugItem("debug").setCreativeTab(CreativeTabs.MISC);
	
	public static void register(IForgeRegistry<Item> registry) {
		registry.registerAll(debugItem);
	}
	
	public static void registerModels() {
		debugItem.registerItemModel();
	}
}
