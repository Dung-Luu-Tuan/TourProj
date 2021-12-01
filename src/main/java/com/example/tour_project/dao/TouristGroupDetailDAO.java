package com.example.tour_project.dao;

import com.example.tour_project.models.DetailTourGroup;
import com.example.tour_project.models.TouristGroup;
import com.example.tour_project.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class TouristGroupDetailDAO {
    private static SessionFactory factory;

    public static void update(DetailTourGroup detailTourGroup) {
        if (detailTourGroup != null) {
            factory = HibernateUtil.getSessionFactory();
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(detailTourGroup);
            tx.commit();
            session.close();
        }
    }

    public static void insert(DetailTourGroup detailTourGroup){
        if (detailTourGroup != null) {
            factory = HibernateUtil.getSessionFactory();
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.save(detailTourGroup);
            tx.commit();
            session.close();
        }
    }

    public static void delete(DetailTourGroup detailTourGroup){
        if (detailTourGroup != null) {
            factory = HibernateUtil.getSessionFactory();
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.delete(detailTourGroup);
            tx.commit();
            session.close();
        }
    }
}
