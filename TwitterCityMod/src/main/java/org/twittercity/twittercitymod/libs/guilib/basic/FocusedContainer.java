/**
 * Complying with Gui Library's license, I have made the following changes
 * to this class:
 * - Fixed outdated imports, overrides, and method calls.
 */
package org.twittercity.twittercitymod.libs.guilib.basic;

import org.twittercity.twittercitymod.libs.guilib.core.Container;
import org.twittercity.twittercitymod.libs.guilib.core.Scrollbar;
import org.twittercity.twittercitymod.libs.guilib.core.Widget;
import org.twittercity.twittercitymod.libs.guilib.focusable.FocusableWidget;

/**
 * 
 * A "Focused" version of a Container.
 * This container will always have a focused widget
 * as long as there is a focusable widget contained.
 *
 */
public class FocusedContainer extends Container {

	public FocusedContainer() {
		super();
	}

	public FocusedContainer(Scrollbar scrollbar, int shiftAmount, int extraScrollHeight) {
		super(scrollbar, shiftAmount, extraScrollHeight);
	}

	@Override
	public void setFocused(FocusableWidget f) {
		if (f != null)
			super.setFocused(f);
	}

	@Override
	public void addWidgets(Widget... arr) {
		super.addWidgets(arr);

		if (focusIndex == -1 && focusList.size() > 0) {
			focusIndex = 0;
			focusList.get(focusIndex).focusGained();
		}
	}
}
