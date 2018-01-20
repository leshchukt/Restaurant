package model.dao.mapper;

import model.entity.Menu;
import model.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderMapper implements EntityMapper<Order>{

    @Override
    public List<Order> extractListFromResultSet(ResultSet resultSet) throws SQLException {
        List<Order> orders = new ArrayList<>();

        Map<Integer,Order> orderMap = new HashMap<>();
        Map<Integer,Menu> menuMap = new HashMap<>();

        EntityMapper<Menu> menuMapper = new MenuMapper();

        while (resultSet.next()) {
            Order order = makeUnique(orderMap, extractFromResultSet(resultSet));
            Menu menu = menuMapper.makeUnique(menuMap, menuMapper.extractFromResultSet(resultSet));
            menu.setAmount(resultSet.getInt(ColumnLabel.AMOUNT));
            order.addMenuItem(menu);
            orders.add(order);
        }

        return orders;
    }

    @Override
    public Order extractFromResultSet(ResultSet resultSet) throws SQLException {
        return Order.builder()
                .setId(resultSet.getInt(ColumnLabel.ID_ORDER))
                .setTimeOfOrder(resultSet.getTimestamp(ColumnLabel.TIME_OF_ORDER).toLocalDateTime())
                .setAccepted(resultSet.getInt(ColumnLabel.ACCEPTED))
                .setIdClient(resultSet.getInt(ColumnLabel.ID_CLIENT))
                .build();
    }

    @Override
    public Order makeUnique(Map<Integer, Order> map, Order entity) {
        int key = entity.getId();
        map.putIfAbsent(key, entity);
        return map.get(key);
    }
}
