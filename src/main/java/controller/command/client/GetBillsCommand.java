package controller.command.client;

import controller.command.Command;
import model.entity.Bill;
import model.entity.User;
import model.service.implementation.BillService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetBillsCommand implements Command {
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_BILLS = "userBills";

    private static final Logger LOGGER = Logger.getLogger(GetBillsCommand.class);

    //todo change classes to interfaces
    private BillService service = BillService.getInstance();

    private User client;
    private List<Bill> bills;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);

        bills = service.getBills(client);

        request.setAttribute(ATTRIBUTE_BILLS, bills);
        LOGGER.info("User " + client.getId() + " got his bills.");

        return CLIENT_BILLS_PAGE;
    }

    private void initCommand(HttpServletRequest request) {
        client = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
    }
}
