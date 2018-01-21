package controller.command.admin;

import controller.command.Command;
import controller.command.CommandFactory;
import model.entity.Order;
import model.entity.User;
import model.exception.ConcurrentProcessingException;
import model.service.DeclineBillService;
import model.service.implementation.BillService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeclineOrderAdminCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(DeclineOrderAdminCommand.class);

    private static final String REDIRECT_PAGE = "redirect:" + CommandFactory.ADMIN_HOME;

    private static final String ATTRIBUTE_CURRENT_ORDER = "currentOrder";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_MESSAGE = "message";

    private Order order;
    private User admin;

    private DeclineBillService billService = BillService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        try {
            billService.declineOrder(order.getId());
            LOGGER.info("Admin: " + admin.getId() + " declined order: " + order.getId());
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
