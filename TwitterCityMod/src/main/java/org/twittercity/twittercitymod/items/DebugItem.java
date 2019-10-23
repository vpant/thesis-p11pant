package org.twittercity.twittercitymod.items;

import java.util.ArrayList;

import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.city.BuildingReference;
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
			ArrayList<Tweet> tweets = new ArrayList<Tweet>();
			for(int i = 0; i < 50000; i++) {
				tweets.add(new Tweet());
			}
			//CitiesManager.getInstance().startBuilding(tweets);
			int latestID = ConstructionWorldData.get(DimensionManager.getWorld(TwitterCityWorldGenReference.DIM_ID)).getLatestTweetID();
			TwitterCity.logger.info("The latest Tweet ID is: {}", latestID);
			//for(Tweet tweet : tweets) {
				//TwitterCity.logger.info(tweet.toString());
			//}
			TwitterCity.logger.info("tweetsToBuild size is: {}", BuildingReference.tweetsToBuild.size());
					
		}		
		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
	}
	
}
