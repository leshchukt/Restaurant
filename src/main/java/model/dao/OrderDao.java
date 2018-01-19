package model.dao;

import model.entity.Order;

public interface OrderDao extends GenericDao<Order> {
    boolean create (Order entity);
}
