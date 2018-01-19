package model.dao.implementation;

import model.dao.OrderDao;
import model.dao.implementation.query.OrderQuery;
import model.dao.mapper.EntityMapper;
import model.dao.mapper.OrderMapper;
import model.entity.Menu;
import model.entity.Order;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

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
            ps.setBoolean(2, entity.isAccepted());
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
        return null;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public void update(Order entity) {

    }

    @Override
    public void delete(int id) {

    }
}
