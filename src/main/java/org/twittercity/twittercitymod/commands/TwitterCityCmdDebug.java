package org.twittercity.twittercitymod.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import org.twittercity.twittercitymod.city.City;
import org.twittercity.twittercitymod.data.world.CityWorldData;
import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;

import java.util.List;

public class TwitterCityCmdDebug extends AbstractTwitterCityCommand {
	@Override
	public String getName() {
		return "tcd";
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
		final String command = fetchString(sender, args, 0, "");
		final World world = DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID);
		final List<City> cities = CityWorldData.get(world).getCities();
		ITextComponent comp;
		switch (command) {
			case "count":
				comp = new TextComponentString(String.valueOf(cities.size()));
				sender.sendMessage(comp);
				break;
			case "city":
				int cityId = fetchInt(sender, args, 1, 0);
				comp = getCityInformation(cityId);
				sender.sendMessage(comp);
				break;

		}
	}

	private ITextComponent getCityInformation(int cityId) {
		final World world = DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID);
		final City city = CityWorldData.get(world).getCity(cityId);
		return new TextComponentString(city.getSettings().toString());
	}
}
