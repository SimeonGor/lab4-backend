package com.simeon.lab4.ejb.repo;

import com.simeon.lab4.entities.CheckResult;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.transaction.Transactional;
import org.hibernate.Session;

import java.util.List;

@Stateless
public class CheckResultRepository{
    @EJB
    private HibernateSessionFactory hibernateSessionFactory;

    public CheckResult findById(long id) {
        try (Session session = hibernateSessionFactory.getSessionFactory().openSession()) {
            return session.get(CheckResult.class, id);
        }
    }

    public List<CheckResult> findAll() {
        try (Session session = hibernateSessionFactory.getSessionFactory().openSession()) {
            return session.createQuery("from CheckResult", CheckResult.class).list();
        }
    }

    @Transactional
    public void save(CheckResult checkResult) {
        try (Session session = hibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(checkResult);
            session.getTransaction().commit();
        }
    }
}
