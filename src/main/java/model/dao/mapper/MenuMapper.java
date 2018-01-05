package model.dao.mapper;

import model.entity.Category;
import model.entity.Menu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MenuMapper implements EntityMapper<Menu>{

    @Override
    public Menu extractFromResultSet(ResultSet resultSet) throws SQLException{
        return Menu.builder()
                .setId(resultSet.getInt("idmenu"))
                .setTitle(resultSet.getString("title"))
                .setPrice(resultSet.getDouble("price"))
                .setWeight(resultSet.getInt("weight"))
                .build();
    }

    @Override
    public Menu makeUnique(Map<Integer, Menu> map, Menu entity) {
        int key = entity.getId();
        map.putIfAbsent(key, entity);
        return map.get(key);
    }
}
