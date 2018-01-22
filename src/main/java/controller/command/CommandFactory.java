package controller.command;

import com.sun.org.apache.bcel.internal.generic.RET;
import controller.command.admin.*;
import controller.command.client.*;
import controller.command.guest.*;
import org.apache.log4j.Logger;

public class CommandFactory {

    public static final String LOGIN_PAGE = "/restaurant/login_page";
    public static final String REGISTRATION = "/restaurant/registration";
    public static final String REGISTER_USER = "/restaurant/registration/register";
    public static final String LOGIN = "/restaurant/login";
    public static final String SET_LOCALE = "/restaurant/change_locale";
    public static final String LOGOUT = "/restaurant/logout";


    public static final String MENU_SEARCH = "/restaurant/client/menu";
    public static final String CLIENT_HOME = "/restaurant/client/home";
    public static final String CLIENT_HOME_PAGINATION = "/restaurant/client/home/";
    public static final String GET_ORDER_MEALS = "/restaurant/client/show_order";
    public static final String ADD_MEAL_TO_ORDER = "/restaurant/client/add_meal";
    public static final String REMOVE_MEAL_FROM_ORDER = "/restaurant/client/remove_meal";
    public static final String CREATE_ORDER = "/restaurant/client/create_order";
    public static final String CLIENT_ORDER = "/restaurant/client/order";
    public static final String CLIENT_DECLINE_ORDER = "/restaurant/client/decline_order";
    public static final String CLIENT_BILLS = "/restaurant/client/bills";
    public static final String CLIENT_BILLS_PAGINATION = "/restaurant/client/bills/";
    public static final String PAY_BILL = "/restaurant/client/pay";


    public static final String ADMIN_HOME = "/restaurant/admin/home";
    public static final String GO_TO_ORDER = "/restaurant/admin/order";
    public static final String ADMIN_DECLINE_ORDER = "/restaurant/admin/decline_order";
    public static final String ADMIN_ACCEPT_ORDER = "/restaurant/admin/accept_order";

    private static final Logger LOGGER = Logger.getLogger(CommandFactory.class);

    public static Command create(String uri){

        if(uri == null){
            uri = LOGIN_PAGE;
        }

        LOGGER.info("Creating command for uri: " + uri);

        switch (uri){
            case SET_LOCALE :
                return new SetLocaleCommand();
            case REGISTRATION :
                return new RegistrationCommand();
            case LOGIN :
                return new LoginCommand();
            case LOGIN_PAGE :
                return new LoginPageCommand();
            case CLIENT_HOME:
                return new HomeCommand();
            case CLIENT_HOME_PAGINATION:
                return new HomeCommand();
            case MENU_SEARCH:
                return new SearchMenuCommand();
            case REGISTER_USER:
                return new RegisterUserCommand();
            case GET_ORDER_MEALS:
                return new OrderMealCommand();
            case ADD_MEAL_TO_ORDER:
                return new AddMealToOrderCommand();
            case CLIENT_DECLINE_ORDER:
                return new DeclineOrderCommand();
            case REMOVE_MEAL_FROM_ORDER:
                return new RemoveMealCommand();
            case CLIENT_ORDER:
                return new OrderPageCommand();
            case CREATE_ORDER:
                return new CreateOrderCommand();
            case CLIENT_BILLS:
                return new GetBillsCommand();
            case CLIENT_BILLS_PAGINATION:
                return new GetBillsCommand();
            case PAY_BILL:
                return new PayTheBillCommand();
            case ADMIN_HOME:
                return new HomeAdminCommand();
            case GO_TO_ORDER:
                return new FindOrderCommand();
            case ADMIN_ACCEPT_ORDER:
                return new AcceptOrderCommand();
            case ADMIN_DECLINE_ORDER:
                return new DeclineOrderAdminCommand();
            case LOGOUT:
                return new LogoutCommand();
            default:
                return new UnknownCommand();
        }

    }
}
