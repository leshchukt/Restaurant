package controller.command.client;

import controller.command.Command;
import controller.command.CommandFactory;
import model.entity.Menu;
import model.entity.User;
import model.service.OrderService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OrderMealCommand implements Command {
    private final static String PARAMETER_ORDER_ID = "order.id";
    private final static String ATTRIBUTE_ORDER_MEALS = "orderMeals";
    private final static String ATTRIBUTE_USER = "user";

    private static final String REDIRECT_PAGE = "redirect:" + CommandFactory.CLIENT_HOME;

    private int orderId;
    private List<Menu> menu;
    private User client;

    private OrderService orderService = OrderService.getInstance();

    private static final Logger LOGGER = Logger.getLogger(OrderMealCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);

        menu = orderService.getOrderMeals(orderId);

        request.setAttribute(ATTRIBUTE_ORDER_MEALS, menu);
        LOGGER.info("Client: " + client.getId() + " got meals from order: " + orderId);

        return REDIRECT_PAGE;
    }

    private void initCommand(HttpServletRequest request) {
        orderId = Integer.parseInt(request.getParameter(PARAMETER_ORDER_ID));
        client = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
    }
}
