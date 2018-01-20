package model.dao.implementation;

import model.dao.UserDao;
import model.dao.implementation.query.UserQuery;
import model.dao.mapper.EntityMapper;
import model.dao.mapper.UserMapper;
import model.entity.Role;
import model.entity.User;
import org.apache.log4j.Logger;
import sun.rmi.runtime.Log;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class JDBCUserDao implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(JDBCUserDao.class);

    private Connection connection;
    private EntityMapper<User> userMapper = new UserMapper();

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(User entity) {
        try (PreparedStatement statement = connection
                .prepareStatement(UserQuery.INSERT, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, entity.getNickname());
            statement.setDate(2, Date.valueOf(entity.getBirthDate()));
            statement.setString(3, entity.getRole().toString());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                entity.setId(resultSet.getInt(1));
                return true;
            }
            else return false;
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findById(int id) {
        try (PreparedStatement ps = connection.prepareStatement(UserQuery.SELECT_BY_ID)){
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return Optional.of(userMapper.extractFromResultSet(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public int update(User entity) {
        return 0;
    }

    @Override
    public void delete(int id) {

    }
}
