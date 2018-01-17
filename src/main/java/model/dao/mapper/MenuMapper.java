package model.dao.mapper;

import model.entity.Category;
import model.entity.Menu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuMapper implements EntityMapper<Menu>{
    @Override
    public List<Menu> extractListFromResultSet(ResultSet resultSet) throws SQLException {
        List<Menu> meals = new ArrayList<>();

        Map<Integer,Menu> menuMap = new HashMap<>();
        Map<Integer,Category> categoryMap = new HashMap<>();

        CategoryMapper categoryMapper = new CategoryMapper();

        while ( resultSet.next() ) {
            Category category = categoryMapper.makeUnique(
                    categoryMap,
                    categoryMapper.extractFromResultSet(resultSet)
            );
            Menu menu = makeUnique(
                    menuMap,
                    extractFromResultSet(resultSet)
            );
            menu.setCategory(category);
            meals.add(menu);
        }
        return meals;
    }

    @Override
    public Menu extractFromResultSet(ResultSet resultSet) throws SQLException{
        return Menu.builder()
                .setId(resultSet.getInt(ColumnLabel.ID_MENU))
                .setTitle(resultSet.getString(ColumnLabel.TITLE))
                .setPrice(resultSet.getDouble(ColumnLabel.PRICE))
                .setWeight(resultSet.getInt(ColumnLabel.WEIGHT))
                .build();
    }

    @Override
    public Menu makeUnique(Map<Integer, Menu> map, Menu entity) {
        int key = entity.getId();
        map.putIfAbsent(key, entity);
        return map.get(key);
    }
}
