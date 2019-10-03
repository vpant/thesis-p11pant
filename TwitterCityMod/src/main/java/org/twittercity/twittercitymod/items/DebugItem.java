package org.twittercity.twittercitymod.items;

import org.twittercity.twittercitymod.city.CitiesManager;
import org.twittercity.twittercitymod.data.db.Tweet;
import org.twittercity.twittercitymod.data.world.ConstructionWorldData;
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
		if (!worldIn.isRemote) {
			Tweet[] tweets = new Tweet[200000];
			for(int i = 0; i < 200000; i++) {
				tweets[i] = new Tweet();
			}
			CitiesManager.getInstance().startBuilding(tweets);
			//System.out.println(ConstructionWorldData.get(DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID)).toString());
			
		}		
		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
	}
	
}
