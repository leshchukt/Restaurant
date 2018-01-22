package controller.command.admin;

import model.entity.Bill;
import model.entity.Order;
import model.entity.User;
import model.service.implementation.BillService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class AcceptOrderCommandTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private BillService service;

    @InjectMocks
    private AcceptOrderCommand command;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void execute() throws Exception {
        Order order = Order.builder().build();
        User user = User.builder().build();
        Bill bill = Bill.builder().build();

        when(request.getSession()).thenReturn(session);

        when(session.getAttribute("currentOrder")).thenReturn(order);
        when(session.getAttribute("user")).thenReturn(user);
        when(service.acceptOrder(order, user)).thenReturn(bill);

        String redirect = command.execute(request, response);

        verify(session, times(1)).getAttribute("currentOrder");
        verify(session, times(1)).getAttribute("user");
        verify(service, times(1)).acceptOrder(order, user);

        assertEquals("redirect:admin.home.page", redirect);
    }

}