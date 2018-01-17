package model.dao.implementation.query;

public interface MenuQuery {
    String SELECT_ALL = "SELECT * FROM menu JOIN category USING (idcategory)";
    String SELECT_BY_CATEGORY = "SELECT * FROM menu WHERE idcategory = ?";
}
