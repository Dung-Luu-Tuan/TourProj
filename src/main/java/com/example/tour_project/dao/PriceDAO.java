package com.example.tour_project.dao;

import com.example.tour_project.models.PlaceOrder;
import com.example.tour_project.models.TourPrice;
import com.example.tour_project.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.List;
import java.text.SimpleDateFormat;

public class PriceDAO {
    private static SessionFactory factory;

    public static List<TourPrice> listLocation() {
        List<TourPrice> tourPrices = null;
        factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        try {
            tourPrices = session.createQuery("from TourPrice").list();
            return tourPrices;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        session.close();
        return null;
    }

    public static void insert(TourPrice tourPrice){
        if (tourPrice != null) {
            factory = HibernateUtil.getSessionFactory();
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.save(tourPrice);
            tx.commit();
            session.close();
        }
    }

    public static void delete(TourPrice tourPrice){
        if (tourPrice != null) {
            factory = HibernateUtil.getSessionFactory();
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.delete(tourPrice);
            tx.commit();
            session.close();
        }
    }

    public static void update(TourPrice tourPrice){
        if (tourPrice != null) {
            factory = HibernateUtil.getSessionFactory();
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(tourPrice);
            tx.commit();
            session.close();
        }
    }

    public static String priceWithoutDecimal (float price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(price);
    }

    public static String DateFormat (Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return  formatter.format(date);
    }
}
