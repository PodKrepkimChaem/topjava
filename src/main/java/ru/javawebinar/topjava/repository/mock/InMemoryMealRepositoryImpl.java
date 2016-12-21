package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id) {
        try {
            repository.remove(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Meal get(int id) {
        try {
            return repository.get(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Collection<Meal> getAll() {
        List<Meal> collectMeal = repository.values().stream().sorted(Comparator.comparing(Meal::getDateTime)).
                collect(Collectors.toList());
        Collections.reverse(collectMeal);
        return collectMeal;
    }
}

