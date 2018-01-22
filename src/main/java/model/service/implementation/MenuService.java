package model.service.implementation;

import model.dao.CategoryDao;
import model.dao.ConnectionDao;
import model.dao.DaoFactory;
import model.dao.MenuDao;
import model.entity.Category;
import model.entity.Menu;
import model.service.ClientMenuService;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * This service execute all operations with Menu via dao implementation
 */
public class MenuService implements ClientMenuService {
    DaoFactory daoFactory;

    private MenuService() {
        daoFactory = DaoFactory.getInstance();
    }

    public static MenuService getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Adding menuItem to menu list
     *
     * @param idMenu to add
     * @param amount to add
     * @param menu
     */
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

    /**
     * Set amount to menu, which is found by idMenu
     *
     * @param idMenu
     * @param amount
     * @return menu entity with adjusted amount
     */
    public Menu getMenuItemWithAmount(int idMenu, int amount) {
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()) {
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

    /**
     * Removes menuItem from menu list
     *
     * @param idMenu to remove
     * @param amount to remove
     * @param menu
     */
    @Override
    public void removeMealFromList(int idMenu, int amount, List<Menu> menu) {
        Menu removedMenuItem = getMenuItemWithAmount(idMenu, amount);

        for (Iterator<Menu> it = menu.iterator(); it.hasNext(); ) {
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

    /**
     * find menuItems by idCategory
     *
     * @param idCategory
     * @return menu list in this category
     */
    @Override
    public List<Menu> getMenuByCategory(int idCategory) {
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()) {
            MenuDao menuDao = daoFactory.createMenuDao(connectionDao);
            CategoryDao categoryDao = daoFactory.createCategoryDao(connectionDao);
            Optional<Category> category = categoryDao.findById(idCategory);
            if (category.isPresent()) {
                return menuDao.findByCategory(category.get());
            }
            throw new RuntimeException(NO_ID_EXCEPTION_MESSAGE);
        }
    }

    private static class Holder {
        private static MenuService INSTANCE = new MenuService();
    }

}
