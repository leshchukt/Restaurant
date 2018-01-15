package model.service;

import model.dao.DaoFactory;
import model.dao.MenuDao;
import model.dao.implementation.JDBCMenuDao;
import model.entity.Category;
import model.entity.Menu;

import java.util.List;

public class MenuService {
    DaoFactory daoFactory;

    private MenuService(){
        daoFactory = DaoFactory.getInstance();
    }

    public void addMealToList(int menuId, int amount, List<Menu> menu) {

    }

    public void removeMealFromList(int menuId, int amount, List<Menu> menu) {

    }

    public List<Menu> getMenuByCategory(int categoryId) {
        return null;
    }

    public List<Category> getAllCategories() {
        return null;
    }

    public Category getById(int category) {
        return null;
    }

    private static class Holder{
        private static MenuService INSTANCE = new MenuService();
    }

    public static MenuService getInstance(){
        return Holder.INSTANCE;
    }

    public List<Menu> getAllMenu(){
        try (MenuDao menuDao = daoFactory.createMenuDao()) {
            return menuDao.findAll();
        }
    }
}
