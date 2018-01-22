package model.dao;

import model.entity.Bill;
import model.entity.User;

import java.util.List;

public interface BillDao extends GenericDao<Bill> {

    @Override
    boolean create(Bill entity);

    List<Bill> findByClient(User client);

    List<Bill> getWithLimit(User client, int start, int total);
}
