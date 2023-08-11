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

    public User getUserByUsernameOrEmail(String credential) {
        return (User) getUsernameOrEmailQuery(credential).getSingleResult();
    }

    @Transactional
    public void persistUser(User user) {
        em.persist(user);
    }

    @Transactional
    public void update(User user) {
        em.merge(user);
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public void delete(Long id) {
        em.remove(findById(id));
    }

    public boolean isUser(String username, String email) {
        return !getUsernameOrEmailQuery(username, email).getResultList().isEmpty();
    }

    private Query getUsernameOrEmailQuery(String... credential) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        Predicate equalUsername = criteriaBuilder.equal(root.get("username"), credential[0]);
        Predicate equalEmail = criteriaBuilder.equal(root.get("email"), credential[credential.length - 1]);
        criteriaQuery.select(root)
                .where(criteriaBuilder.or(equalUsername, equalEmail));

        return em.createQuery(criteriaQuery);
    }
}
