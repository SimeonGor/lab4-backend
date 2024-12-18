package com.simeon.lab4.ejb.repo;

import com.simeon.lab4.entities.CheckResult;
import com.simeon.lab4.entities.User;
import jakarta.ejb.Stateless;
import org.hibernate.Session;

import java.util.List;

@Stateless
public class CheckResultRepository{

    public CheckResult findById(long id) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.get(CheckResult.class, id);
        }
    }

    public List<CheckResult> findAllByUser(User user) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.createQuery("select r from CheckResult r where r.user = :user", CheckResult.class)
                    .setParameter("user", user)
                    .list();
        }
    }

    public void save(CheckResult checkResult) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(checkResult);
            session.getTransaction().commit();
        }
    }
}
