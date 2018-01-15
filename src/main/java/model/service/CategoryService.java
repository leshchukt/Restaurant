package model.service;

import model.dao.DaoFactory;
import model.entity.Category;

import java.util.List;

public class CategoryService {
    private DaoFactory daoFactory;

    private CategoryService(){
        daoFactory = DaoFactory.getInstance();
    }

    public List<Category> getAllCategories() {
        return null;
    }

    public Category getById(int categoryId) {
        return null;
    }

    private static class Holder{
        private static CategoryService INSTANCE = new CategoryService();
    }

    public static CategoryService getInstance(){
        return Holder.INSTANCE;
    }
}
