package com.olehhilchenko.repository;

import java.util.List;

public interface GenericRepository<L, T> {

    long insert(T object);

    void update(T object);

    T select(L id);

    void delete(T object);

    List<T> getAll();
}
