package org.twittercity.twittercitymod.city.templatestructures;

import org.twittercity.twittercitymod.Reference;
import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.city.Building;
import org.twittercity.twittercitymod.city.Buildings;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

public class TemplateBuildings {
	
	private static TemplateBuildings instance = null;
	
	private TemplateBuildings() {
		
	}
	
	public void spawnTemplateBuildings(World worldIn) {
		MinecraftServer minecraftServer = worldIn.getMinecraftServer();
		for(Building building : Buildings.getAllBuildings()) {
			if(building != null) {	
				Template template = (((WorldServer)worldIn).getStructureTemplateManager()).getTemplate(minecraftServer, new ResourceLocation(Reference.MOD_ID, building.getTemplateFileName()));
				template.addBlocksToWorld(worldIn, new BlockPos(building.getSourceX(), building.getSourceStartY(), building.getSourceZ()), new PlacementSettings());
			}
		}
	}
	
	/*
	 * Returns the TemplateStructure data object from a Template
	 */
	public TemplateStructure getSingleBuildingTemplateStructure(World worldIn, Building building) {
		MinecraftServer minecraftServer = worldIn.getMinecraftServer();
		TwitterCityTemplate template = getTemplateForBuilding(minecraftServer, building);
		if(building != null) {
			return template.getTemplateStructureFromTemplate(worldIn, new BlockPos(building.getSourceX(), building.getSourceStartY(), building.getSourceZ()), new PlacementSettings());
		}
		return null;
	}
	
	private TwitterCityTemplate getTemplateForBuilding(MinecraftServer minecraftServer, Building building) {
		TwitterCityTemplate template = null;
		if(building != null) {
			ResourceLocation res = new ResourceLocation(Reference.MOD_ID, building.getTemplateFileName());
			TwitterCityTemplateManager tm = new TwitterCityTemplateManager(res.toString(), new DataFixer(1343));				
			template = tm.get(minecraftServer, res);
		}
		else {
			TwitterCity.logger.error("Building object is null");
		}
		
		return template;
	}
	
	public static TemplateBuildings getInstance() {
		return (instance == null) ? new TemplateBuildings() : instance;
	}
}
