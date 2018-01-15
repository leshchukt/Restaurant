package controller.command.client;

import controller.command.Command;
import model.entity.Menu;
import model.entity.User;
import model.service.MenuService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class RemoveMealCommand implements Command {
    private static String PARAMETER_MENU = "menu";
    private static String PARAMETER_AMOUNT = "amount";

    private static String ATTRIBUTE_ORDER_MEALS = "currentOrder";
    private static String ATTRIBUTE_USER = "user";
    private static String MESSAGE_ATTRIBUTE = "message";

    private int menuId;
    private int amount;
    private User client;
    private MenuService menuService = MenuService.getInstance();

    private static final Logger LOGGER = Logger.getLogger(RemoveMealCommand.class);
    private List<Menu> menu;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        menuService.removeMealFromList(menuId, amount, menu);

        LOGGER.info("User " + client.getId() + " removed meal: " + menuId + " from order.");
        request.setAttribute(MESSAGE_ATTRIBUTE, "message.deleted");

        request.getSession().setAttribute(ATTRIBUTE_ORDER_MEALS, menu);

        return CLIENT_ORDER_PAGE;
    }

    private void initCommand(HttpServletRequest request) {
        client = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
        menu = (List<Menu>) request.getSession().getAttribute(ATTRIBUTE_ORDER_MEALS);
        menuId = Integer.parseInt(request.getParameter(PARAMETER_MENU));
        amount = Integer.parseInt(request.getParameter(PARAMETER_AMOUNT));
    }
}
