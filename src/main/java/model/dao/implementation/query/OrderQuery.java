package model.dao.implementation.query;

public interface OrderQuery {
    String INSERT = "INSERT INTO `order` (time_of_order, accepted, idclient) VALUES (?, ?, ?)";
    String INSERT_ORDER_HAS_MENU = "INSERT INTO order_has_menu (idorder, idmenu, amount) VALUES (?, ?, ?)";

    String SELECT_ALL = "SELECT * FROM `order` ORDER BY time_of_order DESC";
    String SELECT_BY_ID = "SELECT * FROM `order` JOIN order_has_menu JOIN menu " +
            "WHERE `order`.idorder = order_has_menu.idorder " +
            "AND order_has_menu.idmenu = menu.idmenu " +
            "AND `order`.idorder = ?";
    String SELECT_BY_CLIENT = "SELECT * FROM `order` " +
            "WHERE idclient = ? " +
            "ORDER BY time_of_order DESC";
    String SELECT_WITH_LIMIT = "SELECT * FROM `order` LIMIT ?, ?";

    String UPDATE = "UPDATE `order` SET idclient = ?, time_of_order = ?, accepted = ? WHERE idorder = ?";
}
