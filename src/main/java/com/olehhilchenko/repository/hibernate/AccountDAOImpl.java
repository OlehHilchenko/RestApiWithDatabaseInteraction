package com.olehhilchenko.repository.hibernate;

import com.olehhilchenko.model.Account;
import com.olehhilchenko.repository.AccountRepository;
import org.hibernate.Session;

import java.util.List;

public class AccountDAOImpl implements AccountRepository {

    @Override
    public long insert(Account object) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
            return object.getId();
        }
    }

    @Override
    public void update(Account object) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.saveOrUpdate(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public Account select(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            Account result = session.load(Account.class, id);
            session.getTransaction().commit();
            return result;
        }
    }

    @Override
    public void delete(Account object) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.delete(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Account> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            List<Account> result = session.createQuery("FROM Account ").list();
            session.getTransaction().commit();
            return result;
        }
    }
}
