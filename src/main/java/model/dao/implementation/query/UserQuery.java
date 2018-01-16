package model.dao.implementation.query;

public interface UserQuery {
    String SELECT_BY_ID = "SELECT * FROM user WHERE iduser = ?";
    String INSERT = "INSERT INTO user (nickname, birthdate, role) VALUES (?, ?, ?)";
}
