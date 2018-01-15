package model.service;

import model.dao.DaoFactory;
import model.entity.Menu;
import model.entity.Order;
import model.entity.User;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderService {
    private DaoFactory factory;
    private static final Logger LOGGER = Logger.getLogger(OrderService.class);

    private OrderService(){
        factory = DaoFactory.getInstance();
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


    public int createOrder(User client, List<Menu> meals){
        return 0;
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
