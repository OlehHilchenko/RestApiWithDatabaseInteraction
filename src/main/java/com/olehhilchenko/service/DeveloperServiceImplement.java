package com.olehhilchenko.service;

        import com.olehhilchenko.models.Developer;
        import com.olehhilchenko.repository.DeveloperRepository;
        import com.olehhilchenko.repository.DeveloperRepositoryImplement;

        import java.util.List;

public class DeveloperServiceImplement implements DeveloperService {

    private DeveloperRepository developerRepository = new DeveloperRepositoryImplement();

    @Override
    public long add(Developer developer) {
        return developerRepository.insert(developer);
    }

    @Override
    public void update(Developer developer) {
        developerRepository.update(developer);
    }

    @Override
    public Developer get(long id) {
        return developerRepository.select(id);
    }

    @Override
    public void remove(Developer developer) {
        developerRepository.delete(developer);
    }

    @Override
    public List<Developer> getDeveloperList() {
        return developerRepository.getDeveloperList();
    }
}
