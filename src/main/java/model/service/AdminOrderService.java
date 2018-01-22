package model.service;

import model.entity.Order;

import java.util.List;

public interface AdminOrderService {
    Order getFullInfoAboutOrder(int idOrder);
    List<Order> getActiveOrders();
}
