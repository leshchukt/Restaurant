package controller.command.admin;

import controller.command.Command;
import model.entity.Order;
import model.service.ActiveOrdersService;
import model.service.implementation.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HomeAdminCommand implements Command {
    private static String ATTRIBUTE_ACTIVE_ORDERS = "activeOrders";

    private List<Order> activeOrders;

    private ActiveOrdersService orderService = OrderService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        activeOrders = orderService.getActiveOrders();
        request.setAttribute(ATTRIBUTE_ACTIVE_ORDERS, activeOrders);
        return ADMIN_PAGE;
    }
}
