package model.service;

import model.dao.DaoFactory;
import model.dao.LoginDao;
import model.dao.UserDao;
import model.entity.Login;
import model.entity.User;
import model.exception.EmailAlreadyExistsException;

import java.util.Optional;

public class LoginService {
    DaoFactory daoFactory;

    private LoginService() {
        daoFactory = DaoFactory.getInstance();
    }

    public void registerUser(Login login, User user) throws EmailAlreadyExistsException{

    }

    private static class Holder {
        private static LoginService INSTANCE = new LoginService();
    }

    public static LoginService getInstance() {
        return Holder.INSTANCE;
    }

    public Optional<User> getUser(String email, String password){
        try (LoginDao loginDao = daoFactory.createLoginDao();
             UserDao userDao = daoFactory.createUserDao()){
            Optional<Login> login = loginDao.findUser(email, password);
            if (login.isPresent()) {
                return userDao.findById(login.get().getId());
            }
            return Optional.empty();
        }
    }
}
