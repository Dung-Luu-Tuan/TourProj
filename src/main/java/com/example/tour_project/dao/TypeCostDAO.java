package com.example.tour_project.dao;

import com.example.tour_project.models.Place;
import com.example.tour_project.models.TypeCost;
import com.example.tour_project.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class TypeCostDAO {
    private static SessionFactory factory;

    public static List<TypeCost> listTypeCost() {
        List<TypeCost> typeCosts = null;
        factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        try {
            typeCosts = session.createQuery("from TypeCost").list();
            return typeCosts;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        session.close();
        return null;
    }
}
