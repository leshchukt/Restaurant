package model.service;

import model.dao.DaoFactory;
import model.dao.MenuDao;
import model.dao.implementation.JDBCMenuDao;
import model.entity.Menu;

import java.util.List;

public class MenuService {
    DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Menu> getAllMenu(){
        try (MenuDao menuDao = daoFactory.createMenuDao()) {
            return menuDao.findAll();
        }
    }
}
