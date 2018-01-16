package model.dao.mapper;

import model.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class OrderMapper implements EntityMapper<Order>{
    @Override
    public Order extractFromResultSet(ResultSet resultSet) throws SQLException {
        return Order.builder()
                .setId(resultSet.getInt(ColumnLabel.ID_ORDER))
                .setTimeOfOrder(resultSet.getTimestamp(ColumnLabel.TIME_OF_ORDER).toLocalDateTime())
                .setAccepted(resultSet.getBoolean(ColumnLabel.ACCEPTED))
                .build();

        //no client, no meals
    }

    @Override
    public Order makeUnique(Map<Integer, Order> map, Order entity) {
        int key = entity.getId();
        map.putIfAbsent(key, entity);
        return map.get(key);
    }
}
