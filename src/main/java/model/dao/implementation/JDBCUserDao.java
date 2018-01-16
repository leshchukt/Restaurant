package model.dao.implementation;

import model.dao.UserDao;
import model.dao.implementation.query.UserQuery;
import model.entity.Role;
import model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class JDBCUserDao implements UserDao {
    private Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void create(User entity) {

    }

    @Override
    public Optional<User> findById(int id) {
        try (PreparedStatement ps = connection.prepareStatement(UserQuery.SELECT_BY_ID)){
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getUser(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException{
        return User.builder()
                .setId(resultSet.getInt("iduser"))
                .setNickname(resultSet.getString("nickname"))
                .setBirthDate(resultSet.getDate("birthdate").toLocalDate())
                .setRole(Role.valueOf(resultSet.getString("role")))
                .build();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(int id) {

    }
}
