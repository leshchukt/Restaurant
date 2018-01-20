package model.dao.implementation.query;

public interface MenuQuery {
    String SELECT_ALL = "SELECT * FROM menu JOIN category USING (idcategory)";
    String SELECT_BY_ID = "SELECT * FROM menu JOIN category USING (idcategory) WHERE idmenu = ?";
    String SELECT_BY_CATEGORY = "SELECT * FROM menu JOIN category USING (idcategory) WHERE idcategory = ?";
    String SELECT_BY_ORDER = "SELECT * FROM menu JOIN category USING (idcategory) JOIN order_has_menu USING (idmenu) " +
            "WHERE idorder = ?";
}
