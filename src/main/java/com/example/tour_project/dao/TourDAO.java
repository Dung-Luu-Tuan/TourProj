package com.example.tour_project.dao;

import com.example.tour_project.models.Place;
import com.example.tour_project.models.Tour;
import com.example.tour_project.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.text.DecimalFormat;
import java.util.List;

public class TourDAO {
    private static SessionFactory factory;

    public static List<Tour> listTour() {
        List<Tour> tours = null;
        factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        try {
            tours = session.createQuery("from Tour").list();
            return tours;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        session.close();
        return null;
    }

    public static Tour getDetail(int matour) {
        Tour tour = null;
        factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        session.clear();
        try {
            session.beginTransaction();
            tour = session.find(Tour.class, matour);
            return tour;
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();
        return null;
    }


    public static void update(Tour tour){
        if (tour != null) {
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.update(tour);
            tx.commit();
            session.close();
        }
    }

    public static void insert(Tour tour){
        if (tour != null) {
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.save(tour);
            tx.commit();
            session.close();
        }
    }

    public static void delete(Tour tour){
        if (tour != null) {
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.delete(tour);
            tx.commit();
            session.close();
        }
    }


}
