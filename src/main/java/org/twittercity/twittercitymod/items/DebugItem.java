package org.twittercity.twittercitymod.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.twittercity.twittercitymod.city.ConstructionOrchestration;
import org.twittercity.twittercitymod.data.db.Tweet;
import org.twittercity.twittercitymod.data.db.USState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DebugItem extends ItemBase{

	public DebugItem(String name) {
		super(name);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if (!worldIn.isRemote) {
			List<Tweet> tweets = new ArrayList<>();
			for(int i = 0; i < 10000; i++) {
				final Tweet tweet = new Tweet();
				USState state = new USState();
				state.setId(1);
				tweet.setState(state);
				tweets.add(tweet);
			}
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ConstructionOrchestration.getInstance().build(tweets);
		}		
		return new ActionResult<>(EnumActionResult.PASS, itemstack);
	}
}
