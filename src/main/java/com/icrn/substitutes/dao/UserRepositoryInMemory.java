package com.icrn.substitutes.dao;

import com.icrn.substitutes.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class UserRepositoryInMemory implements UserRepository {
    private Map<Long,User> userMap;

    public UserRepositoryInMemory() {
         this.userMap = new ConcurrentHashMap<>();
    }

    public UserRepositoryInMemory(Map<Long, User> userMap) {
        this.userMap = userMap;
    }

    @Override
    public Optional<User> getUserById(long userId) {
        return this.userMap.entrySet().stream()
                .filter(entry -> entry.getValue().getId() == userId)
                .map(Map.Entry::getValue)
                .findAny();
    }

    @Override
    public User saveUser(User user) {
        if (user.getId() == 0)
            user.setId(Math.abs(new Random().nextLong()));
        this.userMap.put(user.getId(),user);
        return user;
    }

    @Override
    public List<User> getAllusers() {
        return this.userMap.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
