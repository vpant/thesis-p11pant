package org.twittercity.twittercitymod.data.db;

import java.io.File;
import java.util.ArrayList;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.twittercity.twittercitymod.Reference;

public class TweetManager {
	
	private SessionFactory sessionFactory;
	private static TweetManager instance = new TweetManager();
	
	private TweetManager() {
		File hibernateConfig = new File((TweetManager.class).getClassLoader().getResource("assets/" + Reference.MOD_ID + "/hibernate.cfg.xml").getFile());
		sessionFactory =  new Configuration().configure(hibernateConfig).buildSessionFactory();
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
	
	public ArrayList<Tweet> getAllTweetsAfter(int id) {		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		ArrayList<Tweet> tweets = null;
		try {
			String hql = "FROM Tweet WHERE id > :id";
			tx = session.beginTransaction();
			Query<Tweet> query = session.createQuery(hql, Tweet.class);
			query.setParameter("id", id);
			tweets = (ArrayList<Tweet>) query.list();
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
