package model.entity;

import java.time.LocalDateTime;

public class Bill {
    private int idOrder;
    private LocalDateTime payment_datetime;
    //private Order order;
    private User admin;

    private Bill(){}

    public int getIdOrder() {
        return idOrder;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public static Builder builder(){
        return new Bill().new Builder();
    }

    @Override
    public String toString() {
        return "Bill{" +
                "payment_datetime=" + payment_datetime +
                ", id_order=" + idOrder +
                ", admin=" + admin +
                '}';
    }

    public class Builder {
        private Builder(){}

        public Bill build(){
            return Bill.this;
        }

        public Builder setIdOrder(int idOrder) {
            Bill.this.idOrder = idOrder;
            return this;
        }

        public Builder setPayment_datetime(LocalDateTime payment_datetime) {
            Bill.this.payment_datetime = payment_datetime;
            return this;
        }
/*
        public Builder setOrder(Order order) {
            Bill.this.order = order;
            return this;
        }
*/
        public Builder setAdmin(User admin) {
            Bill.this.admin = admin;
            return this;
        }
    }
}
