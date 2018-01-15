package controller.command.client;

import controller.command.Command;
import model.entity.Bill;
import model.entity.User;
import model.service.BillService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetBillsCommand implements Command {
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_CHECKS = "userChecks";

    private static final Logger LOGGER = Logger.getLogger(GetBillsCommand.class);



    private User client;
    private List<Bill> bills;
    private BillService service = BillService.getInstance();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);

        bills = service.getBills(client);

        request.setAttribute(ATTRIBUTE_CHECKS, bills);
        LOGGER.info("User " + client.getId() + " got his bills.");

        return CLIENT_CHECKS_PAGE;
    }

    private void initCommand(HttpServletRequest request) {
        client = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
    }
}
