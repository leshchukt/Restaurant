package model.dao.mapper;

import model.entity.Bill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class BillMapper implements EntityMapper<Bill> {
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
