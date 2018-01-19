package model.dao.implementation;

import model.dao.LoginDao;
import model.dao.implementation.query.LoginQuery;
import model.dao.implementation.query.UserQuery;
import model.dao.mapper.EntityMapper;
import model.dao.mapper.LoginMapper;
import model.entity.Login;
import model.exception.EmailAlreadyExistsException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class JDBCLoginDao implements LoginDao {
    private static final Logger LOGGER = Logger.getLogger(JDBCLoginDao.class);

    private Connection connection;
    private EntityMapper<Login> loginMapper = new LoginMapper();

    public JDBCLoginDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Login entity) throws EmailAlreadyExistsException{
        String email = entity.getEmail();
        if (isEmailExist(email)) {
            throw new EmailAlreadyExistsException(email);
        }
        try (PreparedStatement statement = connection
                .prepareStatement(LoginQuery.INSERT, Statement.RETURN_GENERATED_KEYS)){
            statement.setInt(1, entity.getId());
            statement.setString(2, email);
            statement.setString(3, entity.getPassword());
            return statement.executeUpdate() > 0;
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<Login> findById(int id) {
        return null;
    }

    @Override
    public Optional<Login> findUser(String email, String password) {
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
            return loginMapper.extractListFromResultSet(resultSet);
        } catch (Exception e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Login entity) {

    }

    @Override
    public void delete(int id) {

    }

    private boolean isEmailExist(String email) {
        Set<String> emails = findAllEmails();
        return emails.contains(email);
    }

    private Set<String> findAllEmails() {
        Set<String> emails = new HashSet<>();
        try (Statement ps = connection.createStatement()){
            ResultSet resultSet = ps.executeQuery(LoginQuery.SELECT_ALL_EMAILS);
            while (resultSet.next()) {
                emails.add(resultSet.getString("email"));
            }
            return emails;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }
}
