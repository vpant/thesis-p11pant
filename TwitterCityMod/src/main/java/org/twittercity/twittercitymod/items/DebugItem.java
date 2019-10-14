package org.twittercity.twittercitymod.items;

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
			Tweet[] tweets = new Tweet[500];
			for(int i = 0; i < 500; i++) {
				tweets[i] = new Tweet();
			}
			//CitiesManager.getInstance().startBuilding(tweets);
			//for(int i = 0; i<=16; i++) {
				CitiesManager.getInstance().createNewCity();
				
			//}
			//TwitterCity.logger.info("The calculation of cityLength is: {}", CitySettings.getCitySizeFromCityLength(128, 8));//RandomHelper.nextInt(2,6)));
						
		}	
		
		
		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
	}
	
}
