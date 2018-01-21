package model.service;

import model.entity.Order;

import java.util.List;

public interface AdminOrderService {
    int getSummaryPrice(Order order);
    Order getFullInfoAboutOrder(int idOrder);
    List<Order> getActiveOrders();
}
