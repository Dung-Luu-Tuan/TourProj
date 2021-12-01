package com.example.tour_project.dao;

import com.example.tour_project.models.Cost;
import com.example.tour_project.models.PlaceOrder;
import com.example.tour_project.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class CostDAO {
    private static SessionFactory factory;

    public static List<Cost> listLocation() {
        List<Cost> costs = null;
        factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        try {
            costs = session.createQuery("from Cost").list();
            return costs;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        session.close();
        return null;
    }

    public static void insert(Cost cost){
        if (cost != null) {
            factory = HibernateUtil.getSessionFactory();
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.save(cost);
            tx.commit();
            session.close();
        }
    }

    public static void delete(Cost cost){
        if (cost != null) {
            factory = HibernateUtil.getSessionFactory();
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.delete(cost);
            tx.commit();
            session.close();
        }
    }

    public static void update(Cost cost){
        if (cost != null) {
            factory = HibernateUtil.getSessionFactory();
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(cost);
            tx.commit();
            session.close();
        }
    }
}
