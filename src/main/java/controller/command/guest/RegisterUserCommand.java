package controller.command.guest;

import controller.command.Command;
import controller.command.CommandFactory;
import controller.command.util.PasswordSecurity;
import model.entity.Login;
import model.entity.Role;
import model.entity.User;
import model.exception.EmailAlreadyExistsException;
import model.service.LoginService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RegisterUserCommand implements Command {

    //Parameters and Attributes
    private final String NAME_PARAMETER = "name";
    private final String EMAIL_PARAMETER = "email";
    private final String PASSWORD_PARAMETER = "password";
    private final String PASSWORD_REPEAT_PARAMETER = "repeatedPassword";
    private final String BIRTH_DATE_PARAMETER = "birthDay";
    private final String ERRORS = "registration_errors";
    private final String USER_ATTRIBUTE = "user";

    //Errors
    private final String NAME_ERROR = "error.name";
    private final String EMAIL_ERROR = "errors.email";
    private final String PASSWORD_ERROR = "error.password";
    private final String PASSWORDS_ARE_NOT_EQUAL = "error.val.password";
    private final String WRONG_DATE = "error.date";
    private final String SERVER_ERROR = "errors.not.unique";

    //Regular expressions
    private final String NAME_REGEX = "([A-Z][a-z]{1,13} ?){1,5}";
    private final String EMAIL_REGEX = "[A-Za-z0-9._]+@[a-z]\\.(com|net)";
    private final String PASSWORD_REGEX = ".{6,}";
    private final String DATE_FORMAT = "yyyy-MM-dd";

    private static final Logger LOGGER = Logger.getLogger(RegisterUserCommand.class);

    private LoginService loginService = LoginService.getInstance();

    private final LocalDate MAX_BIRTH_DATE = LocalDate.now().minusYears(18);

    private HttpServletRequest request;
    private String name;
    private String email;
    private String password;
    private String repeatedPassword;
    private LocalDate birthDay;

    private Login login;
    private User user;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        List<String> errors = validate();

        if (!errors.isEmpty()) {
            processInputErrors(errors);
            return REGISTRATION_PAGE;
        }

        buildAccount();

        try {
            loginService.registerUser(login, user);

            LOGGER.info("USER " + user.getId() + "registered");
            request.getSession().setAttribute(USER_ATTRIBUTE, user);

            return "redirect:" + CommandFactory.CLIENT_HOME;
        } catch (EmailAlreadyExistsException e) {
            processExistingEmail();
            return REGISTRATION_PAGE;
        }
    }

    private void processExistingEmail() {
        LOGGER.info("Invalid attempt of registration with used email: " + email);
        List<String> errors = new ArrayList<>();
        errors.add(SERVER_ERROR);
        request.setAttribute(ERRORS, errors);
    }

    private void buildAccount() {
        login = Login.builder()
                .setEmail(email)
                .setPassword(PasswordSecurity.getSecurePassword(password))
                .build();

        user = User.builder()
                .setNickname(name)
                .setBirthDate(birthDay)
                .setRole(Role.CLIENT)
                .build();
    }

    private void processInputErrors(List<String> errors) {
        String toLog = "Invalid attempt of registration.";
        for (String message : errors) {
            toLog += "\n" + message;
        }
        LOGGER.info(toLog);
        request.setAttribute(ERRORS, errors);
    }

    private List<String> validate() {
        List<String> errors = new ArrayList<>();

        if (name == null || !name.matches(NAME_REGEX)) {
            errors.add(NAME_ERROR);
        }

        if (email == null || !email.matches(EMAIL_REGEX)) {
            errors.add(EMAIL_ERROR);
        }

        if (password == null || !password.matches(PASSWORD_REGEX)) {
            errors.add(PASSWORD_ERROR);
        }

        if (repeatedPassword == null || !password.equals(repeatedPassword)) {
            errors.add(PASSWORDS_ARE_NOT_EQUAL);
        }

        if (birthDay == null || birthDay.isAfter(MAX_BIRTH_DATE)) {
            errors.add(WRONG_DATE);
        }

        return errors;
    }

    private void initCommand(HttpServletRequest request) {
        this.request = request;
        name = request.getParameter(NAME_PARAMETER);
        email = request.getParameter(EMAIL_PARAMETER);
        password = request.getParameter(PASSWORD_PARAMETER);
        repeatedPassword = request.getParameter(PASSWORD_REPEAT_PARAMETER);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        String dateString = request.getParameter(BIRTH_DATE_PARAMETER);
        if (dateString == null) {
            birthDay = null;
        } else {
            birthDay = LocalDate.parse(dateString, dtf);
        }
    }
}
