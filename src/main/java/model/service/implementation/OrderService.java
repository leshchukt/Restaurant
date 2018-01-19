package model.service.implementation;

import model.dao.ConnectionDao;
import model.dao.DaoFactory;
import model.dao.OrderDao;
import model.entity.Menu;
import model.entity.Order;
import model.entity.User;
import model.service.CreateOrderService;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;

public class OrderService implements CreateOrderService{
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

    public List<Order> ordersOfUser(User user){
        return null;
    }

    public List<Menu> getOrderMeals(int orderId){
        return null;
    }

    @Override
    public int createOrder(User client, List<Menu> menu){
        Order order = Order.builder()
                .setClient(client)
                .setMenu(menu)
                .setTimeOfOrder(LocalDateTime.now())
                .setAccepted(false)
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
