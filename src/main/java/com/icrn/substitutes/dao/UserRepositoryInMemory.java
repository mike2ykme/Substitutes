package com.icrn.substitutes.dao;

import com.icrn.substitutes.model.UserInterface;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class UserRepositoryInMemory implements UserRepository {
    private Map<Long,UserInterface> userMap;

    public UserRepositoryInMemory() {
         this.userMap = new ConcurrentHashMap<>();
    }

    public UserRepositoryInMemory(Map<Long, UserInterface> userMap) {
        this.userMap = userMap;
    }

    @Override
    public Optional<UserInterface> getUserById(long userId) {
        return this.userMap.entrySet().stream()
                .filter(entry -> entry.getValue().getId() == userId)
                .map(Map.Entry::getValue)
                .findAny();
    }

    @Override
    public UserInterface saveUser(UserInterface user) {
        if (user.getId() == 0)
            user.setId(Math.abs(new Random().nextLong()));
        this.userMap.put(user.getId(),user);
        return user;
    }

    @Override
    public List<UserInterface> getAllusers() {
        return this.userMap.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
