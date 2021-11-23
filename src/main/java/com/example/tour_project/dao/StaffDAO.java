package com.example.tour_project.dao;

import com.example.tour_project.models.Staff;
import com.example.tour_project.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class StaffDAO {
    private static SessionFactory factory;

    public static List<Staff> listStaff() {
        List<Staff> staffs = null;
        factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        try {
            staffs = session.createQuery("from Staff").list();
            return staffs;
        }
        catch (HibernateException e) {
            e.printStackTrace();
        }
        session.close();
        return null;
    }

    public static void update(Staff staff) {
        if (staff != null) {
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.update(staff);
            tx.commit();
            session.close();
        }
    }

    public static void insert(Staff staff) {
        if (staff != null) {
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.save(staff);
            tx.commit();
            session.close();
        }
    }

    public static void delete(Staff staff) {
        if (staff != null) {
            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.delete(staff);
            tx.commit();
            session.close();
        }
    }
}