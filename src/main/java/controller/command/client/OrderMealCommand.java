package controller.command.client;

import controller.command.Command;
import controller.command.CommandFactory;
import model.entity.Menu;
import model.entity.User;
import model.service.ClientOrderService;
import model.service.implementation.OrderService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OrderMealCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(OrderMealCommand.class);

    private final static String PARAMETER_ORDER_ID = "order.id";
    private final static String ATTRIBUTE_ORDER_MEALS = "orderMeals";
    private final static String ATTRIBUTE_USER = "user";

    private static final String REDIRECT_PAGE = "redirect:" + CommandFactory.CLIENT_HOME;

    private int idOrder;
    private List<Menu> menu;
    private User client;

    private ClientOrderService orderService = OrderService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);

        menu = orderService.getOrderMenu(idOrder);

        request.setAttribute(ATTRIBUTE_ORDER_MEALS, menu);
        LOGGER.info("Client: " + client.getId() + " got meals from order: " + idOrder);

        return REDIRECT_PAGE;
    }

    private void initCommand(HttpServletRequest request) {
        idOrder = Integer.parseInt(request.getParameter(PARAMETER_ORDER_ID));
        client = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
    }
}
