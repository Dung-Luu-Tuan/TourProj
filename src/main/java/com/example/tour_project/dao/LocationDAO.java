package com.example.tour_project.dao;

import com.example.tour_project.models.Place;
import com.example.tour_project.models.PlaceOrder;
import com.example.tour_project.models.Tour;
import com.example.tour_project.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class LocationDAO {
    private static SessionFactory factory;

    public static List<PlaceOrder> listLocation() {
        List<PlaceOrder> placeOrders = null;
        factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        try {
            placeOrders = session.createQuery("from PlaceOrder").list();
            return placeOrders;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        session.close();
        return null;
    }

    public static void insert(PlaceOrder placeOrder){
        if (placeOrder != null) {
            factory = HibernateUtil.getSessionFactory();
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.save(placeOrder);
            tx.commit();
            session.close();
        }
    }

    public static void delete(PlaceOrder placeOrder){
        if (placeOrder != null) {
            factory = HibernateUtil.getSessionFactory();
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.delete(placeOrder);
            tx.commit();
            session.close();
        }
    }

    public static void update(PlaceOrder placeOrder){
        if (placeOrder != null) {
            factory = HibernateUtil.getSessionFactory();
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(placeOrder);
            tx.commit();
            session.close();
        }
    }
}
