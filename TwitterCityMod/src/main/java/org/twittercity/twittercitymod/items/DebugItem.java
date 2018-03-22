package org.twittercity.twittercitymod.items;

import org.twittercity.twittercitymod.city.Paths;
import org.twittercity.twittercitymod.util.ArrayUtils;

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
		if (!worldIn.isRemote)
		{
			area = Paths.MakePaths(worldIn);
			ArrayUtils.print2DArrayToFile(area);
		}
		
		
		
		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
	}
}
