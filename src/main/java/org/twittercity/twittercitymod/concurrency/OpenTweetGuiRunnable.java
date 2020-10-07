package org.twittercity.twittercitymod.concurrency;

import org.twittercity.twittercitymod.TwitterCity;
import org.twittercity.twittercitymod.data.db.Tweet;
import org.twittercity.twittercitymod.data.db.TweetManager;
import org.twittercity.twittercitymod.tileentity.TileEntityTwitter;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OpenTweetGuiRunnable implements Runnable {
	private TileEntityTwitter entity;
	
	public OpenTweetGuiRunnable(TileEntityTwitter entity) {
		this.entity = entity;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void run() {
		Tweet tweet = TweetManager.getInstance().getTweet(entity.getTweetID());
		Minecraft.getMinecraft().addScheduledTask(TwitterCity.proxy.openTweetGUI(tweet));

	}
}
