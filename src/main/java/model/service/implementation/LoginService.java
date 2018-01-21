package model.service.implementation;

import model.dao.ConnectionDao;
import model.dao.DaoFactory;
import model.dao.LoginDao;
import model.dao.UserDao;
import model.entity.Login;
import model.entity.User;
import model.exception.EmailAlreadyExistsException;
import model.service.LogInService;
import model.service.RegisterLoginService;

import java.util.Optional;

public class LoginService implements LogInService, RegisterLoginService {
    DaoFactory daoFactory;

    private LoginService() {
        daoFactory = DaoFactory.getInstance();
    }

    private static class Holder {
        private static LoginService INSTANCE = new LoginService();
    }

    public static LoginService getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void registerUser(Login login, User user) throws EmailAlreadyExistsException{
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()) {
            connectionDao.beginTransaction();
            UserDao userDao = daoFactory.createUserDao(connectionDao);
            if (userDao.create(user)) {
                login.setId(user.getId());
                LoginDao loginDao = daoFactory.createLoginDao(connectionDao);
                if(loginDao.create(login)) {
                    connectionDao.commitTransaction();
                } else {
                    connectionDao.rollbackTransaction();
                }
            }
        }
    }

    @Override
    public Optional<User> getUser(String email, String password) {
        try (ConnectionDao connectionDao = daoFactory.getConnectionDao()){
            LoginDao loginDao = daoFactory.createLoginDao(connectionDao);
            UserDao userDao = daoFactory.createUserDao(connectionDao);
            Optional<Login> login = loginDao.findUser(email, password);
            if (login.isPresent()) {
                return userDao.findById(login.get().getId());
            }
            return Optional.empty();
        }
    }
}
