package model.service;

import model.entity.Bill;
import model.entity.Order;
import model.entity.User;

public interface AdminBillService {
    Bill acceptOrder(Order order, User admin);
}
