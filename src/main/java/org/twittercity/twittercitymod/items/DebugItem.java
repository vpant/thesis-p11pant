package org.twittercity.twittercitymod.items;

import java.util.ArrayList;

import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.city.CitiesManager;
import org.twittercity.twittercitymod.data.db.Tweet;

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
		if (!worldIn.isRemote) {
			ArrayList<Tweet> tweets = new ArrayList<>();
			for(int i = 0; i < 10000; i++) {
				tweets.add(new Tweet());
			}
			//TwitterCity.logger.info(CitiesManager.getInstance().startBuilding(tweets));
		}		
		return new ActionResult<>(EnumActionResult.PASS, itemstack);
	}
	
}
