package com.simeon.lab4.ejb.repo;

import com.simeon.lab4.entities.CheckResult;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import org.hibernate.Session;

import java.util.List;

@Stateless
public class CheckResultRepository implements Repository<CheckResult> {
    @EJB
    private HibernateSessionFactory hibernateSessionFactory;

    @Override
    public CheckResult findById(long id) {
        try (Session session = hibernateSessionFactory.getSessionFactory().openSession()) {
            return session.get(CheckResult.class, id);
        }
    }

    @Override
    public List<CheckResult> findAll() {
        try (Session session = hibernateSessionFactory.getSessionFactory().openSession()) {
            return session.createQuery("from CheckResult", CheckResult.class).list();
        }
    }

    @Override
    public void save(CheckResult checkResult) {
        try (Session session = hibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(checkResult);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(CheckResult checkResult) {
        try (Session session = hibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.merge(checkResult);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(CheckResult checkResult) {
        try (Session session = hibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.remove(checkResult);
            session.getTransaction().commit();
        }
    }
}
