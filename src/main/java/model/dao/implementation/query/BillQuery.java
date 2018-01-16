package model.dao.implementation.query;

public interface BillQuery {
    String SELECT_ALL = "SELECT * FROM bill JOIN user USING (iduser)";
}
