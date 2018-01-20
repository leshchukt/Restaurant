package model.dao;

import model.entity.Order;
import model.entity.User;

import java.util.List;

public interface OrderDao extends GenericDao<Order> {

    @Override
    boolean create (Order entity);

    List<Order> findByClient(User client);
}
