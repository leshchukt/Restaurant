package model.dao;

public interface ConnectionDao extends AutoCloseable {

    void beginTransaction();

    void commitTransaction();

    void rollbackTransaction();

    void close();

}
