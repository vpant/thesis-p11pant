package org.twittercity.twittercitymod.city;

import org.twittercity.twittercitymod.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class TemplateBuildings {
	
	private static TemplateBuildings instance = null;
	
	private TemplateBuildings() {
		
	}
	
	public void spawnTemplateBuildings(World worldIn) {
		WorldServer worldserver = (WorldServer) worldIn;
		MinecraftServer minecraftserver = worldIn.getMinecraftServer();
		TemplateManager templatemanager = worldserver.getStructureTemplateManager();	
		for(Building building : Buildings.getAllBuildings()) {
			ResourceLocation res = new ResourceLocation(Reference.MOD_ID, building.getTemplateFileName());
			Template template = templatemanager.get(minecraftserver, res);
			
			template.addBlocksToWorld(worldIn, new BlockPos(building.getSourceX(), building.getSourceStartY(), building.getSourceZ()), new PlacementSettings());
		}
	}
	
	public static TemplateBuildings getInstance() {
		return (instance == null) ? new TemplateBuildings() : instance;
	}
	
}
