package model.dao.implementation;

import model.dao.OrderDao;
import model.dao.implementation.query.OrderQuery;
import model.dao.mapper.*;
import model.entity.Category;
import model.entity.Menu;
import model.entity.Order;
import model.entity.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

public class JDBCOrderDao implements OrderDao {
    private static final Logger LOGGER = Logger.getLogger(JDBCOrderDao.class);

    private EntityMapper<Order> orderMapper = new OrderMapper();

    private Connection connection;

    public JDBCOrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Order entity) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(OrderQuery.INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, Timestamp.valueOf(entity.getTimeOfOrder()));
            ps.setInt(2, entity.getAccepted());
            ps.setInt(3, entity.getClient().getId());
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            if(resultSet.next()){
                entity.setId(resultSet.getInt(1));
                if(insertIntoOrderHasMenu(entity)){
                    connection.commit();
                    return true;
                }
                else {
                    connection.rollback();
                    return false;
                }
            }
            else return false;
        } catch (SQLException e) {
            LOGGER.error(e);
            throw  new RuntimeException(e);
        }
    }

    @Override
    public List<Order> findByClient(User client) {
        try (PreparedStatement ps = connection.prepareStatement(OrderQuery.SELECT_BY_CLIENT)){
            ps.setInt(1, client.getId());
            ResultSet resultSet = ps.executeQuery();

            return orderMapper.extractListFromResultSet(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> getWithLimit(int start, int end) {
        try (PreparedStatement ps = connection.prepareStatement(OrderQuery.SELECT_WITH_LIMIT)){
            ps.setInt(1, start);
            ps.setInt(2, end);
            ResultSet resultSet = ps.executeQuery();

            return orderMapper.extractListFromResultSet(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }

    }

    private boolean insertIntoOrderHasMenu(Order entity) throws SQLException{
        for(Menu menuItem : entity.getMenu()){
            PreparedStatement statement = connection.prepareStatement(OrderQuery.INSERT_ORDER_HAS_MENU);
            statement.setInt(1, entity.getId());
            statement.setInt(2, menuItem.getId());
            statement.setInt(3, menuItem.getAmount());

            if (!(statement.executeUpdate() > 0)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Optional<Order> findById(int id) {
        try (PreparedStatement ps = connection.prepareStatement(OrderQuery.SELECT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            return getOrderWithMenu(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    private Optional<Order> getOrderWithMenu(ResultSet resultSet) throws SQLException {
        Map<Integer,Order> orderMap = new HashMap<>();
        List<Menu> menu = new ArrayList<>();
        Order order = null;
        while (resultSet.next()) {
            order = orderMapper.makeUnique(orderMap, orderMapper.extractFromResultSet(resultSet));
            Menu menuItem = new MenuMapper().extractFromResultSet(resultSet);
            menuItem.setAmount(resultSet.getInt(ColumnLabel.AMOUNT));
            menu.add(menuItem);
        }
        order.setMenu(menu);
        return Optional.of(order);
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(OrderQuery.SELECT_ALL);
            while (resultSet.next()) {
                orders.add(orderMapper.extractFromResultSet(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Order entity) {
        try (PreparedStatement ps = connection.prepareStatement(OrderQuery.UPDATE)) {
            ps.setInt(1, entity.getIdClient());
            ps.setTimestamp(2, Timestamp.valueOf(entity.getTimeOfOrder()));
            ps.setInt(3, entity.getAccepted());
            ps.setInt(4, entity.getId());
            return ps.executeUpdate();
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw  new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {

    }
}
