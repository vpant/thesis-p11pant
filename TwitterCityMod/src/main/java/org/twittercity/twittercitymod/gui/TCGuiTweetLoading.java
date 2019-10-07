package org.twittercity.twittercitymod.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class TCGuiTweetLoading extends GuiScreen {
	
	int counter = 0;
	public static final int WIDTH = 256, HEIGHT = 140;
	private String dots = ".";
	public TCGuiTweetLoading() {
		
	}
	
	@Override
	public void initGui() {
		
	}



	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}


	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		
		if(counter % 30 == 0) {
			dots += ".";
		}
        this.drawCenteredString(this.fontRenderer, I18n.format("loading tweet") + dots, this.width / 2, this.height / 2, -1);
        
        if(counter / 120 == 1) {
        	counter = 0;
        	dots = "";
        } else {
        	counter++;
        }
        
	}	
}
