package model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface EntityMapper<E> {
    E extractFromResultSet(ResultSet resultSet) throws SQLException;
    E makeUnique(Map<Integer, E> map, E entity);
    default List<E> extractListFromResultSet(ResultSet resultSet) throws SQLException {
        throw new RuntimeException();
    }
}
