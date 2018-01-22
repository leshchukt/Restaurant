package controller.command.guest;

import controller.command.util.PasswordSecurity;
import model.entity.Login;
import model.entity.Role;
import model.entity.User;
import model.exception.EmailAlreadyExistsException;
import model.service.implementation.LoginService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegisterUserCommandTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private LoginService service;

    @InjectMocks
    private RegisterUserCommand command;

    @Test
    public void execute() throws EmailAlreadyExistsException {
        Login login = Login.builder()
                .setEmail("test@test.com")
                .setPassword(PasswordSecurity.getSecurePassword("testtesttest"))
                .build();

        User user = User.builder()
                .setNickname("Test Testing")
                .setBirthDate(LocalDate.parse("1970-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setRole(Role.CLIENT)
                .build();

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("nickname")).thenReturn("Test Testing");
        when(request.getParameter("email")).thenReturn("test@test.com");
        when(request.getParameter("password")).thenReturn("testtesttest");
        when(request.getParameter("repeatedPass")).thenReturn("testtesttest");
        when(request.getParameter("birthDay")).thenReturn("1970-01-01");
        doNothing().when(service).registerUser(eq(login), eq(user));

        String path = command.execute(request, response);

        verify(request).getParameter("nickname");
        verify(request).getParameter("email");
        verify(request).getParameter("password");
        verify(request).getParameter("repeatedPass");
        verify(request).getParameter("birthDay");
        verify(service).registerUser(eq(login), eq(user));
        verify(session).setAttribute(eq("user"), eq(user));

        assertEquals("/view/client/home.jsp", path);
    }

}