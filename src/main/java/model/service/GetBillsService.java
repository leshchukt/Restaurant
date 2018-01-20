package model.service;

import model.entity.Bill;
import model.entity.User;

import java.util.List;

public interface GetBillsService {
    String NO_ID_EXCEPTION_MESSAGE = "No such id in database";

    List<Bill> getBillsByClient(User client);
}
