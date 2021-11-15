package com.example.tour_project.dao;

import com.example.tour_project.models.Customer;
import com.example.tour_project.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class CustomerDAO {
    private static SessionFactory factory;

    public static List<Customer> listTour() {
        List<Customer> customers = null;
        factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        try {
            customers = session.createQuery("from Customer").list();
            return customers;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        session.close();
        return null;
    }
}
