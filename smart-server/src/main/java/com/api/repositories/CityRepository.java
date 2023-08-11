package com.api.repositories;

import com.api.model.adress.Adress;
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
public class CityRepository<T extends City> implements ResidenseRepository<T> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public T findById(Long id) {
        return (T) em.find(City.class, id);
    }

    @Override
    public T findByName(String name) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<City> criteriaQuery = criteriaBuilder.createQuery(City.class);
        Root<City> root = criteriaQuery.from(City.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("name"), name));

        return (T) em.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public T findByDetails(String... details) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<City> criteriaQuery = criteriaBuilder.createQuery(City.class);
        Root<City> root = criteriaQuery.from(City.class);
        Join<City, Country> countryJoin = root.join("country");
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(criteriaBuilder.equal(countryJoin.get("name"), details[0]),
                        criteriaBuilder.equal(root.get("name"), details[1])));

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
