package controller.command.guest;

import controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

public class LogoutCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Enumeration<String> attributes = request.getSession().getAttributeNames();
        for(; attributes.hasMoreElements(); ){
            String attribute = attributes.nextElement();
            if(!attribute.equals("locale")){
                request.getSession().setAttribute(attribute, null);
            }
        }
        return LOG_IN_PAGE;
    }
}
