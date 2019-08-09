/**
 * Complying with Gui Library's license, I have made the following changes
 * to this class:
 * - Fixed outdated imports, overrides, and method calls.
 */
package org.twittercity.twittercitymod.libs.guilib.basic;

public abstract class OverlayScreen extends BasicScreen {

	protected BasicScreen bg;

	public OverlayScreen(BasicScreen bg) {
		super(bg);

		this.bg = bg;
	}

	@Override
	public void drawBackground() {
		bg.drawScreen(-1, -1, 0);
	}

	@Override
	protected void revalidateGui() {
		bg.width = width;
		bg.height = height;
		bg.revalidateGui();
	}

	@Override
	protected void reopenedGui() { }

}
