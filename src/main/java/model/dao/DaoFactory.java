package model.dao;

import controller.command.util.Configuration;
import org.apache.log4j.Logger;

public abstract class DaoFactory {

    private static final Logger LOGGER = Logger.getLogger(DaoFactory.class);
    private static DaoFactory daoFactory;

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    try {
                        daoFactory = (DaoFactory) Class.forName(Configuration.getInstance().getFactoryClass()).newInstance();
                    } catch (Exception e) {
                        LOGGER.error("Error in getting the instance of DaoFactory: ", e);
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return daoFactory;
    }

    public abstract ConnectionDao getConnectionDao();

    public abstract BillDao createBillDao(ConnectionDao connectionDao);

    public abstract CategoryDao createCategoryDao(ConnectionDao connectionDao);

    public abstract LoginDao createLoginDao(ConnectionDao connectionDao);

    public abstract MenuDao createMenuDao(ConnectionDao connectionDao);

    public abstract OrderDao createOrderDao(ConnectionDao connectionDao);

    public abstract UserDao createUserDao(ConnectionDao connectionDao);
}

