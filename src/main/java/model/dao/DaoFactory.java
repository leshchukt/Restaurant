package model.dao;

import model.dao.implementation.JDBCDaoFactory;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract BillDao createBillDao();
    public abstract CategoryDao createCategoryDao();
    public abstract LoginDao createLoginDao();
    public abstract MenuDao createMenuDao();
    public abstract OrderDao createOrderDao();
    public abstract UserDao createUserDao();

    public static DaoFactory getInstance(){
        if( daoFactory == null ){
            synchronized (DaoFactory.class){
                if(daoFactory==null){
                    daoFactory = new JDBCDaoFactory();
                }
            }
        }
        return daoFactory;
    }

}

