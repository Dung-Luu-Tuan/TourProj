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
    public static List<Place> listPlace() {
        List<Place> places = null;
        factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        try {
            places = session.createQuery("from Place").list();
            return places;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        session.close();
        return null;
    }
    public static void insert(PlaceOrder placeOrder){
        if (placeOrder != null) {
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.save(placeOrder);
            tx.commit();
            session.close();
        }
    }
}
