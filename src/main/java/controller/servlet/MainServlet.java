package controller.servlet;

import controller.command.Command;
import controller.command.CommandFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "main", value = "/restaurant/*")
public class MainServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processQuery(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processQuery(request, response);
    }

    private void processQuery(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String query = (String) request.getAttribute("uri");
        Command command = CommandFactory.create(query);
        String page = command.execute(request, response);
        if (page.startsWith("redirect:")) {
            request.setAttribute("uri", page.replace("redirect:", ""));
            processQuery(request, response);
        } else {
            getServletContext().getRequestDispatcher(page).forward(request, response);
        }
    }
}
