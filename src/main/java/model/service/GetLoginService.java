package model.service;

import model.entity.User;

import java.util.Optional;

public interface GetLoginService {
    Optional<User> getUser(String email, String password);
}
