package model.service.implementation;

import model.dao.CategoryDao;
import model.dao.ConnectionDao;
import model.dao.DaoFactory;
import model.dao.MenuDao;
import model.entity.Category;
import model.entity.Menu;
import model.service.AddMenuItemService;
import model.service.GetMenuByCategoryService;
import model.service.RemoveMenuItemService;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class MenuService implements AddMenuItemService, RemoveMenuItemService, GetMenuByCategoryService {
    DaoFactory daoFactory;

    private MenuService(){
        daoFactory = DaoFactory.getInstance();
    }

    private static class Holder{
        private static MenuService INSTANCE = new MenuService();
    }

    public static MenuService getInstance(){
        return Holder.INSTANCE;
    }

    @Override
    public void addMealToList(int idMenu, int amount, List<Menu> menu) {
        Menu newMenuItem = getMenuItemWithAmount(idMenu, amount);
        if (menu.contains(newMenuItem)) {
            for (Menu menuItem : menu) {
                if (menuItem.equals(newMenuItem)) {
                    menuItem.setAmount(menuItem.getAmount() + newMenuItem.getAmount());
                }
            }
        } else {
            menu.add(newMenuItem);
        }
    }

    private Menu getMenuItemWithAmount(int idMenu, int amount) {
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()){
            MenuDao menuDao = daoFactory.createMenuDao(connectionDao);
            Optional<Menu> menuItem = menuDao.findById(idMenu);
            if (menuItem.isPresent()) {
                menuItem.get().setAmount(amount);
                return menuItem.get();
            } else {
                throw new RuntimeException(NO_ID_EXCEPTION_MESSAGE);
            }
        }
    }

    @Override
    public void removeMealFromList(int idMenu, int amount, List<Menu> menu) {
        Menu removedMenuItem = getMenuItemWithAmount(idMenu, amount);

        for (Iterator<Menu> it = menu.iterator(); it.hasNext();) {
            Menu menuItem = it.next();
            if (menuItem.equals(removedMenuItem)) {
                int restAmount = menuItem.getAmount() - removedMenuItem.getAmount();
                if (restAmount == 0) {
                    it.remove();
                } else {
                    menuItem.setAmount(restAmount);
                }
            }
        }
    }

    @Override
    public List<Menu> getMenuByCategory(int idCategory) {
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()){
            MenuDao menuDao = daoFactory.createMenuDao(connectionDao);
            CategoryDao categoryDao = daoFactory.createCategoryDao(connectionDao);
            Optional<Category> category = categoryDao.findById(idCategory);
            if (category.isPresent()) {
                return menuDao.findByCategory(category.get());
            }
            throw new RuntimeException(NO_ID_EXCEPTION_MESSAGE);
        }
    }

}
