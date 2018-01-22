package model.dao.implementation;

import com.mysql.jdbc.util.ResultSetUtil;
import model.dao.BillDao;
import model.dao.ConnectionDao;
import model.dao.implementation.query.BillQuery;
import model.dao.mapper.BillMapper;
import model.dao.mapper.EntityMapper;
import model.dao.mapper.OrderMapper;
import model.dao.mapper.UserMapper;
import model.entity.*;
import org.apache.log4j.Logger;

import javax.crypto.spec.OAEPParameterSpec;
import java.awt.font.OpenType;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class JDBCBillDao implements BillDao {
    private static final Logger LOGGER = Logger.getLogger(JDBCBillDao.class);

    private Connection connection;
    private EntityMapper<Bill> billMapper = new BillMapper();

    public JDBCBillDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Bill entity) {
        try (PreparedStatement ps = connection.prepareStatement(BillQuery.INSERT)) {
            ps.setTimestamp(1, null);
            ps.setInt(2, entity.getIdOrder());
            ps.setInt(3, entity.getAdmin().getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e);
            throw  new RuntimeException(e);
        }
    }

    @Override
    public List<Bill> findByClient(User client) {
        try (PreparedStatement ps
                     = connection.prepareStatement(BillQuery.SELECT_BY_CLIENT)){
            ps.setInt(1, client.getId());
            ResultSet resultSet = ps.executeQuery();

            return billMapper.extractListFromResultSet(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Bill> getWithLimit(User client, int start, int total) {
        try (PreparedStatement ps = connection.prepareStatement(BillQuery.SELECT_WITH_LIMIT)){
            ps.setInt(1, client.getId());
            ps.setInt(2, start);
            ps.setInt(3, total);
            ResultSet resultSet = ps.executeQuery();

            return billMapper.extractListFromResultSet(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Bill> findById(int id) {
        try (PreparedStatement ps
                     = connection.prepareStatement(BillQuery.SELECT_BY_ID)){
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            Bill bill = null;
            if (resultSet.next()) {
                bill = billMapper.extractFromResultSet(resultSet);
                Order order = new OrderMapper().extractFromResultSet(resultSet);
                User admin = new UserMapper().extractFromResultSet(resultSet);
                bill.setOrder(order);
                bill.setAdmin(admin);
            }
            return Optional.of(bill);
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
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
    public int update(Bill entity) {
        try (PreparedStatement ps = connection.prepareStatement(BillQuery.UPDATE)) {
            ps.setTimestamp(1, Timestamp.valueOf(entity.getPayment_datetime()));
            ps.setInt(2, entity.getIdOrder());
            return ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {

    }
}
