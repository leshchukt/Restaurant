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
import model.service.AcceptBillService;
import model.service.ClientBillService;
import model.service.DeclineBillService;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * BillService execute all operations with Bills via dao implementation
 */
public class BillService implements ClientBillService, DeclineBillService, AcceptBillService {
    private static final Logger LOGGER = Logger.getLogger(BillService.class);
    private DaoFactory daoFactory;

    private BillService() {
        daoFactory = DaoFactory.getInstance();
    }

    public static BillService getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * method to decline order by id for client or for administrator
     *
     * @param idOrder
     */
    @Override
    public void declineOrder(int idOrder) {
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()) {
            connectionDao.beginTransaction();
            OrderDao orderDao = daoFactory.createOrderDao(connectionDao);
            Order order = orderDao.findById(idOrder).get();
            if (order.getAccepted() == 0) {
                order.setAccepted(-1);
                if (orderDao.update(order) == 1) {
                    connectionDao.commitTransaction();
                } else throw new RuntimeException();
            } else throw new ConcurrentProcessingException("concurrent.order");
        }
    }

    /**
     * method to accept order for administrator
     *
     * @param order to accept
     * @param admin who accepting
     * @return bill for client
     */
    @Override
    public Bill acceptOrder(Order order, User admin) {
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()) {
            connectionDao.beginTransaction();
            OrderDao orderDao = daoFactory.createOrderDao(connectionDao);

            order = orderDao.findById(order.getId()).get();
            if (order.getAccepted() == 0) {
                order.setAccepted(1);
            } else throw new ConcurrentProcessingException("concurrent.order");

            orderDao.update(order);

            Bill bill = Bill.builder()
                    .setIdOrder(order.getId())
                    .setAdmin(admin)
                    .setOrder(order)
                    .build();

            addOrderPriceToBill(bill);

            BillDao billDao = daoFactory.createBillDao(connectionDao);

            if (!billDao.create(bill)) {
                connectionDao.rollbackTransaction();
                throw new RuntimeException();
            } else {
                connectionDao.commitTransaction();
                return bill;
            }
        }
    }

    /**
     * Method to pay the bill for client
     *
     * @param idBill
     */
    @Override
    public void payTheBill(int idBill) {
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()) {
            connectionDao.beginTransaction();
            BillDao billDao = daoFactory.createBillDao(connectionDao);
            Bill bill = billDao.findById(idBill).get();

            OrderDao orderDao = daoFactory.createOrderDao(connectionDao);
            addOrderWithMenuToBill(bill, orderDao);
            addOrderPriceToBill(bill);
            if (bill.getPayment_datetime() == null) {
                bill.pay();
                billDao.update(bill);
                connectionDao.commitTransaction();
            } else {
                throw new ConcurrentProcessingException("concurrent.bill");
            }
        }
    }

    /**
     * Method for pagination on restaurant/client/bills page
     *
     * @param client for whom these bills are intended
     * @param start  index of first bill on page
     * @param total  amount of bills for one page
     * @return list of bills for page
     */
    @Override
    public List<Bill> getLimitedBills(User client, int start, int total) {
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()) {
            BillDao billDao = daoFactory.createBillDao(connectionDao);
            List<Bill> bills = billDao.getWithLimit(client, start, total);
            OrderDao orderDao = daoFactory.createOrderDao(connectionDao);
            for (Bill bill : bills) {
                addOrderWithMenuToBill(bill, orderDao);
                addOrderPriceToBill(bill);
            }

            return bills;
        }
    }

    /**
     * Method for bill.setOrder and order.setMenu purpose
     *
     * @param bill
     * @param orderDao
     */
    private void addOrderWithMenuToBill(Bill bill, OrderDao orderDao) {
        Optional<Order> order = orderDao.findById(bill.getIdOrder());
        if (order.isPresent()) {
            bill.setOrder(order.get());
        } else throw new RuntimeException(NO_ID_EXCEPTION_MESSAGE);
    }

    /**
     * Method for getting bills of particular client
     *
     * @param client for whom these bills are intended
     * @return list of bills of this client
     */
    @Override
    public List<Bill> getBillsByClient(User client) {
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()) {
            BillDao billDao = daoFactory.createBillDao(connectionDao);
            return billDao.findByClient(client);
        }
    }

    /**
     * Method adds summary price to bill
     *
     * @param bill
     */
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
}
