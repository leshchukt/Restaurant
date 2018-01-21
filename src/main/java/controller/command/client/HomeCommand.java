package controller.command.client;

import controller.command.Command;
import model.entity.Order;
import model.entity.User;
import model.service.ClientOrderService;
import model.service.implementation.OrderService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HomeCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(HomeCommand.class);

    private static String ATTRIBUTE_CLIENT_ORDERS = "ordersHistory";
    private static String ATTRIBUTE_USER = "user";
    private static String ATTRIBUTE_PAGE = "page";

    private ClientOrderService orderService = OrderService.getInstance();

    private User client;
    private List<Order> ordersForCount;

    String page;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);

        List<Order> orders = getOrdersForPage(request);

        request.setAttribute(ATTRIBUTE_CLIENT_ORDERS, orders);
        LOGGER.info("Client: " + client.getId() + " entered home page");

        return CLIENT_PAGE;
    }

    private List<Order> getOrdersForPage(HttpServletRequest request) {
        int idPage;
        int total = 2;
        int size = 1;

        if (page == null) {
            idPage = 1;
        } else {
            idPage = Integer.parseInt(page) - 1;
            idPage = idPage * total + 1;
        }

        ordersForCount = orderService.getOrdersByClient(client);
        size *= ordersForCount.size();

        if (size % total == 0) {
            request.setAttribute("countOfOrders", size / total);
        } else {
            request.setAttribute("countOfOrders", size / total + 1);
        }

        return orderService.getLimitedOrders(idPage - 1, total);
    }

    private void initCommand(HttpServletRequest request) {
        page = request.getParameter(ATTRIBUTE_PAGE);
        client = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
    }
}
