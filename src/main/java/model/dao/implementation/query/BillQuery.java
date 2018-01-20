package model.dao.implementation.query;

public interface BillQuery {
    String SELECT_ALL = "SELECT * FROM bill JOIN `order` USING (idorder) JOIN user WHERE iduser = idadmin";
    String SELECT_BY_ID = "SELECT * FROM bill JOIN `order` USING (idorder) JOIN user " +
            "WHERE iduser = idadmin AND idorder = ?";
    String SELECT_BY_CLIENT = "SELECT * FROM bill JOIN `order` USING (idorder) JOIN user " +
            "WHERE iduser = idadmin AND idclient = ?";
}
