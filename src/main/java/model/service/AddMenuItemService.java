package model.service;

import model.entity.Menu;

import java.util.List;

public interface AddMenuItemService {
    void addMealToList(int idMenu, int amount, List<Menu> menu);
}
