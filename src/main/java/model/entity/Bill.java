package model.entity;

import java.time.LocalDateTime;

public class Bill {
    private LocalDateTime payment_datetime;
    private Order order;
    private User admin;

    private Bill(){}

    public static Builder builder(){
        return new Bill().new Builder();
    }

    @Override
    public String toString() {
        return "Bill{" +
                "payment_datetime=" + payment_datetime +
                ", order=" + order +
                ", admin=" + admin +
                '}';
    }

    public class Builder {
        private Builder(){}

        public Bill build(){
            return Bill.this;
        }

        public Builder setPayment_datetime(LocalDateTime payment_datetime) {
            Bill.this.payment_datetime = payment_datetime;
            return this;
        }

        public Builder setOrder(Order order) {
            Bill.this.order = order;
            return this;
        }

        public Builder setAdmin(User admin) {
            Bill.this.admin = admin;
            return this;
        }
    }
}
