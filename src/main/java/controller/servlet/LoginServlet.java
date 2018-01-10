package controller.servlet;

import model.entity.User;
import model.service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static String EMAIL_REGEX = "[A-Za-z0-9._]+@[a-z]\\.(com|net)";

    LoginService loginService = new LoginService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (!isEmailCorrect(email)) {
            //error
        }

        Optional<User> user = loginService.getUser(email, password);
        if (user.isPresent()) {
            request.getSession().setAttribute("user", user.get());
            if (isAdmin(user.get())) {
                request.getRequestDispatcher("./view/admin/menu.jsp").forward(request, response);
            } else {
                response.sendRedirect("./view/client/menu.jsp");
            }
        } else {

            response.sendRedirect("/login.jsp");
        }

    }

    private boolean isAdmin(User user) {
        return String.valueOf(user.getRole()).equals("ADMIN");
    }

    private boolean isEmailCorrect(String email) {
        return email.matches(EMAIL_REGEX);
    }

    /*
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
    */
}
