package model.dao.mapper;

import model.entity.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CategoryMapper implements EntityMapper<Category>{

    @Override
    public Category extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Category(
                resultSet.getInt(ColumnLabel.ID_CATEGORY),
                resultSet.getString(ColumnLabel.NAME)
        );
    }

    @Override
    public Category makeUnique(Map<Integer, Category> map, Category entity) {
        int key = entity.getId();
        map.putIfAbsent(key, entity);
        return map.get(key);
    }
}
