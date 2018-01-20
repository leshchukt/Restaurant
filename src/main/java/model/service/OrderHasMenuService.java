package model.service;

import model.entity.Menu;

import java.util.List;

public interface OrderHasMenuService {
    String NO_ID_EXCEPTION_MESSAGE = "No such id in database";

    List<Menu> getOrderMenu(int idOrder);
}
