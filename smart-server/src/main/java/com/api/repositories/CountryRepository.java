package com.api.repositories;

import com.api.model.adress.City;
import com.api.model.adress.Country;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class CountryRepository<T extends Country> implements ResidenseRepository<T> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public T findById(Long id) {
        return (T) em.find(Country.class, id);
    }

    @Override
    public T findByName(String name) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Country> criteriaQuery = criteriaBuilder.createQuery(Country.class);
        Root<Country> root = criteriaQuery.from(Country.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("name"), name));

        return (T) em.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public T findByDetails(String... details) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Country> criteriaQuery = criteriaBuilder.createQuery(Country.class);
        Root<Country> root = criteriaQuery.from(Country.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("name"), details[0]));

        return (T) em.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public void persist(T entity) {
        if (em.contains(entity)) {
            em.refresh(entity);
        } else {
            em.persist(entity);
        }
    }

    @Override
    public void update(T entity) {
        em.merge(entity);
    }
}
