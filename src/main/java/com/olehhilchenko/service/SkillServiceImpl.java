package com.olehhilchenko.service;

import com.olehhilchenko.model.Skill;
import com.olehhilchenko.repository.SkillRepository;
import com.olehhilchenko.repository.hibernate.SkillDAOImpl;

import java.util.List;

public class SkillServiceImpl implements GenericService<Long, Skill> {

    private SkillRepository skillRepository = new SkillDAOImpl();

    @Override
    public long save(Skill object) {
        return skillRepository.insert(object);
    }

    @Override
    public void update(Skill object) {
        skillRepository.update(object);
    }

    @Override
    public Skill getById(Long id) {
        return skillRepository.select(id);
    }

    @Override
    public void deleteById(Skill object) {
        skillRepository.delete(object);
    }

    @Override
    public List<Skill> getAll() {
        return skillRepository.getAll();
    }
}
