package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class MealServiceImpl implements MealService {
    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int id, int userId) {
        Meal meal = checkNotFoundWithId(repository.get(id), id);
        checkNotFoundWithId(meal.getUserId() == userId, meal.getId().intValue());
        repository.delete(id);
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = checkNotFoundWithId(repository.get(id), id);
        checkNotFoundWithId(meal.getUserId() == userId, id);
        return meal;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.getAll().stream().
                filter(meal->meal.getUserId() == userId).collect(Collectors.toList());
    }

    @Override
    public void update(Meal meal, int userId) {
        checkNotFoundWithId(meal.getUserId() == userId, meal.getId().intValue());
        repository.save(meal);
    }
}
