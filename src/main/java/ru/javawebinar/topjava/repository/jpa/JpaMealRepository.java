package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class, userId);
        meal.setUser(ref);
        if (meal.isNew()) {
            em.persist(meal);
        } else {
//            if (get(meal.getId(), userId) == null)
//                return null;
//            em.merge(meal);
            Query query = em.createQuery("UPDATE Meal m SET m.dateTime=:dateTime, m.description=:description, " +
                    " m.calories=:calories WHERE m.id=:id and m.user.id=:userId");
            query.setParameter("id", meal.getId());
            query.setParameter("userId", userId);
            query.setParameter("dateTime", meal.getDateTime());
            query.setParameter("description", meal.getDescription());
            query.setParameter("calories", meal.getCalories());
            if (query.executeUpdate() == 0) {
                return null;
            }
        }
        return meal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        Query query = em.createQuery("DELETE FROM Meal m WHERE m.id=:id and m.user.id=:userId");
        query.setParameter("id", id);
        query.setParameter("userId", userId);
        return query.executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Query query = em.createQuery("SELECT m FROM Meal m WHERE m.id=:id and m.user.id=:userId");
        query.setParameter("id", id);
        query.setParameter("userId", userId);
        List<Meal> meals = query.getResultList();
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        Query query = em.createQuery("SELECT m FROM Meal m JOIN m.user u WHERE u.id=:userId order by m.dateTime desc ");
        query.setParameter("userId", userId);
        List<Meal> meals = query.getResultList();
        return meals;
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        Query query = em.createQuery("SELECT m FROM Meal m WHERE m.user.id=:userId and m.dateTime > :startDate and m.dateTime < :endDate order by m.dateTime desc");
        query.setParameter("userId", userId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        List<Meal> meals = query.getResultList();
        return meals;
    }
}