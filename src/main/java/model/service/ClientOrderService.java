package model.service;

import model.entity.Menu;
import model.entity.Order;
import model.entity.User;

import java.util.List;

public interface ClientOrderService {
    String NO_ID_EXCEPTION_MESSAGE = "No such id in database";

    int createOrder(User client, List<Menu> menu);
    List<Menu> getOrderMenu(int idOrder);
    List<Order> getOrdersByClient(User client);
    List<Order> getLimitedOrders(int start, int end);
}
