package model.dao.mapper;

import model.entity.Bill;
import model.entity.Order;
import model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillMapper implements EntityMapper<Bill> {

    @Override
    public List<Bill> extractListFromResultSet(ResultSet resultSet) throws SQLException {
        List<Bill> bills = new ArrayList<>();

        Map<Integer, Bill> billMap = new HashMap<>();
        Map<Integer, User> userMap = new HashMap<>();
        Map<Integer, Order> orderMap = new HashMap<>();

        EntityMapper<User> userMapper = new UserMapper();
        EntityMapper<Order> orderMapper = new OrderMapper();

        while ( resultSet.next() ){
            User user = userMapper.makeUnique(
                    userMap,
                    userMapper.extractFromResultSet(resultSet)
            );
            Order order = orderMapper.makeUnique(
                    orderMap,
                    orderMapper.extractFromResultSet(resultSet)
            );
            Bill bill = makeUnique(
                    billMap,
                    extractFromResultSet(resultSet)
            );
            bill.setAdmin(user);
            bill.setOrder(order);
            bills.add(bill);
        }
        return bills;
    }

    @Override
    public Bill extractFromResultSet(ResultSet resultSet) throws SQLException {
        Timestamp payment_datetime = resultSet.getTimestamp(ColumnLabel.PAYMENT_DATETIME);
        return Bill.builder()
                .setIdOrder(resultSet.getInt(ColumnLabel.ID_ORDER))
                .setPayment_datetime(payment_datetime == null ? null : payment_datetime.toLocalDateTime())
                .build();
    }

    @Override
    public Bill makeUnique(Map<Integer, Bill> map, Bill entity) {
        int key = entity.getIdOrder();
        map.putIfAbsent(key, entity);
        return map.get(key);
    }
}
