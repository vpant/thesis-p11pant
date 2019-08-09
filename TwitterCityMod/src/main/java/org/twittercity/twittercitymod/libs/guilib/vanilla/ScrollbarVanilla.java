/**
 * Complying with Gui Library's license, I have made the following changes
 * to this class:
 * - Fixed outdated imports, overrides, and method calls.
 */
package org.twittercity.twittercitymod.libs.guilib.vanilla;

import org.twittercity.twittercitymod.libs.guilib.core.Scrollbar;
import org.twittercity.twittercitymod.libs.guilib.core.Widget;

/**
 * 
 * Default style scrollbar.
 *
 */
public class ScrollbarVanilla extends Scrollbar {

	public ScrollbarVanilla(int width) {
		super(width);

	}

	@Override
	protected void drawBoundary(int x, int y, int width, int height) {
		drawRect(x, y, x + width, y + height, 0x80000000);
	}

	@Override
	protected void drawScrollbar(int x, int y, int width, int height) {
		drawGradientRect(x, y, x + width, y + height, 0x80ffffff, 0x80222222);
	}

	@Override
	protected void shiftChildren(int dy) {
		for (Widget w : container.getWidgets()) {
			if (w instanceof Shiftable)
				((Shiftable) w).shiftY(dy);
		}
	}

}
