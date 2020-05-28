package org.twittercity.twittercitymod.tileentity;

import java.awt.Color;

public enum Feeling {
	JOY(0, new Color(255, 255, 0).getRGB()), 
	SADNESS(1, new Color(128, 128, 128).getRGB()), 
	FEAR(2, new Color(0, 0, 0).getRGB()),
	ANGER(3, new Color(255, 0, 0).getRGB()),
	DISGUST(4, new Color(144, 238, 144).getRGB()),
	NO_FEELING(-1, -1);
	
	private final int value;
	private final int colorCode;
	
	private Feeling(int value, int colorCode) {
        this.value = value;
        this.colorCode = colorCode;
    }
	
	public int getFeelingID() {
		return value;
	}
	
	public int getFeelingColor() {
		return colorCode;
	}
	
	public static Feeling forFeelingID(int feelingID) {
		for (Feeling feeling: values()) {
            if (feeling.getFeelingID() == feelingID) {
            	return feeling;
            }          
        }
		return NO_FEELING;
	}
} 
