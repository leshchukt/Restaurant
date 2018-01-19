package controller.command.client;

import controller.command.Command;
import controller.command.CommandFactory;
import model.entity.User;
import model.exception.ConcurrentProcessingException;
import model.service.implementation.BillService;
import model.service.implementation.OrderService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeclineOrderCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(DeclineOrderCommand.class);

    private int orderId;
    private User client;

    //todo change classes to interfaces
    private OrderService orderService = OrderService.getInstance();
    private BillService billService = BillService.getInstance();

    private static String PARAMETER_ORDER = "order.id";
    private static String ATTRIBUTE_USER = "user";
    private static String ATTRIBUTE_MESSAGE = "message";

    private static String REDIRECT_PAGE = "redirect:" + CommandFactory.CLIENT_HOME;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        if (!orderService.checkClientRightsOnOrder(orderId, client)) {
            LOGGER.error("Client attempt to decline someones order.");
            throw new RuntimeException();
        }
        try {
            LOGGER.info("Client id: " + client.getId() + " declined order: " + orderId);
            billService.declineOrder(orderId);
        } catch (ConcurrentProcessingException e) {
            request.setAttribute(ATTRIBUTE_MESSAGE, "error.concurrency.processed");
        }
        return REDIRECT_PAGE;
    }

    private void initCommand(HttpServletRequest request) {
        orderId = Integer.parseInt(request.getParameter(PARAMETER_ORDER));
        client = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
    }
}
