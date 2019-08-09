package org.twittercity.twittercitymod.gui;

import org.lwjgl.input.Keyboard;
import org.twittercity.twittercitymod.libs.guilib.basic.BasicScreen;
import org.twittercity.twittercitymod.libs.guilib.core.Button;
import org.twittercity.twittercitymod.libs.guilib.core.Button.ButtonHandler;

import net.minecraft.client.gui.GuiScreen;

public abstract class TCScreen extends BasicScreen implements ButtonHandler{

	public TCScreen(GuiScreen parent) {
		super(parent);

	}

	public boolean doesGuiPauseGame() {
		return false;
	}

	protected void unhandledKeyTyped(char c, int code) {
		if (code == Keyboard.KEY_ESCAPE)
			mc.displayGuiScreen(null);
	}

	public void setEnabled(boolean aFlag, Button... buttons) {
		for (Button b : buttons)
			b.setEnabled(aFlag);
	}

	protected void reopenedGui() { }
	public void buttonClicked(Button button) { }

}