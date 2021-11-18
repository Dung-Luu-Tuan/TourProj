package com.example.tour_project.dao;

import com.example.tour_project.models.Tour;
import com.example.tour_project.models.TouristGroup;
import com.example.tour_project.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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

    public static TouristGroup getDetails(String matour) {
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
}
