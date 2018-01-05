package model.dao.implementation;

import model.dao.*;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {

    private DataSource dataSource;

    public JDBCDaoFactory() {
        try {
            InitialContext initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/restaurant");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BillDao createBillDao() {
        return new JDBCBillDao(getConnection());
    }

    @Override
    public CategoryDao createCategoryDao() {
        return new JDBCCategoryDao(getConnection());
    }

    @Override
    public LoginDao createLoginDao() {
        return new JDBCLoginDao(getConnection());
    }

    @Override
    public MenuDao createMenuDao() {
        return new JDBCMenuDao(getConnection());
    }

    @Override
    public OrderDao createOrderDao() {
        return new JDBCOrderDao(getConnection());
    }

    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(getConnection());
    }
}
