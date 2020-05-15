package com.olehhilchenko.service;

import java.util.List;

public interface GenericService<L, T> {

    long save(T object);

    void update(T object);

    T getById(L id);

    void deleteById(T object);

    List<T> getAll();
}
