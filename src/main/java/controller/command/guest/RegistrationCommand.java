package controller.command.guest;

import controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return REGISTRATION_PAGE;
    }
}
