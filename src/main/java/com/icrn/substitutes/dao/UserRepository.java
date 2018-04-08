package com.icrn.substitutes.dao;

import com.icrn.substitutes.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> getUserById(long userId);

    User saveUser(User user);

    List<User> getAllusers();
}
