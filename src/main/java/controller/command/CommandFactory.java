package controller.command;

import controller.command.admin.*;
import controller.command.client.*;
import controller.command.guest.*;
import org.apache.log4j.Logger;

public class CommandFactory {

    static final String LOGIN_PAGE = "/restaurant/login_page";
    static final String REGISTRATION = "/restaurant/registration";
    static final String REGISTER_USER = "/restaurant/registration/register";
    static final String LOGIN = "/restaurant/login";
    static final String SET_LOCALE = "/restaurant/change_locale";
    static final String EXIT = "/restaurant/exit";


    public static final String MENU_SEARCH = "/restaurant/client/menu";
    public static final String CLIENT_HOME = "/restaurant/client/home";
    static final String GET_ORDER_MEALS = "/restaurant/client/show_order";
    static final String ADD_MEAL_TO_ORDER = "/restaurant/client/add_meal";
    static final String REMOVE_MEAL_FROM_ORDER = "/restaurant/client/remove_meal";
    static final String CREATE_ORDER = "/restaurant/client/create_order";
    static final String CLIENT_ORDER = "/restaurant/client/order";
    static final String CLIENT_DECLINE_ORDER = "/restaurant/client/decline_order";
    public static final String CLIENT_BILLS = "/restaurant/client/bills";
    static final String PAY_CHECK = "/restaurant/client/pay";


    public static final String ADMIN_HOME = "/restaurant/admin/home";
    static final String GO_TO_ORDER = "/restaurant/admin/order";
    static final String ADMIN_DECLINE_ORDER = "/restaurant/admin/decline_order";
    static final String ADMIN_ACCEPT_ORDER = "/restaurant/admin/accept_order";

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
            case MENU_SEARCH:
                return new SearchMenuCommand();
            case REGISTER_USER:
                return new RegisterUserCommand();
            case GET_ORDER_MEALS:
                return new OrderMealCommand();
            case ADD_MEAL_TO_ORDER:
                return new AddMenuCommand();
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
            case PAY_CHECK:
                return new PayTheBillCommand();
            case ADMIN_HOME:
                return new HomeAdminCommand();
            case GO_TO_ORDER:
                return new FindOrderCommand();
            case ADMIN_ACCEPT_ORDER:
                return new AcceptOrderCommand();
            case ADMIN_DECLINE_ORDER:
                return new DeclineOrderAdminCommand();
            case EXIT:
                return new ExitCommand();
            default:
                return new UnknownCommand();
        }

    }
}
