package controller.command.admin;

import controller.command.Command;
import controller.command.CommandFactory;
import model.entity.Order;
import model.entity.User;
import model.exception.ConcurrentProcessingException;
import model.service.AdminBillService;
import model.service.implementation.BillService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AcceptOrderCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(AcceptOrderCommand.class);

    private static String ATTRIBUTE_CURRENT_ORDER = "currentOrder";
    private static String ATTRIBUTE_USER = "user";
    private static String ATTRIBUTE_MESSAGE = "message";

    private static String REDIRECT_PAGE = "redirect:" + CommandFactory.ADMIN_HOME;

    private Order order;
    private User admin;

    private AdminBillService billService = BillService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        try {
            billService.acceptOrder(order, admin);
            LOGGER.info("Admin: " + admin.getId() + " accepted order: " + order.getId());
        }
        catch (ConcurrentProcessingException e){
            request.setAttribute(ATTRIBUTE_MESSAGE, "error.concurrency.processed");
        }
        return REDIRECT_PAGE;
    }

    private void initCommand(HttpServletRequest request) {
        order = (Order) request.getSession().getAttribute(ATTRIBUTE_CURRENT_ORDER);
        admin = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
    }
}
