package controller.command.client;

import controller.command.Command;
import model.entity.Category;
import model.service.CategoryService;
import model.service.MenuService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchMenuCommand implements Command {
    private static final String ATTRIBUTE_MENU = "menu";
    private static final String ATTRIBUTE_CATEGORIES = "categories";
    private static final String ATTRIBUTE_CURRENT = "currentCategory";

    private static final String PARAMETER_CATEGORY = "category";

    MenuService menuService = MenuService.getInstance();
    CategoryService categoryService = CategoryService.getInstance();

    private int categoryId;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        setAttributes(request);

        return CLIENT_MENU_PAGE;
    }

    private void setAttributes(HttpServletRequest request) {
        request.getSession().setAttribute(ATTRIBUTE_MENU, menuService.getMenuByCategory(categoryId));
        request.setAttribute(ATTRIBUTE_CATEGORIES, categoryService.getAllCategories());
        request.getSession().setAttribute(ATTRIBUTE_CURRENT, categoryService.getById(categoryId));
    }

    private void initCommand(HttpServletRequest request) {
        if(request.getParameter(PARAMETER_CATEGORY)!= null) {
            categoryId = Integer.parseInt(request.getParameter(PARAMETER_CATEGORY));
            return;
        }
        if(request.getSession().getAttribute(ATTRIBUTE_CURRENT) != null ){
            categoryId = ((Category)request.getSession().getAttribute(ATTRIBUTE_CURRENT)).getId();
            return;
        }
        categoryId = 1;
    }
}
