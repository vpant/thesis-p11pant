package org.twittercity.twittercitymod.items;

import java.util.List;

import org.twittercity.twittercitymod.DebugData;
import org.twittercity.twittercitymod.city.Buildings;
import org.twittercity.twittercitymod.city.ChunksEditor;
import org.twittercity.twittercitymod.city.Paths;
import org.twittercity.twittercitymod.city.templatestructures.TemplateStructure;
import org.twittercity.twittercitymod.city.templatestructures.TwitterCityTemplate;
import org.twittercity.twittercitymod.worldgen.TwitterCityWorldGenReference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class DebugItem extends ItemBase{

	public DebugItem(String name) {
		super(name);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		int[][] area = null;
		if (!worldIn.isRemote) {
			World twitterWorld = DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID);
			//MinecraftServer server = playerIn.getServer();
			//ChunksEditor.makeChunksFlat(worldIn, Blocks.BEDROCK, 0, 0, 10);
			//if(ChunkPreGenReference.isPreGenFinished) {
				//TwitterCity.logger.info("Mpika");
				ChunksEditor.makeFlatChunksForCity(twitterWorld, DebugData.firstCity);
			//CityWorldData ws = CityWorldData.get(twitterWorld);
			area = Paths.makePaths(twitterWorld, DebugData.firstCity);
			//DebugData.firstCity.setCityArea(area);
			//DebugData.secondCity.setCityArea(area);
			//ws.setCity(DebugData.firstCity);
			//ws.setCity(DebugData.secondCity);
			
			//TwitterCity.logger.info("TwitterCity id: {}", ws.getCity(1).toString());
			//TwitterCity.logger.info("TwitterCity id: {}", ws.getCity(2).toString());
			
				//ws.setArea(area);
				Buildings.makeInsideCity(twitterWorld, area, DebugData.firstCity);
			//}
			
			//TemplateBuildings.getInstance().spawnTemplateBuildings(worldIn);
			
			//ArrayUtils.print2DArrayToFile(area);
			
			//for(Building building : DebugData.buildings) {
			//	spawnBlocksFromBlockInfoList(worldIn, TemplateBuildings.getInstance().getSingleBuildingTemplateStructure(worldIn, building));
			//}
			
			//City city = DebugData.firstCity;
			//int cityStartX = city.getStartingPos().getX();
			//int cityStartZ = city.getStartingPos().getZ();
			//TwitterCity.logger.info("City length is: " + city.getCityLength());
			//ChunkGenerationUtils.queueChunkGeneration(server, cityStartX, cityStartZ, (city.getCityLength() / 16), (city.getCityLength() / 16), TwitterCityWorldGenReference.DIM_ID, true);
			
			//TemplateStructure ts = TemplateBuildings.getInstance().getSingleBuildingTemplateStructure(worldIn, DebugData.buildings[3]);
			//System.out.println(ts.getSize().toString());
			//playerIn.sendMessage(new TextComponentString("BlockStone is instanceof TCAbstractBlock: " + Boolean.toString((TwitterCityBlocks.STONE instanceof TCAbstractBlock))));
		}

		
		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
	}
	
	
	public void spawnBlocksFromBlockInfoList(World world, TemplateStructure tmpst) {
		List<TwitterCityTemplate.BlockInfo> list = tmpst.getBlockInfoList();
		if(!list.isEmpty()) {
			for(TwitterCityTemplate.BlockInfo block : list) {
				world.setBlockState(block.pos, block.blockState, 4);
			}
		}
	}
	
}
