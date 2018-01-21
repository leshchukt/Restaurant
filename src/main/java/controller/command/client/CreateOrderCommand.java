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

public class CreateOrderCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(CreateOrderCommand.class);

    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_CURRENT_ORDER_MEALS = "currentOrder";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String CREATED_ORDER_MESSAGE = "message.order.created";
    private static final String REDIRECT_PAGE = "redirect:" + CommandFactory.CLIENT_HOME;

    private ClientOrderService orderService = OrderService.getInstance();

    private User client;
    private List<Menu> menu;
    private int idOrder;


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);

        idOrder = orderService.createOrder(client, menu);

        request.getSession().setAttribute(ATTRIBUTE_CURRENT_ORDER_MEALS, null);
        request.setAttribute(ATTRIBUTE_MESSAGE, CREATED_ORDER_MESSAGE);
        LOGGER.info("Client: " + client.getId() + " created order: " + idOrder);

        return REDIRECT_PAGE;
    }

    @SuppressWarnings("unchecked")
    private void initCommand(HttpServletRequest request) {
        client = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
        menu = (List<Menu>) request.getSession().getAttribute(ATTRIBUTE_CURRENT_ORDER_MEALS);
    }
}
