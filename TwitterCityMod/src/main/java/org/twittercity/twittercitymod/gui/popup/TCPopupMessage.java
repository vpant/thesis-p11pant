package org.twittercity.twittercitymod.gui.popup;

import org.twittercity.twittercitymod.libs.guilib.basic.Label;
import org.twittercity.twittercitymod.libs.guilib.core.Button;
import org.twittercity.twittercitymod.libs.guilib.core.Container;
import org.twittercity.twittercitymod.libs.guilib.vanilla.ButtonVanilla;

public class TCPopupMessage extends TCTweetPopup {

	private String bText;
	private String[] labelStr;

	private Container container;
	private Label[] labels;
	private Button ok;

	public TCPopupMessage(String buttonText, String... strings) {
		super(null);

		this.bText = buttonText;
		this.labelStr = strings;
	}

	@Override
	protected void createGui() {
		container = new Container();
		ok = new ButtonVanilla(50, 20, bText, new CloseHandler());
		container.addWidgets(ok);

		labels = new Label[labelStr.length];
		for (int i = 0; i < labels.length; ++i) {
			labels[i] = new Label(labelStr[i], 0, 0);
			labels[i].setShadowedText(false);
		}
		container.addWidgets(labels);

		containers.add(container);
		selectedContainer = container;
	}

	@Override
	public void revalidateGui() {
		int startY = (height - HEIGHT) / 2;
		ok.setPosition(width / 2 - 25, startY + 100);
		container.revalidate(0, 0, width, height);

		for (int i = 0; i < labels.length; ++i)
			labels[i].setPosition(width / 2, startY + i * 13);
	}

	@Override
	protected void reopenedGui() {
		
	}

}