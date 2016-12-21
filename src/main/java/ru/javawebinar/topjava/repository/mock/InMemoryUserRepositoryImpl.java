package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.NamedEntity;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        save(new User());
        save(new User());
    }

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        if (!repository.containsKey(id)){
            return false;
        }
        repository.remove(id);
        return true;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if(user.isNew()){
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        if (!repository.containsKey(id)){
            return null;
        }
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        return Arrays.asList((User[]) repository.values().stream().
                sorted(Comparator.comparing(NamedEntity::getName)).toArray());
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);

        User[] users = (User[]) repository.values().stream().filter(u -> u.getEmail().equals(email)).toArray();
        if (users.length == 0) return null;
        return users[0];
    }
}
