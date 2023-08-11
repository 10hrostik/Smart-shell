package com.api.repositories;

public interface ResidenseRepository<T> {
    T findById(Long id);

    T findByName(String name);

    T findByDetails(String... details);

    void persist(T entity);

    void update(T entity);
}
