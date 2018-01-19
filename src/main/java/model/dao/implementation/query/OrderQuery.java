package model.dao.implementation.query;

public interface OrderQuery {
    String INSERT = "INSERT INTO `order` (time_of_order, accepted, iduser) VALUES (?, ?, ?)";
    String INSERT_ORDER_HAS_MENU = "INSERT INTO order_has_menu (idorder, idmenu, amount) VALUES (?, ?, ?)";
}
