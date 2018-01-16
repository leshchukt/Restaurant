package model.dao;

import model.exception.EmailAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {
    boolean create (T entity) throws EmailAlreadyExistsException;
    Optional<T> findById(int id);
    List<T> findAll();
    void update(T entity);
    void delete(int id);
}
