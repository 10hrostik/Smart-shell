package com.api.repositories;

import com.api.model.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager em;

    public User getUserByUsername(String username) {
        return new User();
    }

    @Transactional
    public void persistUser(User user) {
        em.persist(user);
    }

    public boolean isUser(String username, String email) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        Predicate equalUsername = criteriaBuilder.equal(root.get("username"), username);
        Predicate equalEmail = criteriaBuilder.equal(root.get("email"), email);
        criteriaQuery.select(root)
                .where(criteriaBuilder.or(equalUsername, equalEmail));

        Query query = em.createQuery(criteriaQuery);
        return !query.getResultList().isEmpty();
    }
}
