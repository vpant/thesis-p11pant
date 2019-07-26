package org.twittercity.twittercitymod.commands;

import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;
import org.twittercity.twittercitymod.worldgen.teleport.TeleportationTools;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class TwitterCityCmdTeleport extends AbstractTwitterCityCommand {
	@Override
	public String getName() {
		return "tc";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "Teleports you to Twitter City dimension.";
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
		/*if (args.length < 4) {
			ITextComponent component = new TextComponentString(TextFormatting.RED + "Several parameters are missing!");
			super.sendMessage(sender, component);
			return;
		} else if (args.length > 4) {
			ITextComponent component = new TextComponentString(TextFormatting.RED + "Too many parameters!");
			super.sendMessage(sender, component);
			return;
		}*/

		int dim = TwitterCityWorldGenReference.DIM_ID;//fetchInt(sender, args, 1, 0);
		int x = fetchInt(sender, args, 2, 0);
		int y = fetchInt(sender, args, 3, 100);
		int z = fetchInt(sender, args, 4, 0);

		if (sender instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) sender;

			int currentId = player.getEntityWorld().provider.getDimension();
			if (currentId != dim) {
				TeleportationTools.teleportToDimension(player, dim, x, y, z); // Maybe teleport to first town coordinates
			} else {
				TeleportationTools.teleportToDimension(player, -1, x, y, z);
				//player.setPositionAndUpdate(x, y, z);
			}
		}

	}



}
