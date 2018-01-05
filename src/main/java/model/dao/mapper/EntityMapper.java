package model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface EntityMapper<E> {
    E extractFromResultSet(ResultSet resultSet) throws SQLException;
    E makeUnique(Map<Integer, E> map, E entity);
}
