package model.service;

import model.entity.Order;

public interface AdminOrderService {
    int getSummaryPrice(Order order);
}
