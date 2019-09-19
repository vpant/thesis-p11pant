package org.twittercity.twittercitymod.data.db;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;

import javax.imageio.ImageIO;

import org.twittercity.twittercitymod.tileentity.Feeling;

public class Tweet {

	private int id = - 1;
	private String text = null;
	private String author = null;
	private String authorAccountId = null;
	private String idStr = null;
	private Date date = null;
	private String profilePicUrl = null;
	private Feeling feeling = null;
	private BufferedImage image = null;
	private boolean everythingLoaded;

	public Tweet() {
		text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Sed tempus urna et pharetra pharetra. Habitasse platea dictumst quisque sagittis purus sit amet volutpat. Vitae sapien pellentesque habitant morbi. Ultrices sagittis orci a scelerisque purus semper eget duis at. Ornare quam viverra orci sagittis eu volutpat odio facilisis mauris. Dignissim sodales ut eu sem integer vitae. Mattis ullamcorper velit sed ullamcorper morbi tincidunt. Egestas maecenas pharetra convallis posuere. Nec feugiat in fermentum posuere. Neque sodales ut etiam sit amet nisl purus in mollis. Pellentesque sit amet porttitor eget dolor morbi non. Justo donec enim diam vulputate ut. Aenean et tortor at risus viverra adipiscing at in tellus. Pellentesque sit amet porttitor eget dolor morbi non. Nisi scelerisque eu ultrices vitae. Libero justo laoreet sit amet cursus sit amet. Vitae justo eget magna fermentum iaculis eu non. Posuere morbi leo urna molestie at elementum eu facilisis sed. Condimentum vitae sapien pellentesque habitant. In fermentum posuere urna nec tincidunt praesent semper. Bibendum at varius vel pharetra vel turpis nunc eget. Arcu dui vivamus arcu felis. Praesent semper feugiat nibh sed pulvinar proin gravida hendrerit. Nunc congue nisi vitae suscipit tellus. In mollis nunc sed id semper.";
		author = "vasilis";
		profilePicUrl = "https://pbs.twimg.com/profile_images/880136122604507136/xHrnqf1T_normal.jpg";
	}
	
	public Tweet(int id, String text, String author, String authorAccountId, String idStr, Date date,
			String profilePicUrl, Feeling feeling) {
		this.id = id;
		this.text = text;
		this.author = author;
		this.authorAccountId = authorAccountId;
		this.idStr = idStr;
		this.date = date;
		this.profilePicUrl = profilePicUrl;
		this.feeling = feeling;
		this.everythingLoaded = true;
	}
	
	public Tweet(int id, Feeling feeling) {
		this.id = id;
		this.feeling = feeling;
		everythingLoaded = false;
	}

	public int getID() {
		return id;
	}
	
	public Feeling getFeeling() {
		return feeling;
	}
	
	public String getText() {
		if(!everythingLoaded) {
			try {
				throw new Exception("Data are not loaded for this tweet. "
						+ "Call Tweet#getOrLoadTweet to get a Tweet object with its "
						+ "fields populated.");
			} catch (Exception e) {
				getOrLoadTweet();
			}
		}
		return text;
	}

	public String getAuthor() {
		if(!everythingLoaded) {
			try {
				throw new Exception("Data are not loaded for this tweet. "
						+ "Call Tweet#getOrLoadTweet to get a Tweet object with its "
						+ "fields populated.");
			} catch (Exception e) {
				getOrLoadTweet();
			}
		}
		return author;
	}

	public String getgetProfilePicUrl() {
		if(!everythingLoaded) {
			try {
				throw new Exception("Data are not loaded for this tweet. "
						+ "Call Tweet#getOrLoadTweet to get a Tweet object with its "
						+ "fields populated.");
			} catch (Exception e) {
				getOrLoadTweet();
			}
		}
		return author;
	}

