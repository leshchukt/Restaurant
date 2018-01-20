package model.service;

import model.entity.Order;

import java.util.List;

public interface ActiveOrdersService {
    List<Order> getActiveOrders();
}
