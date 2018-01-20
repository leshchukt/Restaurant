package model.service;

import model.entity.Order;

public interface FullOrderService {
    Order getFullInfoAboutOrder(int idOrder);
}
