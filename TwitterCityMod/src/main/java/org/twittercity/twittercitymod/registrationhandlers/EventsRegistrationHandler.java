package org.twittercity.twittercitymod.registrationhandlers;

import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber
public class EventsRegistrationHandler {

	@SubscribeEvent
	public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
		if (!event.player.inventory.hasItemStack(new ItemStack(TCItemsRegistrationHandler.debugItem))) {
			event.player.inventory.addItemStackToInventory(new ItemStack(TCItemsRegistrationHandler.debugItem));
		}
	}

	@SubscribeEvent
	public static void onTravelToDimension(EntityTravelToDimensionEvent event) {
		if (event.getDimension() == TwitterCityWorldGenReference.DIM_ID) {
			Entity player = event.getEntity();
			if (player instanceof EntityPlayer) {
				player.sendMessage(new TextComponentString(I18n.format("twittercity.teleport.welcome")));
			}
		}
	}
	
}
