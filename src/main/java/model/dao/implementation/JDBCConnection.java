package model.dao.implementation;

import model.dao.ConnectionDao;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCConnection implements ConnectionDao {
    private static final Logger LOGGER = Logger.getLogger(JDBCConnection.class);

    private Connection connection;
    private boolean transactionUncommitted = false;

    JDBCConnection(Connection connection) {
        this.connection = connection;
    }

    Connection getConnection() {
        return connection;
    }

    @Override
    public void beginTransaction() {
        try {
            connection.setAutoCommit(false);
            transactionUncommitted = true;
        } catch (SQLException e) {
            LOGGER.error("Error during transaction beginning: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void commitTransaction() {
        try {
            connection.commit();
            transactionUncommitted = false;
        } catch (SQLException e) {
            LOGGER.error("Error during the transaction commit: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void rollbackTransaction() {
        try {
            connection.rollback();
            transactionUncommitted = false;
        } catch (SQLException e) {
            LOGGER.error("Error during the transaction rollback: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        if (transactionUncommitted) {
            rollbackTransaction();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Error during connection closing: ", e);
            throw new RuntimeException(e);
        }
    }
}
