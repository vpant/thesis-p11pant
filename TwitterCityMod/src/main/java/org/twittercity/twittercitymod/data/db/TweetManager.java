package org.twittercity.twittercitymod.data.db;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.twittercity.twittercitymod.Reference;
import org.twittercity.twittercitymod.tileentity.Feeling;

public class TweetManager {
	
	public static void getTweets() {

		File hibernateConfig = new File((TweetManager.class).getClassLoader().getResource("assets/" + Reference.MOD_ID + "/hibernate.cfg.xml").getFile());
		SessionFactory sf =  new Configuration().configure(hibernateConfig).buildSessionFactory();
		Session session = sf.openSession();
	
		//Creating the first object
		Tweet ob1 = new Tweet("ola good", "32341341", "431423", "asdas", "", "https://mangas.gr/",Feeling.HAPPY);
		Tweet ob2 = new Tweet("oasdod", "3234112312341", "431412131423", "123fasdqw", "", "https://mangas.gr/",Feeling.NO_FEELING);
		Tweet ob3 = new Tweet("olfewfwedqwod", "1231231r124", "213", "asdfq", "", "https://mangas.gr/",Feeling.SAD);
	
		
		//Saving the objects
		session.beginTransaction();
		session.save(ob1); //Saving the first object
		session.save(ob2); //Saving the second object
		session.save(ob3); //Saving the third object
		session.getTransaction().commit();
		session.close();
	
	}
	
}
