package org.twittercity.twittercitymod.commands;

import org.twittercity.twittercitymod.DebugData;
import org.twittercity.twittercitymod.city.City;
import org.twittercity.twittercitymod.teleport.TeleportationTools;
import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;

import net.minecraft.client.resources.I18n;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class TwitterCityCmdTeleport extends AbstractTwitterCityCommand {
	@Override
	public String getName() {
		return "tc";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "Teleports you to Twitter City dimension if you are not there and two overworld if you are there.";
	}
	
	// Maybe add twitter city id you want to teleport and defaulting to 1
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) {

		// TO-DO Get city id or default as 1
		City city = DebugData.firstCity;
		
		int twitterCityDim = TwitterCityWorldGenReference.DIM_ID;
		// Maybe make the default value to First city spawn point
		int x = fetchInt(sender, args, 2, city.getStartingPos().getX()); 
		int y = fetchInt(sender, args, 3, 80);
		int z = fetchInt(sender, args, 4, city.getStartingPos().getZ());

		if (sender instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) sender;

			int currentId = player.getEntityWorld().provider.getDimension();
			if (currentId != twitterCityDim) {
				// Maybe teleport to first town coordinates
				TeleportationTools.teleportToDimension(player, twitterCityDim, x, y, z); 
				player.sendMessage(new TextComponentString(I18n.format("twittercity.teleport.welcome")));
			} else {
				BlockPos spawnPoint = player.getBedLocation(0); //Teleport to players spawn point
				if(spawnPoint != null) {
					TeleportationTools.teleportToDimension(player, 0, spawnPoint.getX(), spawnPoint.getY(), spawnPoint.getZ());
				} else {
					TeleportationTools.teleportToDimension(player, 0, 0, 63, 0);
					BlockPos worldSpawn = player.getEntityWorld().getSpawnPoint();
					player.setPositionAndUpdate(worldSpawn.getX(), 70, worldSpawn.getZ());
				}	
			}
		}
	}
}
