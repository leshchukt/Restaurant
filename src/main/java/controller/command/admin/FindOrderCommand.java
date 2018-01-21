package controller.command.admin;

import controller.command.Command;
import model.entity.Order;
import model.service.AdminOrderService;
import model.service.implementation.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FindOrderCommand implements Command {
    private static String PARAMETER_ORDER = "order.id";
    private static String ATTRIBUTE_CURRENT_ORDER = "currentOrder";

    private AdminOrderService orderService = OrderService.getInstance();

    private int idOrder;
    private Order order;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        order = orderService.getFullInfoAboutOrder(idOrder);
        request.getSession().setAttribute(ATTRIBUTE_CURRENT_ORDER, order);
        return ADMIN_ORDER_PAGE;
    }

    private void initCommand(HttpServletRequest request) {
        idOrder = Integer.parseInt(request.getParameter(PARAMETER_ORDER));
    }
}
