package model.dao.implementation;

import com.mysql.jdbc.util.ResultSetUtil;
import model.dao.BillDao;
import model.dao.implementation.query.BillQuery;
import model.dao.mapper.BillMapper;
import model.dao.mapper.UserMapper;
import model.entity.*;
import org.apache.log4j.Logger;

import javax.crypto.spec.OAEPParameterSpec;
import java.awt.font.OpenType;
import java.sql.*;
import java.util.*;

public class JDBCBillDao implements BillDao {
    private static final Logger LOGGER = Logger.getLogger(JDBCBillDao.class);

    private Connection connection;
    private BillMapper billMapper = new BillMapper();

    public JDBCBillDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Bill entity) {
        return false;
    }

    @Override
    public Optional<Bill> findById(int id) {
        return null;
    }

    @Override
    public List<Bill> findAll() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(BillQuery.SELECT_ALL);
            return billMapper.extractListFromResultSet(resultSet);
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Bill entity) {

    }

    @Override
    public void delete(int id) {

    }
}
