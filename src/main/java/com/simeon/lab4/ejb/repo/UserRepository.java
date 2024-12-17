package com.simeon.lab4.ejb.repo;

import com.simeon.lab4.entities.User;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.transaction.Transactional;
import org.hibernate.Session;

@Stateless
public class UserRepository {
    @EJB
    private HibernateSessionFactory hibernateSessionFactory;

    @Transactional
    public void save(User user) {
        try (Session session = hibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }
    }

    public User getByUsername(String username) {
        try (Session session = hibernateSessionFactory.getSessionFactory().openSession()) {
            return session.createQuery("from User where username = :username", User.class)
                    .setParameter("username", username)
                    .getResultStream().findAny().orElse(null);
        }
    }
}

