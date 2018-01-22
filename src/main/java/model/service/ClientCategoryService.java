package model.service;

import model.entity.Category;

import java.util.List;

public interface ClientCategoryService {
    String NO_ID_EXCEPTION_MESSAGE = "No such id in database";

    List<Category> getAllCategories();

    Category getById(int idCategory);
}
