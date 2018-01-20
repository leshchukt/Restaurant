package model.dao;

import model.entity.Bill;
import model.entity.User;
import model.exception.EmailAlreadyExistsException;

import java.util.List;

public interface BillDao extends GenericDao<Bill> {

    @Override
    boolean create(Bill entity);

    List<Bill> findByClient(User client);
}
