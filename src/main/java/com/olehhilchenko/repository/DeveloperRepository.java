package com.olehhilchenko.repository;

import com.olehhilchenko.models.Developer;

import java.util.List;

public interface DeveloperRepository {

    long insert(Developer developer);

    void update (Developer developer);

    Developer select (long id);

    void delete (Developer developer);

    List<Developer> getDeveloperList();
}
