package org.twittercity.twittercitymod.data.Tweet;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

public class Tweet {

	private String text;
	private String author;
	private String profilePicUrl;
	private BufferedImage image = null;

	public Tweet() {
		text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Sed tempus urna et pharetra pharetra. Habitasse platea dictumst quisque sagittis purus sit amet volutpat. Vitae sapien pellentesque habitant morbi. Ultrices sagittis orci a scelerisque purus semper eget duis at. Ornare quam viverra orci sagittis eu volutpat odio facilisis mauris. Dignissim sodales ut eu sem integer vitae. Mattis ullamcorper velit sed ullamcorper morbi tincidunt. Egestas maecenas pharetra convallis posuere. Nec feugiat in fermentum posuere. Neque sodales ut etiam sit amet nisl purus in mollis. Pellentesque sit amet porttitor eget dolor morbi non. Justo donec enim diam vulputate ut. Aenean et tortor at risus viverra adipiscing at in tellus. Pellentesque sit amet porttitor eget dolor morbi non. Nisi scelerisque eu ultrices vitae. Libero justo laoreet sit amet cursus sit amet. Vitae justo eget magna fermentum iaculis eu non. Posuere morbi leo urna molestie at elementum eu facilisis sed. Condimentum vitae sapien pellentesque habitant. In fermentum posuere urna nec tincidunt praesent semper. Bibendum at varius vel pharetra vel turpis nunc eget. Arcu dui vivamus arcu felis. Praesent semper feugiat nibh sed pulvinar proin gravida hendrerit. Nunc congue nisi vitae suscipit tellus. In mollis nunc sed id semper.";
		author = "vasilis";
		profilePicUrl = "https://pbs.twimg.com/profile_images/880136122604507136/xHrnqf1T_normal.jpg";
	}

	public String getText() {
		return text;
	}

	public String getAuthor() {
		return author;
	}

	public String getgetProfilePicUrl() {
		return author;
	}

	public BufferedImage getProfilePicture() {
		if(this.image != null) {
			return this.image;
		}
		try {
			URL imageURL = new URL(profilePicUrl);
			BufferedImage jpgImage = ImageIO.read(imageURL);
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			byteArrayOut.flush();
			ImageIO.write(jpgImage, "png", byteArrayOut);
			byteArrayOut.close();
			byte[] resultingBytes = byteArrayOut.toByteArray();
			
			InputStream in = new ByteArrayInputStream(resultingBytes);
			BufferedImage oldImage = ImageIO.read(in);
			
			image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
		    Graphics g = image.getGraphics();
		    g.drawImage(oldImage, 0, 0, null);
		    g.dispose();
		    
			return this.image;
		} catch (Exception e) {
			this.image = null;
		}		
		return this.image;
	}
}
