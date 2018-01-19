package controller.command.client;

import controller.command.Command;
import model.entity.Category;
import model.service.GetCategoryService;
import model.service.GetMenuByCategoryService;
import model.service.implementation.CategoryService;
import model.service.implementation.MenuService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchMenuCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(SearchMenuCommand.class);

    private static final String ATTRIBUTE_MENU = "menu";
    private static final String ATTRIBUTE_CATEGORIES = "categories";
    private static final String ATTRIBUTE_CURRENT = "currentCategory";

    private static final String PARAMETER_CATEGORY = "category";

    GetMenuByCategoryService menuService = MenuService.getInstance();
    GetCategoryService categoryService = CategoryService.getInstance();

    private int categoryId;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        setAttributes(request);

        return CLIENT_MENU_PAGE;
    }

    private void setAttributes(HttpServletRequest request) {
        request.getSession().setAttribute(ATTRIBUTE_MENU, menuService.getMenuByCategory(categoryId));
        request.getSession().setAttribute(ATTRIBUTE_CURRENT, categoryService.getById(categoryId));
        request.setAttribute(ATTRIBUTE_CATEGORIES, categoryService.getAllCategories());
    }

    private void initCommand(HttpServletRequest request) {
        String parameterCategory = request.getParameter(PARAMETER_CATEGORY);
        if(parameterCategory != null) {
            categoryId = Integer.parseInt(parameterCategory);
            return;
        }
        Object attributeCategory = request.getSession().getAttribute(ATTRIBUTE_CURRENT);
        if(attributeCategory != null ){
            categoryId = ((Category)attributeCategory).getId();
            return;
        }
        categoryId = 1;
    }
}
