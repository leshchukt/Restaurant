package controller.servlet;

import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LogoutServletTest {

    private static String PATH = "/login.jsp";
    @Test
    public void doGet() throws Exception {
/*
        final LogoutServlet logoutServlet = new LogoutServlet();
        final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        final HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        final HttpSession httpSession = mock(HttpSession.class);
        final RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        when(httpServletRequest.getSession()).thenReturn(httpSession);
        when(httpServletRequest.getRequestDispatcher(PATH)).thenReturn(requestDispatcher);

        logoutServlet.doGet(httpServletRequest, httpServletResponse);

        verify(httpServletRequest, times(1)).getSession();
        verify(httpServletRequest, times(1)).getRequestDispatcher(PATH);
        verify(requestDispatcher).forward(httpServletRequest, httpServletResponse);
  */
    }

}