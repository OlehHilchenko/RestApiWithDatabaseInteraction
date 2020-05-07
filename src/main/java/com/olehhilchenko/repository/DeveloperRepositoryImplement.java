package com.olehhilchenko.repository;

import com.olehhilchenko.models.Developer;
import com.olehhilchenko.repository.hibernate.HibernateUtilities;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.util.List;

public class DeveloperRepositoryImplement implements DeveloperRepository {

    @Override
    public long insert(Developer developer) {
        try (Session session = HibernateUtilities.getSessionFactory().getCurrentSession()) {
            long id;
            session.beginTransaction();
            session.save(developer);
            id = developer.getId();
            session.getTransaction().commit();
            return id;
        }
    }

    @Override
    public void update(Developer developer) {
        try (Session session = HibernateUtilities.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.saveOrUpdate(developer);
            session.getTransaction().commit();
        }
    }

    @Override
    public Developer select(long id) {
        try (Session session = HibernateUtilities.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            Developer result = session.load(Developer.class, id);
            Hibernate.initialize(result.getSkills());
            session.getTransaction().commit();
            return result;
        }
    }

    @Override
    public void delete(Developer developer) {
        try (Session session = HibernateUtilities.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.delete(developer);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Developer> getDeveloperList() {
        try (Session session = HibernateUtilities.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            List<Developer> result = session.createQuery("FROM Developer").list();
            for (Developer developer : result) {
                Hibernate.initialize(developer.getSkills());
            }
            session.getTransaction().commit();
            return result;
        }
    }
}
