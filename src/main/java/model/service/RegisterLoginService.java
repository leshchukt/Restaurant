package model.service;

import model.entity.Login;
import model.entity.User;
import model.exception.EmailAlreadyExistsException;

public interface RegisterLoginService {
    void registerUser(Login login, User user) throws EmailAlreadyExistsException;
}
