package org.twittercity.twittercitymod.data.db;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.twittercity.twittercitymod.Reference;
import org.twittercity.twittercitymod.TwitterCity;



public class TweetManager {
	
	private SessionFactory sessionFactory;
	private static TweetManager instance;
	
	private TweetManager() {
		File hibernateConfig = new File((TweetManager.class).getClassLoader().getResource("assets/" + Reference.MOD_ID + "/hibernate.cfg.xml").getFile());
		sessionFactory =  new Configuration().configure(hibernateConfig).buildSessionFactory();
	}
	
	public void getTweets() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<Tweet> tweets = session.createQuery("FROM Tweet", Tweet.class).list();
			for (Iterator<Tweet> iterator = tweets.iterator(); iterator.hasNext();){
	            Tweet tweet = (Tweet) iterator.next(); 
	            TwitterCity.logger.info(tweet.toString());
	        }
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
	        e.printStackTrace(); 
	    } finally {
	    	session.close(); 
	    }
	}
	
	public Tweet getTweet(int id) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Tweet tweet = null;
		try {
			String hql = "FROM Tweet WHERE id = :id";
			tx = session.beginTransaction();
			Query<Tweet> query = session.createQuery(hql, Tweet.class);
			query.setParameter("id", id);
			tweet = query.getSingleResult();
			
			tx.commit();
			session.close();
		} catch(NoResultException noResultE) {
			tweet = null;
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
	        e.printStackTrace(); 
	    } finally {
	    	session.close(); 
	    }
		return tweet;
	}
	
	public List<Tweet> getAllTweetsAfter(int id) {		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Tweet> tweets = null;
		try {
			String hql = "FROM Tweet WHERE id > :id";
			tx = session.beginTransaction();
			Query<Tweet> query = session.createQuery(hql, Tweet.class);
			query.setParameter("id", id);
			tweets = query.list();
			for (Iterator<Tweet> iterator = tweets.iterator(); iterator.hasNext();){
	            Tweet tweet = (Tweet) iterator.next(); 
	            TwitterCity.logger.info(tweet.toString());
	         }
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
	        e.printStackTrace(); 
	    } finally {
	    	session.close(); 
	    }
		return tweets;
	}
	
	public static TweetManager getInstance() {
		return instance == null ? new TweetManager() : instance;
	}
	
}
