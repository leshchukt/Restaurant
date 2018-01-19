package model.service;

import model.entity.Menu;

import java.util.List;

public interface RemoveMenuItemService {
    void removeMealFromList(int idMenu, int amount, List<Menu> menu);
}
