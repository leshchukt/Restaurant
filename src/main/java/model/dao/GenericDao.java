package model.dao;

import model.entity.Bill;
import model.entity.User;
import model.exception.EmailAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {
    boolean create (T entity) throws EmailAlreadyExistsException;
    Optional<T> findById(int id);
    List<T> findAll();
    int update(T entity);
    void delete(int id);
}
