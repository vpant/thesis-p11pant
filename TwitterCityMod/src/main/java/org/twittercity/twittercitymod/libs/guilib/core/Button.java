/**
 * Complying with Gui Library's license, I have made the following changes
 * to this class:
 * - Fixed outdated imports, overrides, and method calls.
 */
package org.twittercity.twittercitymod.libs.guilib.core;

/**
 * 
 * Abstract representation of a minecraft button.
 * The buttons calls handler.buttonClicked(this) when it is pressed.
 *
 */
public abstract class Button extends Widget {

	public interface ButtonHandler {
		void buttonClicked(Button button);
	}

	protected ButtonHandler handler;

	public Button(int width, int height, ButtonHandler handler) {
		super(width, height);

		this.handler = handler;
	}

	@Override
	public boolean click(int mx, int my) {
		return enabled && inBounds(mx, my);
	}

	@Override
	public void handleClick(int mx, int my) {
		if (handler != null)
			handler.buttonClicked(this);
	}

	public void setEnabled(boolean flag) {
		this.enabled = flag;
	}
	
	public String getText() {
		return "";
	}

	public void setText(String str) { }
}
