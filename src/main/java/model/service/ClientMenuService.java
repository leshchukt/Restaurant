package model.service;

import model.entity.Menu;

import java.util.List;

public interface ClientMenuService {
    String NO_ID_EXCEPTION_MESSAGE = "No such id in database";

    List<Menu> getMenuByCategory(int idCategory);
    void addMealToList(int idMenu, int amount, List<Menu> menu);
    void removeMealFromList(int idMenu, int amount, List<Menu> menu);
}
