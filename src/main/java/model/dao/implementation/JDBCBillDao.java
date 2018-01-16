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
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(BillQuery.SELECT_ALL);
            return getFromRS(resultSet);
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

    public List<Bill> getFromRS (ResultSet resultSet) throws SQLException{
        List<Bill> bills = new ArrayList<>();

        Map<Integer, Bill> billMap = new HashMap<>();
        Map<Integer, User> userMap = new HashMap<>();

        BillMapper billMapper = new BillMapper();
        UserMapper userMapper = new UserMapper();

        while ( resultSet.next() ){
            User user = userMapper.makeUnique(
                    userMap,
                    userMapper.extractFromResultSet(resultSet)
            );
            Bill bill = billMapper.makeUnique(
                    billMap,
                    billMapper.extractFromResultSet(resultSet)
            );
            bill.setAdmin(user);
            bills.add(bill);
        }

        return bills;
    }
}
