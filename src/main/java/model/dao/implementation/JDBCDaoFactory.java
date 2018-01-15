package model.dao.implementation;

import model.dao.*;
import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {

    private DataSource dataSource;

    private static final Logger LOGGER = Logger.getLogger(JDBCDaoFactory.class);

    public JDBCDaoFactory() {
        try {
            InitialContext initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/restaurant");
        } catch (Exception e) {
            LOGGER.error("Error in looking up the data source: ", e);
            throw new RuntimeException(e);
        }
    }

    public ConnectionDao getConnectionDao() {
        try {
            return new JDBCConnection(dataSource.getConnection());
        } catch (SQLException e) {
            LOGGER.error("Error during the getting connection with connection pool: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public BillDao createBillDao(ConnectionDao connectionDao) {
        JDBCConnection connection = (JDBCConnection) connectionDao;
        return new JDBCBillDao(connection.getConnection());
    }

    @Override
    public CategoryDao createCategoryDao(ConnectionDao connectionDao) {
        JDBCConnection connection = (JDBCConnection) connectionDao;
        return new JDBCCategoryDao(connection.getConnection());
    }

    @Override
    public LoginDao createLoginDao(ConnectionDao connectionDao) {
        JDBCConnection connection = (JDBCConnection) connectionDao;
        return new JDBCLoginDao(connection.getConnection());
    }

    @Override
    public MenuDao createMenuDao(ConnectionDao connectionDao) {
        JDBCConnection connection = (JDBCConnection) connectionDao;
        return new JDBCMenuDao(connection.getConnection());
    }

    @Override
    public OrderDao createOrderDao(ConnectionDao connectionDao) {
        JDBCConnection connection = (JDBCConnection) connectionDao;
        return new JDBCOrderDao(connection.getConnection());
    }

    @Override
    public UserDao createUserDao(ConnectionDao connectionDao) {
        JDBCConnection connection = (JDBCConnection) connectionDao;
        return new JDBCUserDao(connection.getConnection());
    }
}
