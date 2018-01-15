package model.dao;

import java.sql.Connection;

public interface ConnectionDao extends AutoCloseable {

    void beginTransaction();

    void commitTransaction();

    void rollbackTransaction();

    void close();

}
