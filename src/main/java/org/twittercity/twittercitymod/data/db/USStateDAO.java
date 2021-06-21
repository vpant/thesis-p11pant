package org.twittercity.twittercitymod.data.db;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.minecraft.nbt.NBTTagCompound;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.twittercity.twittercitymod.Reference;

import javax.persistence.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class USStateDAO {
    private SessionFactory sessionFactory;
    private static USStateDAO instance = new USStateDAO();

    private USStateDAO() {
        File hibernateConfig = new File((USStateDAO.class).getClassLoader().getResource("assets/" + Reference.MOD_ID + "/hibernate.cfg.xml").getFile());
        sessionFactory =  new Configuration().configure(hibernateConfig).buildSessionFactory();
    }

    public USState getState(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        USState usState = null;
        try {
            String hql = "FROM USState WHERE id = :id";
            tx = session.beginTransaction();
            Query<USState> query = session.createQuery(hql, USState.class);
            query.setParameter("id", id);
            usState = query.getSingleResult();
            tx.commit();
            session.close();
        } catch(NoResultException noResultE) {
            usState = null;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return usState;
    }

    public Integer getLastStateId() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Integer usStateId = null;
        try {
            String hql = "select max(state.id) FROM USState state";
            tx = session.beginTransaction();
            Query<Integer> query = session.createQuery(hql, Integer.class);
            usStateId = query.getSingleResult();
            tx.commit();
            session.close();
        } catch(NoResultException noResultE) {
            usStateId = null;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return usStateId;
    }

    public static USStateDAO getInstance() {
        return instance == null ? new USStateDAO() : instance;
    }
}
