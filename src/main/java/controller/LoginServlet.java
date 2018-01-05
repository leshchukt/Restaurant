package controller;

import model.entity.Menu;
import model.service.MenuService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("login", login);
        httpSession.setAttribute("password", password);

        PrintWriter out = response.getWriter();
        out.println(login);
        out.println(password);
        out.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        MenuService menuService = new MenuService();
        List<Menu> menu = menuService.getAllMenu();
        request.setAttribute("menu", menu);
        request.getRequestDispatcher("./WEB-INF/view/menu.jsp").forward(request, response);
    }
}
