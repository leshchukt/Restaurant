package model.dao.implementation;

import model.dao.CategoryDao;
import model.entity.Category;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class JDBCCategoryDao implements CategoryDao{
    private Connection connection;

    public JDBCCategoryDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Category entity) {

    }

    @Override
    public Optional<Category> findById(int id) {
        return null;
    }

    @Override
    public List<Category> findAll() {
        return null;
    }

    @Override
    public void update(Category entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }
}
