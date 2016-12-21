package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;


import java.util.Collection;

/**
 * GKislin
 * 15.06.2015.
 */
public interface MealService {
    Meal save(Meal meal);

    void delete(int id, int userId);

    Meal get(int id, int userId);

    Collection<Meal> getAll(int userId);

    void update(Meal meal, int userId);
}
