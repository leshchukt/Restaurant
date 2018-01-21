package model.service.implementation;

import model.dao.*;
import model.entity.Menu;
import model.entity.Order;
import model.entity.User;
import model.service.*;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderService implements ClientOrderService, AdminOrderService {
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

    @Override
    public int getSummaryPrice(Order order){
        if(order.getMenu() == null){
            order.setMenu(getOrderMenu(order.getId()));
        }
        int result = 0;
        for(Menu menuItem : order.getMenu()){
            result += menuItem.getPrice() * menuItem.getAmount();
        }
        return result;
    }

    @Override
    public Order getFullInfoAboutOrder(int idOrder) {
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()){
            OrderDao orderDao = daoFactory.createOrderDao(connectionDao);
            Order result = orderDao.findById(idOrder).get();
            UserDao userDao = daoFactory.createUserDao(connectionDao);
            User client = userDao.findById(result.getIdClient()).get();
            result.setClient(client);
            result.setMenu(getOrderMenu(result.getId()));
            return result;
        }
    }

    @Override
    public List<Order> getActiveOrders() {
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()){
            OrderDao orderDao = daoFactory.createOrderDao(connectionDao);
            List<Order> result = orderDao.findAll().stream()
                    .filter(order -> order.getAccepted()==0)
                    .collect(Collectors.toList());
            return result;
        }
    }
}
