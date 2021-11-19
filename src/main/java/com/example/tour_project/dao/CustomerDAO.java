package com.example.tour_project.dao;

import com.example.tour_project.models.Customer;
import com.example.tour_project.models.Tour;
import com.example.tour_project.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class CustomerDAO {
    private static SessionFactory factory;

    public static List<Customer> listCustomer() {
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
    public static void insert(Customer customer){
        if (customer != null) {
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.save(customer);
            tx.commit();
            session.close();
        }
    }
    public static void delete(Customer customer){
        if (customer != null) {
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.delete(customer);
            tx.commit();
            session.close();
        }
    }
    public static void update(Customer customer){
        if (customer != null) {
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.update(customer);
            tx.commit();
            session.close();
        }
    }
}
