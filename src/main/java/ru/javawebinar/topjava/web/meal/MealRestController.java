package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        meal.setId(null);
        LOG.info("create " + meal + " " + AuthorizedUser.id());
        meal.setUserId(AuthorizedUser.id());
        return service.save(meal);
    }

    public Meal get(int id) {
        LOG.info("get " + id + " " + AuthorizedUser.id());
        return service.get(id, AuthorizedUser.id());
    }

    public Collection<Meal> getAll() {
        LOG.info("getAll " + AuthorizedUser.id());
        return service.getAll(AuthorizedUser.id());
    }

    public void update(Meal meal) {
        LOG.info("update " + meal + " " + AuthorizedUser.id());
        service.update(meal, AuthorizedUser.id());
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id, AuthorizedUser.id());
    }





}
