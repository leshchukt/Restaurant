package controller.command.client;

import controller.command.Command;
import model.entity.Menu;
import model.entity.User;
import model.service.ClientMenuService;
import model.service.implementation.MenuService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class RemoveMealCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(RemoveMealCommand.class);

    private static String PARAMETER_MENU_ITEM = "meal.id";
    private static String PARAMETER_AMOUNT = "amount";

    private static String ATTRIBUTE_ORDER_MEALS = "currentOrder";
    private static String ATTRIBUTE_USER = "user";
    private static String MESSAGE_ATTRIBUTE = "message";
    private static String MESSAGE_REMOVED = "message.removed";
    private static String MESSAGE_WRONG_AMOUNT = "message.wrong.amount";

    private ClientMenuService menuService = MenuService.getInstance();

    private int idMenu;
    private int amount;
    private User client;

    private List<Menu> menu;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        if (!validateAmount(request)) {
            request.setAttribute(MESSAGE_ATTRIBUTE, MESSAGE_WRONG_AMOUNT);
            return CLIENT_ORDER_PAGE;
        }
        initCommand(request);
        menuService.removeMealFromList(idMenu, amount, menu);

        LOGGER.info("User " + client.getId() + " removed meal: " + idMenu + " from order.");
        request.setAttribute(MESSAGE_ATTRIBUTE, MESSAGE_REMOVED);
        request.getSession().setAttribute(ATTRIBUTE_ORDER_MEALS, menu);

        return CLIENT_ORDER_PAGE;
    }

    @SuppressWarnings("Duplicates")
    private boolean validateAmount(HttpServletRequest request) {
        String sAmount = request.getParameter(PARAMETER_AMOUNT);
        try {
            int amount = Integer.parseInt(sAmount);
            if (amount < 1 || amount > 20) return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    private void initCommand(HttpServletRequest request) {
        client = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
        menu = (List<Menu>) request.getSession().getAttribute(ATTRIBUTE_ORDER_MEALS);
        idMenu = Integer.parseInt(request.getParameter(PARAMETER_MENU_ITEM));
        amount = Integer.parseInt(request.getParameter(PARAMETER_AMOUNT));
    }
}
