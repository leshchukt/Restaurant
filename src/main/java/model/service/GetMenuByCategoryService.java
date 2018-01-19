package model.service;

import model.entity.Menu;

import java.util.List;

public interface GetMenuByCategoryService {
    String NO_ID_EXCEPTION_MESSAGE = "No such id in database";

    List<Menu> getMenuByCategory(int idCategory);
}
