package model.dao.implementation.query;

public interface LoginQuery {
    String SELECT_ALL = "SELECT * FROM login";
    String SELECT_BY_EMAIL_AND_PASSWORD = "SELECT * FROM login WHERE email = ? AND password = ?";
}
