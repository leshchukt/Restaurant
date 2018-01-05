package model.dao.implementation;

import model.dao.LoginDao;
import model.entity.Login;

import java.sql.Connection;
import java.util.List;

public class JDBCLoginDao implements LoginDao {
    private Connection connection;

    public JDBCLoginDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Login entity) {

    }

    @Override
    public Login findById(int id) {
        return null;
    }

    @Override
    public List<Login> findAll() {
        return null;
    }

    @Override
    public void update(Login entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }
}
