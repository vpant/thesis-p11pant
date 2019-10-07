package org.twittercity.twittercitymod.registrationhandlers;

import org.twittercity.twittercitymod.Reference;
import org.twittercity.twittercitymod.blocks.TCBlock;
import org.twittercity.twittercitymod.config.ConfigurationManager;
import org.twittercity.twittercitymod.gui.TCGuiTweet;
import org.twittercity.twittercitymod.gui.TCGuiTweetLoading;
import org.twittercity.twittercitymod.util.BlockHelper;
import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
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

	@SubscribeEvent
	public static void onBreakEvent(BlockEvent.BreakEvent event) {
		if (ConfigurationManager.buildingOptions.unbreakableCity.isEnabled()
				&& ((event.getState().getBlock() instanceof TCBlock)
						|| BlockHelper.isTCBlockNeighbor(event.getWorld(), event.getPos()))) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(Reference.MOD_ID)) {
			ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
		}
	}

	@SubscribeEvent
	public static void onGuiOpen(GuiOpenEvent event) {
		if (event.getGui() instanceof TCGuiTweet) {
			if (!(Minecraft.getMinecraft().currentScreen instanceof TCGuiTweetLoading)) {
				event.setCanceled(true);
			}
		}
	}
}
