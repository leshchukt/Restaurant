package model.dao.implementation;

import model.dao.BillDao;
import model.entity.Bill;

import javax.crypto.spec.OAEPParameterSpec;
import java.awt.font.OpenType;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class JDBCBillDao implements BillDao {
    private Connection connection;

    public JDBCBillDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Bill entity) {

    }

    @Override
    public Optional<Bill> findById(int id) {
        return null;
    }

    @Override
    public List<Bill> findAll() {
        return null;
    }

    @Override
    public void update(Bill entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }
}
