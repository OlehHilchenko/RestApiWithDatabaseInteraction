package com.olehhilchenko.service;

        import com.olehhilchenko.model.Developer;
        import com.olehhilchenko.repository.DeveloperRepository;
        import com.olehhilchenko.repository.hibernate.DeveloperDAOImpl;

        import java.util.List;

public class DeveloperServiceImpl implements GenericService<Long, Developer> {

    private DeveloperRepository developerRepository = new DeveloperDAOImpl();

    @Override
    public long save(Developer developer) {
        return developerRepository.insert(developer);
    }

    @Override
    public void update(Developer developer) {
        developerRepository.update(developer);
    }

    @Override
    public Developer getById(Long id) {
        return developerRepository.select(id);
    }

    @Override
    public void deleteById(Developer developer) {
        developerRepository.delete(developer);
    }

    @Override
    public List<Developer> getAll() {
        return developerRepository.getAll();
    }
}
