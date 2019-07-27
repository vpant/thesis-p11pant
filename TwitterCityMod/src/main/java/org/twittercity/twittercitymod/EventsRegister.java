package org.twittercity.twittercitymod;

import org.twittercity.twittercitymod.blocks.ModBlocks;
import org.twittercity.twittercitymod.items.ModItems;
import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class EventsRegister {

	@Mod.EventBusSubscriber
	public static class RegistrationHandler {

		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			ModItems.register(event.getRegistry());
		}
		
		@SubscribeEvent
		public static void registerItems(ModelRegistryEvent event) {
			ModItems.registerModels();
		}
		
		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			ModBlocks.register(event.getRegistry());
		}
		
		@SubscribeEvent
		public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
			if(!event.player.inventory.hasItemStack(new ItemStack(ModItems.debugItem))) {
				event.player.inventory.addItemStackToInventory(new ItemStack(ModItems.debugItem));
			}	
		}
		

		@SubscribeEvent
		public static void onEvent(EntityTravelToDimensionEvent event) {
			if(event.getDimension() == TwitterCityWorldGenReference.DIM_ID) {
				Entity player = event.getEntity();
				if(player instanceof EntityPlayer) {
					player.sendMessage(new TextComponentString(I18n.format("twittercity.teleport.welcome")));
				}
			}
		}
	}
}
