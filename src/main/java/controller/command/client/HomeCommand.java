package controller.command.client;

import controller.command.Command;
import model.entity.Order;
import model.entity.User;
import model.service.OrderService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HomeCommand implements Command {
    private static String ATTRIBUTE_CLIENT_ORDERS = "ordersHistory";
    private static String ATTRIBUTE_USER = "user";

    private OrderService service = OrderService.getInstance();

    private User client;
    private List<Order> orders;

    private static final Logger LOGGER = Logger.getLogger(HomeCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);

        orders = service.ordersOfUser(client);

        request.setAttribute(ATTRIBUTE_CLIENT_ORDERS, orders);
        LOGGER.info("Client: " + client.getId() + " entered home page");

        return CLIENT_PAGE;
    }

    private void initCommand(HttpServletRequest request) {
        client = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
    }
}
