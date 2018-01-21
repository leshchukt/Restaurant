package controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    String LOG_IN_PAGE = "/view/guest/login.jsp";
    String REGISTRATION_PAGE = "/view/guest/registration.jsp";
    String ADMIN_PAGE = "/view/admin/home.jsp";
    String CLIENT_PAGE = "/view/client/home.jsp";
    String ERROR_404 = "/view/error/error404.jsp";
    String CLIENT_MENU_PAGE = "/view/client/menu.jsp";
    String CLIENT_ORDER_PAGE = "/view/client/order.jsp";
    String CLIENT_BILLS_PAGE = "/view/client/bills.jsp";
    String ADMIN_ORDER_PAGE = "/view/admin/order.jsp";

    String execute(HttpServletRequest request, HttpServletResponse response);
}
