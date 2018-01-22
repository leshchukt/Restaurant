package model.service.implementation;

import model.dao.*;
import model.entity.Menu;
import model.entity.Order;
import model.entity.User;
import model.service.AdminOrderService;
import model.service.ClientOrderService;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service executes operations with Orders via dao implementation
 */
public class OrderService implements ClientOrderService, AdminOrderService {
    private static final Logger LOGGER = Logger.getLogger(OrderService.class);

    private DaoFactory daoFactory;

    private OrderService() {
        daoFactory = DaoFactory.getInstance();
    }

    public static OrderService getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Finds orders of particular client
     *
     * @param client
     * @return orders list
     */
    @Override
    public List<Order> getOrdersByClient(User client) {
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()) {
            OrderDao orderDao = daoFactory.createOrderDao(connectionDao);
            return orderDao.findByClient(client);
        }
    }

    /**
     * Finds order of particular client for page
     *
     * @param client
     * @param start  index of first order on the page
     * @param total  amount of orders on the page
     * @return orders list
     */
    @Override
    public List<Order> getLimitedOrders(User client, int start, int total) {
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()) {
            OrderDao orderDao = daoFactory.createOrderDao(connectionDao);
            return orderDao.getWithLimit(client, start, total);
        }
    }

    /**
     * Gets menu list of particular order
     *
     * @param idOrder
     * @return menu list
     */
    @Override
    public List<Menu> getOrderMenu(int idOrder) {
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()) {
            OrderDao orderDao = daoFactory.createOrderDao(connectionDao);
            Optional<Order> order = orderDao.findById(idOrder);
            if (order.isPresent()) {
                MenuDao menuDao = daoFactory.createMenuDao(connectionDao);

                return menuDao.getMenuFromOrder(order.get());
            } else {
                LOGGER.error(NO_ID_EXCEPTION_MESSAGE);
                throw new RuntimeException();
            }
        }
    }

    /**
     * Inserts new order
     *
     * @param client who makes an order
     * @param menu   in order
     * @return id of order
     */
    @Override
    public int createOrder(User client, List<Menu> menu) {
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

        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()) {
            OrderDao orderDao = daoFactory.createOrderDao(connectionDao);
            orderDao.create(order);

            return order.getId();
        }
    }

    /**
     * Gets all information about particular order
     *
     * @param idOrder
     * @return entity order with all info
     */
    @Override
    public Order getFullInfoAboutOrder(int idOrder) {
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()) {
            OrderDao orderDao = daoFactory.createOrderDao(connectionDao);
            Order result = orderDao.findById(idOrder).get();
            UserDao userDao = daoFactory.createUserDao(connectionDao);
            User client = userDao.findById(result.getIdClient()).get();
            result.setClient(client);
            result.setMenu(getOrderMenu(result.getId()));
            return result;
        }
    }

    /**
     * Finds orders suitable for processing by admin
     *
     * @return order list where field accepted = 0
     */
    @Override
    public List<Order> getActiveOrders() {
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()) {
            OrderDao orderDao = daoFactory.createOrderDao(connectionDao);
            List<Order> result = orderDao.findAll().stream()
                    .filter(order -> order.getAccepted() == 0)
                    .collect(Collectors.toList());
            return result;
        }
    }

    private static class Holder {
        private static OrderService INSTANCE = new OrderService();
    }
}
