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

public class CreateOrderCommand implements Command {

    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_CURRENT_ORDER_MEALS = "currentOrder";

    private static final String REDIRECT_PAGE = "redirect:" + CommandFactory.CLIENT_HOME;

    private User client;
    private List<Menu> menu;
    private int orderId;

    private OrderService orderService = OrderService.getInstance();

    private static final Logger LOGGER = Logger.getLogger(CreateOrderCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);

        orderId = orderService.createOrder(client, menu);

        request.getSession().setAttribute(ATTRIBUTE_CURRENT_ORDER_MEALS, null);
        LOGGER.info("Client: " + client.getId() + " created order: " + orderId);

        return REDIRECT_PAGE;
    }

    private void initCommand(HttpServletRequest request) {
        client = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
        menu = (List<Menu>) request.getSession().getAttribute(ATTRIBUTE_CURRENT_ORDER_MEALS);
    }
}
