package org.twittercity.twittercitymod.commands;

import org.apache.commons.lang3.ArrayUtils;
import org.twittercity.twittercitymod.city.CitiesManager;
import org.twittercity.twittercitymod.city.City;
import org.twittercity.twittercitymod.teleport.TeleportationTools;
import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class TwitterCityCmdTeleport extends AbstractTwitterCityCommand {
	@Override
	public String getName() {
		return "tc";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "Teleports you to Twitter City dimension if you are not there and two overworld if you are there.";
	}	


	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}

	// Maybe add twitter city id you want to teleport and defaulting to 1
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
		int cityID = fetchInt(sender, args, 0, 0);
		City city = CitiesManager.getInstance().getCity(cityID);
		if (sender instanceof EntityPlayer) {			
			EntityPlayer player = (EntityPlayer) sender;
			if(city != null) {
				teleportPlayerToCity(player, city);
			}
			else if(cityID >= 0) {
				ITextComponent comp;
				if(ArrayUtils.isEmpty(args)) {
					comp = new TextComponentTranslation("twittercity.teleport.nocityexists");
				}
				else {
					comp = new TextComponentTranslation("twittercity.teleport.nosuchcity").appendText(" " + String.valueOf(cityID));
				}
				player.sendMessage(comp);
			}
		}
	}
	
	private void teleportPlayerToCity(EntityPlayer player, City city) {
		int currentId = player.getEntityWorld().provider.getDimension();
		int twitterCityDim = TwitterCityWorldGenReference.DIM_ID;
		BlockPos cityStartingPos = city.getStartingPos();
		if (currentId != twitterCityDim) {
			// Maybe teleport to first town coordinates
			TeleportationTools.teleportToDimension(player, twitterCityDim, cityStartingPos.add(2, 2, 2), EnumFacing.EAST); 
			player.sendMessage(new TextComponentTranslation("twittercity.teleport.welcome"));
		} else {
			BlockPos spawnPoint = player.getBedLocation(0); //Teleport to players spawn point
			if(spawnPoint != null) {
				TeleportationTools.teleportToDimension(player, 0, spawnPoint);
			} else {
				TeleportationTools.teleportToDimension(player, 0, new BlockPos(0, 63, 0));
				BlockPos worldSpawn = player.getEntityWorld().getSpawnPoint();
				player.setPositionAndUpdate(worldSpawn.getX(), 70, worldSpawn.getZ());
			}	
		}
	}
}
