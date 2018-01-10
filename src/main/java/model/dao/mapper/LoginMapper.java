package model.dao.mapper;

import model.entity.Login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class LoginMapper implements EntityMapper<Login> {
    @Override
    public Login extractFromResultSet(ResultSet resultSet) throws SQLException {
        return Login.builder()
                .setId(resultSet.getInt("iduser"))
                .setEmail(resultSet.getString("email"))
                .setPassword(resultSet.getString("password"))
                .build();
    }

    @Override
    public Login makeUnique(Map<Integer, Login> map, Login entity) {
        Integer key = entity.getId();
        map.putIfAbsent(key, entity);
        return map.get(key);
    }
}
