package model.dao.mapper;

import model.entity.Role;
import model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserMapper implements EntityMapper<User> {

    @Override
    public User extractFromResultSet(ResultSet resultSet) throws SQLException {
        return User.builder()
                .setId(resultSet.getInt(ColumnLabel.ID_USER))
                .setNickname(resultSet.getString(ColumnLabel.NICKNAME))
                .setRole(Role.valueOf(resultSet.getString(ColumnLabel.ROLE)))
                .setBirthDate(resultSet.getDate(ColumnLabel.BIRTH_DATE).toLocalDate())
                .build();
    }

    @Override
    public User makeUnique(Map<Integer, User> map, User entity) {
        int key = entity.getId();
        map.putIfAbsent(key, entity);
        return map.get(key);
    }
}
