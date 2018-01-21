package controller.filter;

import model.entity.Role;
import model.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import static controller.command.CommandFactory.*;

@WebFilter(urlPatterns = {"/restaurant/*"})
public class AuthorizationFilter implements Filter {
    private Set<String> guestURI = new HashSet<>();
    private Set<String> clientURI = new HashSet<>();
    private Set<String> adminURI = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        // guest URIs
        guestURI.add(LOGIN_PAGE);
        guestURI.add(LOGIN);
        guestURI.add(REGISTRATION);
        guestURI.add(REGISTER_USER);
        guestURI.add(SET_LOCALE);

        // subscriber URIs
        clientURI.add(GET_ORDER_MEALS);
        clientURI.add(CLIENT_HOME);
        clientURI.add(CLIENT_HOME_PAGINATION);
        clientURI.add(MENU_SEARCH);
        clientURI.add(ADD_MEAL_TO_ORDER);
        clientURI.add(REMOVE_MEAL_FROM_ORDER);
        clientURI.add(CREATE_ORDER);
        clientURI.add(CLIENT_ORDER);
        clientURI.add(CLIENT_BILLS);
        clientURI.add(CLIENT_DECLINE_ORDER);
        clientURI.add(PAY_BILL);
        clientURI.add(LOGOUT);


        // admin URIs
        adminURI.add(ADMIN_HOME);
        adminURI.add(GO_TO_ORDER);
        adminURI.add(ADMIN_ACCEPT_ORDER);
        adminURI.add(ADMIN_DECLINE_ORDER);
        adminURI.add(LOGOUT);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        User user = (User) request.getSession().getAttribute("user");

        if (guestURI.contains(uri)) {
            user = null;
        }

        boolean isGuestAccess
                = (user == null)
                && guestURI.contains(uri);

        boolean isSubscriberAccess
                = (user != null)
                && clientURI.contains(uri)
                && "CLIENT".equals(user.getRole().toString());

        boolean isAdminAccess
                = (user != null)
                && adminURI.contains(uri)
                && "ADMIN".equals(user.getRole().toString());

        boolean isLoggedIn
                = (user != null)
                && guestURI.contains(uri);

        boolean needToSignIn
                = (user == null)
                && (clientURI.contains(uri) || adminURI.contains(uri));

        if (isGuestAccess || isSubscriberAccess || isAdminAccess) {
            request.setAttribute("uri", uri);
        }

        if(isLoggedIn){
            if(user.getRole() == Role.CLIENT){
                request.setAttribute("uri", CLIENT_HOME);
            }
            else {
                request.setAttribute("uri", ADMIN_HOME);
            }
        }

        if(needToSignIn){
            request.setAttribute("uri", LOGIN_PAGE);
        }
        filterChain.doFilter(request, response);

    }

    @Override
    public void destroy() {
        guestURI = null;
        clientURI = null;
        adminURI = null;
    }
}
