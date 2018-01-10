package model.dao.implementation;

import model.dao.LoginDao;
import model.dao.implementation.query.LoginQuery;
import model.dao.mapper.LoginMapper;
import model.entity.Login;

import java.sql.*;
import java.util.*;

public class JDBCLoginDao implements LoginDao {
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
        try (PreparedStatement ps = connection.prepareStatement(LoginQuery.SELECT_BY_EMAIL_AND_PASSWORD)){
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getLogin(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Login getLogin(ResultSet resultSet) throws SQLException{
        return Login.builder()
                .setId(resultSet.getInt("iduser"))
                .setEmail(resultSet.getString("email"))
                .setPassword(resultSet.getString("password"))
                .build();
    }

    @Override
    public List<Login> findAll() {
        Map<Integer,Login> loginMap = new HashMap<>();

        LoginMapper loginMapper = new LoginMapper();

        try (Statement ps = connection.createStatement()){
            ResultSet rs = ps.executeQuery(LoginQuery.SELECT_ALL);
            while ( rs.next() ){
                loginMapper.makeUnique(
                        loginMap,
                        loginMapper.extractFromResultSet(rs)
                );
            }
        } catch (Exception e) {
            throw new RuntimeException("problem in findAll");
        }
        return new ArrayList<>(loginMap.values());
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
