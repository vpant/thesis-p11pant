package org.twittercity.twittercitymod.items;

import java.util.List;

import org.twittercity.twittercitymod.DebugData;
import org.twittercity.twittercitymod.city.Building;
import org.twittercity.twittercitymod.city.Buildings;
import org.twittercity.twittercitymod.city.ChunksEditor;
import org.twittercity.twittercitymod.city.Paths;
import org.twittercity.twittercitymod.city.templatestructures.TemplateBuildings;
import org.twittercity.twittercitymod.city.templatestructures.TemplateStructure;
import org.twittercity.twittercitymod.city.templatestructures.TwitterCityTemplate;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class DebugItem extends ItemBase{

	public DebugItem(String name) {
		super(name);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		int[][] area = null;
		//playerIn.sendMessage(new TextComponentString("Kati ginete. " + template.getAuthor()));
		if (!worldIn.isRemote) {
			DebugData.setupData();	
			//ChunksEditor.makeChunksFlat(worldIn, Blocks.BEDROCK, 0, 0, 10);
			ChunksEditor.makeFlatChunksForCity(worldIn, DebugData.firstCity);
			//TemplateBuildings.getInstance().spawnTemplateBuildings(worldIn);
			area = Paths.makePaths(worldIn, DebugData.firstCity);
			//ArrayUtils.print2DArrayToFile(area);
			Buildings.makeInsideCity(worldIn, area, DebugData.firstCity);
			//for(Building building : DebugData.buildings) {
			//	spawnBlocksFromBlockInfoList(worldIn, TemplateBuildings.getInstance().getSingleBuildingTemplateStructure(worldIn, building));
			//}
			
			//TemplateStructure ts = TemplateBuildings.getInstance().getSingleBuildingTemplateStructure(worldIn, DebugData.buildings[3]);
			//System.out.println(ts.getSize().toString());
		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
	}
	
	
	public void spawnBlocksFromBlockInfoList(World world, TemplateStructure tmpst) {
		List<TwitterCityTemplate.BlockInfo> list = tmpst.getBlockInfoList();
		if(!list.isEmpty()) {
			for(TwitterCityTemplate.BlockInfo block : list) {
				//world.setBlockState(block.pos, block.blockState);
				world.setBlockState(block.pos, block.blockState,4 );
			}
		}
	}
}
