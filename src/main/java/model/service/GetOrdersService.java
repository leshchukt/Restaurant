package model.service;

import model.entity.Order;
import model.entity.User;

import java.util.List;

public interface GetOrdersService {
    List<Order> getOrdersByClient(User client);
}
