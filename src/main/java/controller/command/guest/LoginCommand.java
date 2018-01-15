package controller.command.guest;

import controller.command.Command;
import controller.command.CommandFactory;
import controller.command.util.PasswordSecurity;
import model.entity.Role;
import model.entity.User;
import model.service.LoginService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final String EMAIL_REGEX = "[A-Za-z0-9._]+@[a-z]\\.(com|net)";

    private static final String EMAIL_PARAMETER = "email";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String USER_ATTRIBUTE = "user";
    private static final String ERROR_ATTRIBUTE = "errorMessage";
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    private LoginService loginService = LoginService.getInstance();

    private String email;
    private String password;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);

        if (!isEmailCorrect(email)) {
            request.setAttribute(ERROR_ATTRIBUTE, "errors.email");
            return LOG_IN_PAGE;
        }

        Optional<User> user = loginService.getUser(email, password);
        if(user.isPresent()){
            LOGGER.info("User " + user.get().getId() + " logged in.");
            request.getSession().setAttribute(USER_ATTRIBUTE, user.get());
            if(user.get().getRole().equals(Role.ADMIN)){
                return "redirect:" + CommandFactory.ADMIN_HOME;
            }
            else{
                return "redirect:" + CommandFactory.CLIENT_HOME;
            }
        }
        else{
            LOGGER.info("Invalid attempt to log in.");
            request.setAttribute(ERROR_ATTRIBUTE, "errors.login");
            return LOG_IN_PAGE;
        }
    }

    private boolean isEmailCorrect(String email) {
        return email.matches(EMAIL_REGEX);
    }

    private void initCommand(HttpServletRequest request) {
        email = request.getParameter(EMAIL_PARAMETER);
        password = PasswordSecurity.getSecurePassword(request.getParameter(PASSWORD_PARAMETER));
    }
}
