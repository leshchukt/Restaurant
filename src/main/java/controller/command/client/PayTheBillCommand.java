package controller.command.client;

import controller.command.Command;
import controller.command.CommandFactory;
import model.entity.Bill;
import model.entity.User;
import model.exception.ConcurrentProcessingException;
import model.service.PayService;
import model.service.implementation.BillService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PayTheBillCommand implements Command{
    private static final String PARAMETER_BILL = "bill.id";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String ATTRIBUTE_USER = "user";

    private static final String REDIRECT_PAGE = "redirect:" + CommandFactory.CLIENT_BILLS;

    private static final Logger LOGGER = Logger.getLogger(PayTheBillCommand.class);

    private User client;
    private int idBill;

    private PayService billService = BillService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        try {
            billService.payTheBill(idBill);

            LOGGER.info("Client:" + client.getId() + " paid the bill: " + idBill);
        } catch (ConcurrentProcessingException e) {
            request.setAttribute(ATTRIBUTE_MESSAGE, "error.concurrency.check");
        }
        return REDIRECT_PAGE;
    }

    private void initCommand(HttpServletRequest request) {
        idBill = Integer.parseInt(request.getParameter(PARAMETER_BILL));
        client = (User)request.getSession().getAttribute(ATTRIBUTE_USER);
    }
}
