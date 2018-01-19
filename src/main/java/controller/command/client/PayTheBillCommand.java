package controller.command.client;

import controller.command.Command;
import controller.command.CommandFactory;
import model.entity.User;
import model.exception.ConcurrentProcessingException;
import model.service.implementation.BillService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PayTheBillCommand implements Command{
    private static final String PARAMETER_BILL = "check.id";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String ATTRIBUTE_USER = "user";

    private static final String REDIRECT_PAGE = "redirect:" + CommandFactory.CLIENT_BILLS;

    private static final Logger LOGGER = Logger.getLogger(PayTheBillCommand.class);

    private User client;
    private int billId;

    //todo change classes to interfaces
    private BillService billService = BillService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        try {
            billService.payTheBill(billId);

            LOGGER.info("Client:" + client.getId() + " paid the bill: " + billId);
        } catch (ConcurrentProcessingException e) {
            request.setAttribute(ATTRIBUTE_MESSAGE, "error.concurrency.check");
        }
        return REDIRECT_PAGE;
    }

    private void initCommand(HttpServletRequest request) {
        billId = Integer.parseInt(request.getParameter(PARAMETER_BILL));
        client = (User)request.getSession().getAttribute(ATTRIBUTE_USER);
    }
}
