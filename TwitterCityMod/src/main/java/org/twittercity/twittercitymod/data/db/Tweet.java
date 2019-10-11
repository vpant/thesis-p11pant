package org.twittercity.twittercitymod.data.db;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.twittercity.twittercitymod.tileentity.Feeling;

@Entity
@Table(name = "tweets")
public class Tweet {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = - 1;
	@Column(name = "text")
	private String text = null;
	@Column(name = "author")
	private String author = null;
	@Column(name = "author_account_id")
	private String authorAccountId = null;
	@Column(name = "twitter_tweet_id")
	private String tweetID = null;
	@Column(name = "date")
	//@Temporal(TemporalType.TIMESTAMP)
	private String date = null;
	@Column(name = "profile_pic_url")
	private String profilePicUrl = null;
	@Column(name = "feeling")
	@Convert(converter = FeelingEnumConverter.class)
	private Feeling feeling = Feeling.NO_FEELING;
	@Transient
	private BufferedImage image = null;
	@Transient
	private boolean everythingLoaded;

	public Tweet() {
		this.id = 1;
		this.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Sed tempus urna et pharetra pharetra. Habitasse platea dictumst quisque sagittis purus sit amet volutpat. Vitae sapien pellentesque habitant morbi. Ultrices sagittis orci a scelerisque purus semper eget duis at. Ornare quam viverra orci sagittis eu volutpat odio facilisis mauris. Dignissim sodales ut eu sem integer vitae. Mattis ullamcorper velit sed ullamcorper morbi tincidunt. Egestas maecenas pharetra convallis posuere. Nec feugiat in fermentum posuere. Neque sodales ut etiam sit amet nisl purus in mollis. Pellentesque sit amet porttitor eget dolor morbi non. Justo donec enim diam vulputate ut. Aenean et tortor at risus viverra adipiscing at in tellus. Pellentesque sit amet porttitor eget dolor morbi non. Nisi scelerisque eu ultrices vitae. Libero justo laoreet sit amet cursus sit amet. Vitae justo eget magna fermentum iaculis eu non. Posuere morbi leo urna molestie at elementum eu facilisis sed. Condimentum vitae sapien pellentesque habitant. In fermentum posuere urna nec tincidunt praesent semper. Bibendum at varius vel pharetra vel turpis nunc eget. Arcu dui vivamus arcu felis. Praesent semper feugiat nibh sed pulvinar proin gravida hendrerit. Nunc congue nisi vitae suscipit tellus. In mollis nunc sed id semper.";
		this.author = "vasilis";
		this.profilePicUrl = "https://pbs.twimg.com/profile_images/880136122604507136/xHrnqf1T_normal.jpg";
		this.authorAccountId = "10412351231";
		this.date = "11/12/2050";
		this.feeling = Feeling.NO_FEELING;
		this.everythingLoaded = true;
	}
	
	public Tweet(String text, String author, String authorAccountId, String twitterAccountID, String date,
			String profilePicUrl, Feeling feeling) {
		this.text = text;
		this.author = author;
		this.authorAccountId = authorAccountId;
		this.tweetID = twitterAccountID;
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
		return this.feeling;
	}
	
	public String getText() {
		loadEverything();
		return text;
	}

	public String getAuthor() {
		loadEverything();
		return author;
	}

	public String getgetProfilePicUrl() {
		loadEverything();
		return author;
	}

	public int getId() {
		loadEverything();
		return id;
	}


	public String getAuthorAccountId() {
		loadEverything();
		return authorAccountId;
	}

	public String gettwitterAccountID() {
		loadEverything();
		return tweetID;
	}

	public String getDate() {
		loadEverything();
		return date;
	}

	public String getProfilePicUrl() {
		loadEverything();
		return profilePicUrl;
	}

	public void setAuthor(String author) {
		loadEverything();
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
	
	public void loadEverything() {
		if(!everythingLoaded) {
			try {
				throw new Exception("Data are not loaded for this tweet. "
						+ "Call Tweet#getOrLoadTweet to get a Tweet object with its "
						+ "fields populated.");
			} catch (Exception e) {
				getOrLoadTweet();
			}
		}
	}
	
	/**
	 *  Gets a tweet object with its fields populated from the database using a tweetID
	 */
	public static Tweet getOrLoadTweet(int tweetID) {
		return TweetManager.getInstance().getTweet(tweetID);
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
		this.tweetID = tweet.tweetID;
		this.date = tweet.date;
		this.profilePicUrl = tweet.profilePicUrl;
		this.feeling = tweet.feeling;
		this.everythingLoaded = tweet.everythingLoaded;
	}
	
	@Override
	public String toString()
	{
		return "User " + author 
				+ " with ID " + tweetID 
				+ " said: " + text 
				+ " at " + date + "\n";
	}
}
