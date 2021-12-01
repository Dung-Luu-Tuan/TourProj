package com.example.tour_project.dao;

import com.example.tour_project.models.Customer;
import com.example.tour_project.models.CustomerTour;
import com.example.tour_project.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class CustomerTourDAO {
    private static SessionFactory factory;

    public static void insertCustomerTour(CustomerTour idCustomer) {
        if (idCustomer != null) {
            factory = HibernateUtil.getSessionFactory();
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.save(idCustomer);
            tx.commit();
            session.close();
        }
    }

    public static void deleteCustomerTour(CustomerTour idCustomer){
        factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(idCustomer);
        tx.commit();
        session.close();
    }
}
