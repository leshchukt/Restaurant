package model.service.implementation;

import model.dao.CategoryDao;
import model.dao.ConnectionDao;
import model.dao.DaoFactory;
import model.entity.Category;
import model.service.ClientCategoryService;

import java.util.List;
import java.util.Optional;

public class CategoryService implements ClientCategoryService {

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

    @Override
    public List<Category> getAllCategories() {
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()) {
            CategoryDao categoryDao = daoFactory.createCategoryDao(connectionDao);
            return categoryDao.findAll();
        }
    }

    @Override
    public Category getById(int idCategory) {
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()){
            CategoryDao categoryDao = daoFactory.createCategoryDao(connectionDao);
            Optional<Category> category = categoryDao.findById(idCategory);
            if (category.isPresent()) {
                return category.get();
            }
            throw new RuntimeException(NO_ID_EXCEPTION_MESSAGE);
        }
    }



}
