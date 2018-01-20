package model.service.implementation;

import model.dao.*;
import model.entity.Category;
import model.entity.Menu;
import model.entity.Order;
import model.entity.User;
import model.service.CreateOrderService;
import model.service.GetOrdersService;
import model.service.OrderHasMenuService;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OrderService implements CreateOrderService, GetOrdersService, OrderHasMenuService {
    private static final Logger LOGGER = Logger.getLogger(OrderService.class);

    private DaoFactory daoFactory;

    private OrderService(){
        daoFactory = DaoFactory.getInstance();
    }

    private static class Holder{
        private static OrderService INSTANCE = new OrderService();
    }

    public static OrderService getInstance(){
        return Holder.INSTANCE;
    }

    @Override
    public List<Order> getOrdersByClient(User client){
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()){
            OrderDao orderDao = daoFactory.createOrderDao(connectionDao);
            return orderDao.findByClient(client);
        }
    }

    @Override
    public List<Menu> getOrderMenu(int idOrder){
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()){
            OrderDao orderDao = daoFactory.createOrderDao(connectionDao);
            Optional<Order> order = orderDao.findById(idOrder);
            if(order.isPresent()){
                MenuDao menuDao = daoFactory.createMenuDao(connectionDao);

                return menuDao.getMenuFromOrder(order.get());
            }
            else{
                LOGGER.error(NO_ID_EXCEPTION_MESSAGE);
                throw new RuntimeException();
            }
        }
    }


    @Override
    public int createOrder(User client, List<Menu> menu){
        double orderPrice = 0.;
        for (Menu menuItem : menu) {
            orderPrice += menuItem.getPrice() * menuItem.getAmount();
        }
        Order order = Order.builder()
                .setClient(client)
                .setMenu(menu)
                .setTimeOfOrder(LocalDateTime.now())
                .setAccepted(0)
                .setPrice(orderPrice)
                .build();

        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()){
            OrderDao orderDao = daoFactory.createOrderDao(connectionDao);
            orderDao.create(order);

            return order.getId();
        }

    }

    public int getSummaryPrice(Order order){
        return 0;
    }

    public boolean checkClientRightsOnOrder(int orderId, User client) {
        return false;
    }

    public Order getFullInfoAboutOrder(int orderId) {
        return null;
    }

    public List<Order> getActiveOrders() {
        return null;
    }
}
