package org.twittercity.twittercitymod.items;

import java.util.List;

import org.twittercity.twittercitymod.city.templatestructures.TemplateStructure;
import org.twittercity.twittercitymod.city.templatestructures.TwitterCityTemplate;

import net.minecraft.entity.player.EntityPlayer;
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
		if (!worldIn.isRemote) {
			//World twitterWorld = DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID);
			//ChunksEditor.makeChunksFlat(worldIn, Blocks.BEDROCK, 0, 0, 10);
			//ChunksEditor.makeFlatChunksForCity(twitterWorld, DebugData.firstCity);
			//TemplateBuildings.getInstance().spawnTemplateBuildings(worldIn);
			//area = Paths.makePaths(twitterWorld, DebugData.firstCity);
			//ArrayUtils.print2DArrayToFile(area);
			//Buildings.makeInsideCity(twitterWorld, area, DebugData.firstCity);
			//for(Building building : DebugData.buildings) {
			//	spawnBlocksFromBlockInfoList(worldIn, TemplateBuildings.getInstance().getSingleBuildingTemplateStructure(worldIn, building));
			//}
			
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
