package model.service;

import model.entity.User;

import java.util.Optional;

public interface LogInService {
    Optional<User> getUser(String email, String password);
}
