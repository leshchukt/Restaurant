package model.service;

import model.entity.Menu;
import model.entity.User;

import java.util.List;

public interface CreateOrderService {
    int createOrder(User client, List<Menu> menu);
}
