package model.service;

import model.dao.DaoFactory;
import model.entity.Bill;
import model.entity.Order;
import model.entity.User;
import org.apache.log4j.Logger;

import java.util.List;

public class BillService {
    private DaoFactory factory;
    private static final Logger LOGGER = Logger.getLogger(BillService.class);

    private BillService(){
        factory = DaoFactory.getInstance();
    }

    public List<Bill> getBills(User client) {
        return null;
    }

    public void declineOrder(int orderId) {

    }

    public void acceptOrder(Order order, User admin) {

    }

    public void payTheBill(int billId) {

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
