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

@WebFilter(urlPatterns = {"/restaurant/*"})
public class AuthorizationFilter implements Filter {
    private Set<String> guestQueries = new HashSet<>();
    private Set<String> clientQueries = new HashSet<>();
    private Set<String> adminQueries = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    /*
        // guest queries
        guestQueries.add(LOGIN_PAGE);
        guestQueries.add(LOGIN);
        guestQueries.add(REGISTRATION);
        guestQueries.add(REGISTER_USER);
        guestQueries.add(SET_LOCALE);

        // subscriber queries
        clientQueries.add(GET_ORDER_MEALS);
        clientQueries.add(CLIENT_HOME);
        clientQueries.add(MEALS_SEARCH);
        clientQueries.add(ADD_MEAL_TO_ORDER);
        clientQueries.add(REMOVE_MEAL_FROM_ORDER);
        clientQueries.add(CREATE_ORDER);
        clientQueries.add(CLIENT_ORDER);
        clientQueries.add(CLIENT_CHECKS);
        clientQueries.add(CLIENT_DECLINE_ORDER);
        clientQueries.add(PAY_CHECK);
        clientQueries.add(EXIT);


        // admin queries
        adminQueries.add(ADMIN_HOME);
        adminQueries.add(GO_TO_ORDER);
        adminQueries.add(ADMIN_ACCEPT_ORDER);
        adminQueries.add(ADMIN_DECLINE_ORDER);
        adminQueries.add(EXIT);
    */
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    /*
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        User user = (User) request.getSession().getAttribute("user");

        boolean isGuestAccess
                = (user == null)
                && guestQueries.contains(uri);

        boolean isSubscriberAccess
                = (user != null)
                && clientQueries.contains(uri)
                && "CLIENT".equals(user.getRole().toString());

        boolean isAdminAccess
                = (user != null)
                && adminQueries.contains(uri)
                && "ADMIN".equals(user.getRole().toString());

        boolean isLoggedIn
                = (user != null)
                && guestQueries.contains(uri);

        boolean needToSignIn
                = (user == null)
                && (clientQueries.contains(uri) || adminQueries.contains(uri));

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
    */
    }

    @Override
    public void destroy() {
        guestQueries = null;
        clientQueries = null;
        adminQueries = null;
    }
}
