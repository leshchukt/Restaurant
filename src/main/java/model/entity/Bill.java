package model.entity;

import java.time.LocalDateTime;

public class Bill {
    private int idOrder;
    private LocalDateTime payment_datetime;
    private Order order;
    private User admin;
    private double price;

    private Bill() {
    }

    public static Builder builder() {
        return new Bill().new Builder();
    }

    public int getIdOrder() {
        return idOrder;
    }

    public LocalDateTime getPayment_datetime() {
        return payment_datetime;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void pay() {
        payment_datetime = LocalDateTime.now();
    }

    public class Builder {
        private Builder() {
        }

        public Bill build() {
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

        public Builder setOrder(Order order) {
            Bill.this.order = order;
            return this;
        }

        public Builder setAdmin(User admin) {
            Bill.this.admin = admin;
            return this;
        }

        public Builder setPrice(double price) {
            Bill.this.price = price;
            return this;
        }
    }
}
