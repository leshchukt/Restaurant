package controller.command.client;

import controller.command.Command;
import controller.command.CommandFactory;
import model.entity.Menu;
import model.entity.User;
import model.service.MenuService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class AddMealToOrderCommand implements Command{
    private static String PARAMETER_MEAL = "meal";
    private static String PARAMETER_AMOUNT = "amount";
    private static String MESSAGE_ATTRIBUTE = "message";
    private static String ATTRIBUTE_ORDER_MEALS = "currentOrder";
    private static String ATTRIBUTE_USER = "user";

    private static String REDIRECT_PAGE = "redirect:" + CommandFactory.MENU_SEARCH;

    private static final Logger LOGGER = Logger.getLogger(AddMealToOrderCommand.class);

    private int idMenu;
    private int amount;
    private User client;
    private MenuService menuService = MenuService.getInstance();
    private List<Menu> menu;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);

        menuService.addMealToList(idMenu, amount, menu);

        request.getSession().setAttribute(ATTRIBUTE_ORDER_MEALS, menu);
        LOGGER.info("User " + client.getId() + " added meal: " + idMenu + " to order.");
        request.setAttribute(MESSAGE_ATTRIBUTE, "message.added");

        return REDIRECT_PAGE;

    }

    private void initCommand(HttpServletRequest request) {
        idMenu = Integer.parseInt(request.getParameter(PARAMETER_MEAL));
        amount = Integer.parseInt(request.getParameter(PARAMETER_AMOUNT));
        client = (User)request.getSession().getAttribute(ATTRIBUTE_USER);
        menu = getCurrentOrderMeals(request.getSession());
    }

    @SuppressWarnings("unchecked")
    private List<Menu> getCurrentOrderMeals(HttpSession session) {

        List<Menu> currentOrderMeals = (List<Menu>) session.getAttribute(ATTRIBUTE_ORDER_MEALS);
        if(currentOrderMeals == null){
            currentOrderMeals = new ArrayList<>();
        }
        return currentOrderMeals;
    }
}
