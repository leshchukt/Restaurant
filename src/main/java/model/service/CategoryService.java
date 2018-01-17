package model.service;

import model.dao.CategoryDao;
import model.dao.ConnectionDao;
import model.dao.DaoFactory;
import model.entity.Category;
import model.exception.NoSuchIdException;

import java.util.List;
import java.util.Optional;

public class CategoryService {
    private DaoFactory daoFactory;

    private CategoryService(){
        daoFactory = DaoFactory.getInstance();
    }

    private static class Holder{
        private static CategoryService INSTANCE = new CategoryService();
    }

    public static CategoryService getInstance(){
        return Holder.INSTANCE;
    }


    public List<Category> getAllCategories() {
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()) {
            CategoryDao categoryDao = daoFactory.createCategoryDao(connectionDao);
            return categoryDao.findAll();
        }
    }

    public Category getById(int idCategory) throws NoSuchIdException{
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()){
            CategoryDao categoryDao = daoFactory.createCategoryDao(connectionDao);
            Optional<Category> category = categoryDao.findById(idCategory);
            if (category.isPresent()) {
                return category.get();
            }
            throw new NoSuchIdException("idCategory" + idCategory);
        }
    }



}
