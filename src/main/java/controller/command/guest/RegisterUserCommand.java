package controller.command.guest;

import controller.command.Command;
import controller.command.CommandFactory;
import controller.command.util.PasswordSecurity;
import model.entity.Login;
import model.entity.Role;
import model.entity.User;
import model.exception.EmailAlreadyExistsException;
import model.service.RegisterLoginService;
import model.service.implementation.LoginService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegisterUserCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(RegisterUserCommand.class);

    //Parameters and Attributes
    private final String NICKNAME_PARAMETER = "nickname";
    private final String EMAIL_PARAMETER = "email";
    private final String PASSWORD_PARAMETER = "password";
    private final String PASSWORD_REPEAT_PARAMETER = "repeatedPassword";
    private final String BIRTH_DATE_PARAMETER = "birthDate";
    private final String ERROR = "registrationErrors";
    private final String USER_ATTRIBUTE = "user";

    //Errors
    private String VALIDATION_ERROR = "error.val.password";
    private final String WRONG_DATE = "registration.birth.date.description";
    private final String NOT_UNIQUE_EMAILS_ERROR = "error.not.unique";

    private final String DATE_FORMAT = "yyyy-MM-dd";

    private RegisterLoginService loginService = LoginService.getInstance();

    private final LocalDate MAX_DATE = LocalDate.now().minusYears(18);

    private HttpServletRequest request;
    private String nickname;
    private String email;
    private String password;
    private String repeatedPassword;
    private LocalDate birthDate;

    private Login login;
    private User user;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);

        if (!isPasswordMatch()) {
            return processError(VALIDATION_ERROR);
        }

        if (!isAdult()) {
            return processError(WRONG_DATE);
        }

        buildAccount();

        try {
            loginService.registerUser(login, user);

            LOGGER.info("USER " + login.getId() + " registered");
            request.getSession().setAttribute(USER_ATTRIBUTE, user);

            return "redirect:" + CommandFactory.CLIENT_HOME;
        } catch (EmailAlreadyExistsException e) {
            return processError(NOT_UNIQUE_EMAILS_ERROR);
        }
    }

    private String processError(String errorAttribute) {
        LOGGER.info("Invalid attempt of registration because of " + errorAttribute);
        request.setAttribute(ERROR, errorAttribute);

        return REGISTRATION_PAGE;
    }

    private boolean isPasswordMatch() {
        return password.equals(repeatedPassword);
    }

    private boolean isAdult() {
        return birthDate.isBefore(MAX_DATE);
    }

    private void buildAccount() {
        login = Login.builder()
                .setEmail(email)
                .setPassword(PasswordSecurity.getSecurePassword(password))
                .build();

        user = User.builder()
                .setNickname(nickname)
                .setBirthDate(birthDate)
                .setRole(Role.CLIENT)
                .build();
    }

    private void initCommand(HttpServletRequest request) {
        this.request = request;
        nickname = request.getParameter(NICKNAME_PARAMETER);
        email = request.getParameter(EMAIL_PARAMETER);
        password = request.getParameter(PASSWORD_PARAMETER);
        repeatedPassword = request.getParameter(PASSWORD_REPEAT_PARAMETER);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        String dateString = request.getParameter(BIRTH_DATE_PARAMETER);
        birthDate = LocalDate.parse(dateString, dtf);
    }
}
