package controller.command.client;

import controller.command.Command;
import model.entity.Bill;
import model.entity.User;
import model.service.ClientBillService;
import model.service.implementation.BillService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetBillsCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GetBillsCommand.class);

    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_BILLS = "userBills";
    private static final String ATTRIBUTE_COUNT_OF_BILLS = "countOfBills";
    private static String PARAMETER_PAGE = "page";

    private ClientBillService billService = BillService.getInstance();

    private User client;
    private List<Bill> billsForCount;

    String page;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);

        List<Bill> bills = getBillsForPage(request);

        request.setAttribute(ATTRIBUTE_BILLS, bills);
        LOGGER.info("User " + client.getId() + " got his bills.");

        return CLIENT_BILLS_PAGE;
    }

    private List<Bill> getBillsForPage(HttpServletRequest request) {
        int idPage;
        int total = 2;
        int size = 1;

        if (page == null) {
            idPage = 1;
        } else {
            idPage = Integer.parseInt(page) - 1;
            idPage = idPage * total + 1;
        }

        billsForCount = billService.getBillsByClient(client);
        size *= billsForCount.size();

        if (size % total == 0) {
            request.setAttribute(ATTRIBUTE_COUNT_OF_BILLS, size / total);
        } else {
            request.setAttribute(ATTRIBUTE_COUNT_OF_BILLS, size / total + 1);
        }

        return billService.getLimitedBills(client, idPage - 1, total);
    }

    private void initCommand(HttpServletRequest request) {
        page = request.getParameter(PARAMETER_PAGE);
        client = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
    }
}
