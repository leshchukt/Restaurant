package model.dao;

import model.entity.Category;
import model.entity.Menu;
import model.entity.Order;

import java.util.List;

public interface MenuDao extends GenericDao<Menu> {
    List<Menu> findByCategory(Category category);

    List<Menu> getMenuFromOrder(Order order);
}
