package com.example.tour_project.dao;

import com.example.tour_project.models.Tour;
import com.example.tour_project.models.TouristGroup;
import com.example.tour_project.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class TouristGroupDAO {
    private static SessionFactory factory;
    public static List<TouristGroup> listTourGroup() {
        List<TouristGroup> tourGroups = null;
        factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        try {
            tourGroups = session.createQuery("from TouristGroup").list();
                return tourGroups;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        session.close();
        return null;
    }

    public static TouristGroup getDetails(int matour) {
        TouristGroup tourGroup = null;
        factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        session.clear();
        try {
            session.beginTransaction();
            tourGroup = session.find(TouristGroup.class, matour);
            return tourGroup;
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();
        return null;
    }

    public static TouristGroup getDetailsByCustomer(int madoan) {
        TouristGroup tourGroup = null;
        factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        session.clear();
        try {
            session.beginTransaction();
            tourGroup = session.find(TouristGroup.class, madoan);
            return tourGroup;
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();
        return null;
    }

    public static void update(TouristGroup tourGroup) {
        if (tourGroup != null) {
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.update(tourGroup);
            tx.commit();
            session.close();
        }
    }

    public static void insert(TouristGroup tourgroup){
        if (tourgroup != null) {
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.save(tourgroup);
            tx.commit();
            session.close();
        }
    }

    public static void delete(TouristGroup tourgroup){
        if (tourgroup != null) {
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.delete(tourgroup);
            tx.commit();
            session.close();
        }
    }
}
