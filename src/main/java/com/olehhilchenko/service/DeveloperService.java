package com.olehhilchenko.service;

import com.olehhilchenko.models.Developer;

import java.util.List;

public interface DeveloperService {

    long add(Developer developer);

    void update(Developer developer);

    Developer get(long id);

    void remove(Developer developer);

    List<Developer> getDeveloperList();
}
