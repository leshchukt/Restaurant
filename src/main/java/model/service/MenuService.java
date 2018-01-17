package model.service;

import model.dao.CategoryDao;
import model.dao.ConnectionDao;
import model.dao.DaoFactory;
import model.dao.MenuDao;
import model.dao.implementation.JDBCMenuDao;
import model.entity.Category;
import model.entity.Menu;
import model.exception.NoSuchIdException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class MenuService {
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

    public void addMealToList(int menuId, int amount, List<Menu> menu) {

    }

    public void removeMealFromList(int menuId, int amount, List<Menu> menu) {

    }

    public List<Menu> getMenuByCategory(int idCategory) throws NoSuchIdException{
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()){
            MenuDao menuDao = daoFactory.createMenuDao(connectionDao);
            CategoryDao categoryDao = daoFactory.createCategoryDao(connectionDao);
            Optional<Category> category = categoryDao.findById(idCategory);
            if (category.isPresent()) {
                return menuDao.findByCategory(category.get());
            }
            throw new NoSuchIdException("idCategory"+idCategory);
        }
    }

    public List<Menu> getAllMenu(){
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()) {
            MenuDao menuDao = daoFactory.createMenuDao(connectionDao);
            return menuDao.findAll();
        }
    }
}
