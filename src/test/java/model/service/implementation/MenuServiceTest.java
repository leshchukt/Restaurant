package model.service.implementation;

import model.dao.ConnectionDao;
import model.dao.DaoFactory;
import model.dao.MenuDao;
import model.entity.Menu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MenuServiceTest {
    @Mock
    DaoFactory factory;

    @Mock
    ConnectionDao connectionDao;

    @Mock
    MenuDao menuDao;

    @InjectMocks
    MenuService service;

    @Test
    public void getMenuItemWithAmount() throws Exception {
        Menu menu = Menu.builder()
                .setTitle("test")
                .build();

        Menu expectedMenu = Menu.builder()
                .setTitle("test")
                .setAmount(5)
                .build();

        when(factory.getConnectionDao()).thenReturn(connectionDao);
        when(factory.createMenuDao(any())).thenReturn(menuDao);
        when(menuDao.findById(anyInt())).thenReturn(Optional.of(menu));

        service.getMenuItemWithAmount(1, 5);

        verify(factory).getConnectionDao();
        verify(factory).createMenuDao(any());
        verify(menuDao).findById(1);


        assertEquals(expectedMenu, menu);
    }

    @Test
    public void getInstance() throws Exception {
    }

    @Test
    public void addMealToList() throws Exception {
    }

    @Test
    public void removeMealFromList() throws Exception {
    }

    @Test
    public void getMenuByCategory() throws Exception {
    }

}