	public int getId() {
		if(!everythingLoaded) {
			try {
				throw new Exception("Data are not loaded for this tweet. "
						+ "Call Tweet#getOrLoadTweet to get a Tweet object with its "
						+ "fields populated.");
			} catch (Exception e) {
				getOrLoadTweet();
			}
		}
		return id;
	}


	public String getAuthorAccountId() {
		if(!everythingLoaded) {
			try {
				throw new Exception("Data are not loaded for this tweet. "
						+ "Call Tweet#getOrLoadTweet to get a Tweet object with its "
						+ "fields populated.");
			} catch (Exception e) {
				getOrLoadTweet();
			}
		}
		return authorAccountId;
	}

	public String getIdStr() {
		if(!everythingLoaded) {
			try {
				throw new Exception("Data are not loaded for this tweet. "
						+ "Call Tweet#getOrLoadTweet to get a Tweet object with its "
						+ "fields populated.");
			} catch (Exception e) {
				getOrLoadTweet();
			}
		}
		return idStr;
	}

	public Date getDate() {
		if(!everythingLoaded) {
			try {
				throw new Exception("Data are not loaded for this tweet. "
						+ "Call Tweet#getOrLoadTweet to get a Tweet object with its "
						+ "fields populated.");
			} catch (Exception e) {
				getOrLoadTweet();
			}
		}
		return date;
	}

	public String getProfilePicUrl() {
		if(!everythingLoaded) {
			try {
				throw new Exception("Data are not loaded for this tweet. "
						+ "Call Tweet#getOrLoadTweet to get a Tweet object with its "
						+ "fields populated.");
			} catch (Exception e) {
				getOrLoadTweet();
			}
		}
		return profilePicUrl;
	}

	public void setAuthor(String author) {
		if(!everythingLoaded) {
			try {
				throw new Exception("Data are not loaded for this tweet. "
						+ "Call Tweet#getOrLoadTweet to get a Tweet object with its "
						+ "fields populated.");
			} catch (Exception e) {
				getOrLoadTweet();
			}
		}
		this.author = author;
	}

	public BufferedImage getProfilePicture() {
		if(this.image != null) {
			return this.image;
		}
		try {
			//Download profile pic
			URL imageURL = new URL(profilePicUrl);
			URLConnection con = imageURL.openConnection();
			con.setConnectTimeout(1200);
			con.setReadTimeout(2000);
			InputStream imageStream = con.getInputStream();
			BufferedImage jpgImage = ImageIO.read(imageStream);
			
			// Convert from jpg to png
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			byteArrayOut.flush();
			ImageIO.write(jpgImage, "png", byteArrayOut);
			byteArrayOut.close();
			byte[] resultingBytes = byteArrayOut.toByteArray();
			InputStream in = new ByteArrayInputStream(resultingBytes);
			BufferedImage oldImage = ImageIO.read(in);
			// Scale the 48 x 48 image 
			Image scaledImage = oldImage.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
			//Scale canvas to 256 x 256		
			image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
		    Graphics2D g = image.createGraphics();
		    g.drawImage(scaledImage, 0, 0, null);
		    g.dispose();
		} catch (Exception e) {
			this.image = null;
		}		
		return this.image;
	}
	
	/**
	 *  Gets a tweet object with its fields populated from the database using a tweetID
	 */
	public static Tweet getOrLoadTweet(int tweetID) {
		return null;
	}
	
	
	/*
	 * Used internally to populate fields from the database for an instance of this class
	 */
	private void getOrLoadTweet() {
		Tweet tweet = getOrLoadTweet(this.id);
		copyToThis(tweet);
	}

	private void copyToThis(Tweet tweet) {
		this.id = tweet.id;
		this.text = tweet.text;
		this.author = tweet.author;
		this.authorAccountId = tweet.authorAccountId;
		this.idStr = tweet.idStr;
		this.date = tweet.date;
		this.profilePicUrl = tweet.profilePicUrl;
		this.feeling = tweet.feeling;
		this.everythingLoaded = tweet.everythingLoaded;
	}
}
