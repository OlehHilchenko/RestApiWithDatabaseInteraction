package com.olehhilchenko.repository.hibernate;

import com.olehhilchenko.model.Skill;
import com.olehhilchenko.repository.SkillRepository;
import org.hibernate.Session;

import java.util.List;

public class SkillDAOImpl implements SkillRepository {

    @Override
    public long insert(Skill object) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
            return object.getId();
        }
    }

    @Override
    public void update(Skill object) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.saveOrUpdate(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public Skill select(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            Skill result = session.load(Skill.class, id);
            session.getTransaction().commit();
            return result;
        }
    }

    @Override
    public void delete(Skill object) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.delete(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Skill> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            List<Skill> result = session.createQuery("FROM Skill ").list();
            session.getTransaction().commit();
            return result;
        }
    }
}
