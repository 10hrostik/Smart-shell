package com.api.repositories;

import com.api.model.adress.Adress;
import com.api.model.adress.City;
import com.api.model.adress.Country;
import com.api.model.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

@Repository
public class AdressRepository<T extends Adress> implements ResidenseRepository<T> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public T findById(Long id) {
        return (T) em.find(Adress.class, id);
    }

    @Override
    public T findByName(String name) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Adress> criteriaQuery = criteriaBuilder.createQuery(Adress.class);
        Root<Adress> root = criteriaQuery.from(Adress.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("name"), name));

        return (T) em.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public T findByDetails(String... details) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Adress> criteriaQuery = criteriaBuilder.createQuery(Adress.class);
        Root<Adress> root = criteriaQuery.from(Adress.class);
        Join<Adress, City> cityJoin = root.join("city");
        Join<City, Country> countryJoin = cityJoin.join("country");
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(cityJoin.get("name"), details[0]),
                        criteriaBuilder.equal(countryJoin.get("name"), details[1]),
                        criteriaBuilder.equal(root.get("name"), details[2]));

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
