package model.dao.implementation;

import model.dao.LoginDao;
import model.dao.implementation.query.LoginQuery;
import model.dao.mapper.LoginMapper;
import model.entity.Login;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

public class JDBCLoginDao implements LoginDao {

    private static final Logger LOGGER = Logger.getLogger(JDBCLoginDao.class);
    private Connection connection;

    public JDBCLoginDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Login entity) {
    }

    @Override
    public Optional<Login> findById(int id) {
        return null;
    }

    @Override
    public Optional<Login> findUser(String email, String password) {
        LoginMapper loginMapper = new LoginMapper();
        try (PreparedStatement ps = connection.prepareStatement(LoginQuery.SELECT_BY_EMAIL_AND_PASSWORD)){
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return Optional.of(loginMapper.extractFromResultSet(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Login> findAll() {
        try (Statement ps = connection.createStatement()){
            ResultSet resultSet = ps.executeQuery(LoginQuery.SELECT_ALL);
            return getFromRS(resultSet);
        } catch (Exception e) {
            LOGGER.error(e);
            throw new RuntimeException("problem in findAll");
        }
    }

    @Override
    public void update(Login entity) {

    }

    @Override
    public void delete(int id) {

    }

    public List<Login> getFromRS(ResultSet resultSet) throws SQLException{
        Map<Integer,Login> loginMap = new HashMap<>();

        LoginMapper loginMapper = new LoginMapper();

        while ( resultSet.next() ){
            loginMapper.makeUnique(
                    loginMap,
                    loginMapper.extractFromResultSet(resultSet)
            );
        }
        return new ArrayList<>(loginMap.values());
    }
}
