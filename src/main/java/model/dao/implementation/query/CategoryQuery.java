package model.dao.implementation.query;

public interface CategoryQuery {
    String SELECT_ALL = "SELECT * FROM category";
    String SELECT_BY_ID = "SELECT * FROM category WHERE idcategory = ?";
}
