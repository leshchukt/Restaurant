package model.dao;

import model.entity.Login;

import java.util.Optional;

public interface LoginDao extends GenericDao<Login> {
    Optional<Login> findUser(String email, String password);
}
