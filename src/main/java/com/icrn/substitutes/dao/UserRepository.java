package com.icrn.substitutes.dao;

import com.icrn.substitutes.model.UserInterface;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<UserInterface> getUserById(long userId);

    UserInterface saveUser(UserInterface user);

    List<UserInterface> getAllusers();
}
