package model.service.implementation;

import model.dao.BillDao;
import model.dao.ConnectionDao;
import model.dao.DaoFactory;
import model.dao.OrderDao;
import model.entity.Bill;
import model.entity.Menu;
import model.entity.Order;
import model.entity.User;
import model.exception.ConcurrentProcessingException;
import model.service.*;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class BillService implements ClientBillService, DeclineBillService, AcceptBillService {
    private DaoFactory daoFactory;
    private static final Logger LOGGER = Logger.getLogger(BillService.class);

    private BillService(){
        daoFactory = DaoFactory.getInstance();
    }

    public List<Bill> getBills(User client) {
        return null;
    }

    @Override
    public void declineOrder(int idOrder) {
        try(ConnectionDao connectionDao = daoFactory.getConnectionDao()){
            connectionDao.beginTransaction();
            OrderDao orderDao = daoFactory.createOrderDao(connectionDao);
            Order order = orderDao.findById(idOrder).get();
            if(order.getAccepted() == 0) {
                order.setAccepted(-1);
                if(orderDao.update(order) == 1){
                    connectionDao.commitTransaction();
                }
                else throw new RuntimeException();
            }
            else {
                throw new ConcurrentProcessingException("concurrent.order");
            }
        }
    }

    public Bill acceptOrder(Order order, User admin) {
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()) {
            connectionDao.beginTransaction();
            OrderDao orderDao = daoFactory.createOrderDao(connectionDao);

            order = orderDao.findById(order.getId()).get();
            if(order.getAccepted() == 0) {
                order.setAccepted(1);
            }
            else{
                throw new ConcurrentProcessingException("concurrent.order");
            }
            orderDao.update(order);

            AdminOrderService orderService = OrderService.getInstance();
            int price = orderService.getSummaryPrice(order);

            Bill bill = Bill.builder()
                    .setIdOrder(order.getId())
                    .setAdmin(admin)
                    .setOrder(order)
                    .setPrice(price)
                    .build();

            BillDao billDao = daoFactory.createBillDao(connectionDao);

            if(!billDao.create(bill)){
                connectionDao.rollbackTransaction();
                return null;
            }
            else {
                connectionDao.commitTransaction();
                return bill;
            }
        }
    }

    public void payTheBill(int idBill) {
        try(ConnectionDao connectionDao = daoFactory.getConnectionDao()){
            connectionDao.beginTransaction();
            BillDao billDao = daoFactory.createBillDao(connectionDao);
            Bill bill = billDao.findById(idBill).get();

            OrderDao orderDao = daoFactory.createOrderDao(connectionDao);
            addOrderWithMenuToBill(bill, orderDao);
            addOrderPriceToBill(bill);
            if(bill.getPayment_datetime() == null){
                bill.pay();
                billDao.update(bill);
                connectionDao.commitTransaction();
            }
            else {
                throw new ConcurrentProcessingException("concurrent.bill");
            }
        }
    }

    private void addOrderWithMenuToBill(Bill bill, OrderDao orderDao) {
        Optional<Order> order = orderDao.findById(bill.getIdOrder());
        if (order.isPresent()) {
            bill.setOrder(order.get());
        } else throw new RuntimeException(NO_ID_EXCEPTION_MESSAGE);
    }

    @Override
    public List<Bill> getBillsByClient(User client) {
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()){
            BillDao billDao = daoFactory.createBillDao(connectionDao);
            List<Bill> bills = billDao.findByClient(client);

            OrderDao orderDao = daoFactory.createOrderDao(connectionDao);
            for (Bill bill : bills) {
                addOrderWithMenuToBill(bill, orderDao);
                addOrderPriceToBill(bill);
            }

            return bills;
        }
    }

    private void addOrderPriceToBill(Bill bill) {
        double price = 0.;
        for (Menu menuItem : bill.getOrder().getMenu()) {
            price += menuItem.getPrice() * menuItem.getAmount();
        }
        bill.setPrice(price);
    }

    private static class Holder {
        private static BillService INSTANCE = new BillService();
    }

    public static BillService getInstance(){
        return Holder.INSTANCE;
    }
    /*
    public List<Bill> getChecks(User client){
        //ConnectionDAO connectionDAO = factory.getConnectionDAO();
        CheckDAO checkDAO = factory.getCheckDAO(connectionDAO);
        List<Bill> result = checkDAO.getAllChecksForUser(client);
        loadOrdersIntoChecks(result, connectionDAO);
        loadAdminsIntoChecks(result, connectionDAO);
        connectionDAO.close();
        return result;
    }

    private void loadOrdersIntoChecks(List<Bill> checks, ConnectionDAO connectionDAO){
        OrderDAO orderDAO = factory.getOrderDAO(connectionDAO);
        for(Check check : checks){
            if(check.getOrder() == null){
                check.setOrder(orderDAO.getForId(check.getOrderId()).get());
            }
        }
    }

    private void loadAdminsIntoChecks(List<Check> checks, ConnectionDAO connectionDAO){
        UserDAO userDAO = factory.getUserDAO(connectionDAO);
        for(Check check : checks){
            if(check.getAdmin() == null){
                check.setAdmin(userDAO.getForId(check.getAdminId()).get());
            }
        }
    }

    public void payCheck(int checkId){
        try(ConnectionDAO connectionDAO = factory.getConnectionDAO()){
            connectionDAO.beginTransaction();
            CheckDAO checkDAO = factory.getCheckDAO(connectionDAO);
            Check check = checkDAO.getForId(checkId).get();
            if(check.getPaid() == null){
                check.pay();
                checkDAO.update(check);
                connectionDAO.commitTransaction();
            }
            else {
                throw new ConcurrentProcessingException("concurrent.check");
            }
        }
    }

    public Check acceptOrder(Order order, User admin){
        try(ConnectionDAO connectionDAO = factory.getConnectionDAO()) {
            connectionDAO.beginTransaction();
            OrderDAO orderDAO = factory.getOrderDAO(connectionDAO);


            order = orderDAO.getForId(order.getId()).get();
            if(order.getAccepted() == 0) {
                order.setAccepted(1);
            }
            else{
                throw new ConcurrentProcessingException("concurrent.order");
            }
            orderDAO.update(order);

            OrderService service = OrderServiceImpl.getInstance();
            int price = service.getSummaryPrice(order);

            Check check = new CheckBuilder()
                    .setAdmin(admin)
                    .setOrder(order)
                    .setPrice(price)
                    .createCheck();

            CheckDAO checkDAO = factory.getCheckDAO(connectionDAO);

            if(!checkDAO.insert(check)){
                connectionDAO.rollbackTransaction();
                return null;
            }
            else {
                connectionDAO.commitTransaction();
                return check;
            }
        }
    }

    public void declineOrder(int orderId){
        try(ConnectionDAO connectionDAO = factory.getConnectionDAO()){
            connectionDAO.beginTransaction();
            OrderDAO orderDAO = factory.getOrderDAO(connectionDAO);
            Order order = orderDAO.getForId(orderId).get();
            if(order.getAccepted() == 0) {
                order.setAccepted(-1);
                if(orderDAO.update(order) == 1){
                    connectionDAO.commitTransaction();
                }
                else throw new RuntimeException();
            }
            else {
                throw new ConcurrentProcessingException("concurrent.order");
            }
        }
    }
    */
}
