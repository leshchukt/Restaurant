package model.dao;

import model.entity.Category;
import model.entity.Menu;

import java.util.List;

public interface MenuDao extends GenericDao<Menu> {
    List<Menu> findByCategory(Category category);
}
