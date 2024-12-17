package com.simeon.lab4.ejb.repo;

import jakarta.ejb.Singleton;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Singleton
public class HibernateSessionFactory {
    private SessionFactory sessionFactory;

    private HibernateSessionFactory() {}

    public synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}
