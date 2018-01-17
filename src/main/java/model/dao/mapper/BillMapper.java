package model.dao.mapper;

import model.entity.Bill;
import model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
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

        UserMapper userMapper = new UserMapper();

        while ( resultSet.next() ){
            User user = userMapper.makeUnique(
                    userMap,
                    userMapper.extractFromResultSet(resultSet)
            );
            Bill bill = makeUnique(
                    billMap,
                    extractFromResultSet(resultSet)
            );
            bill.setAdmin(user);
            bills.add(bill);
        }
        return bills;
    }

    @Override
    public Bill extractFromResultSet(ResultSet resultSet) throws SQLException {
        return Bill.builder()
                .setIdOrder(resultSet.getInt(ColumnLabel.ID_ORDER))
                .setPayment_datetime(resultSet.getTimestamp(ColumnLabel.PAYMENT_DATETIME).toLocalDateTime())
                .build();
    }

    @Override
    public Bill makeUnique(Map<Integer, Bill> map, Bill entity) {
        int key = entity.getIdOrder();
        map.putIfAbsent(key, entity);
        return map.get(key);
    }
}
