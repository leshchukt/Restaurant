package model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Order {
    private int id;
    private LocalDateTime timeOfOrder;
    private int accepted;
    private int idClient;
    private User client;
    private List<Menu> menu = new ArrayList<>();

    private double price;

    private Order(){}

    public int getId() {
        return id;
    }

    public LocalDateTime getTimeOfOrder() {
        return timeOfOrder;
    }

    public int getAccepted() {
        return accepted;
    }

    public User getClient() {
        return client;
    }

    public double getPrice() {
        return price;
    }

    public int getIdClient() {
        return idClient;
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

    public void addMenuItem(Menu menuItem) {
        menu.add(menuItem);
    }

    public static Builder builder() {
        return new Order().new Builder();
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }

    public class Builder {
        private Builder(){}

        public Order build(){
            return Order.this;
        }

        public Builder setId(int id) {
            Order.this.id = id;
            return this;
        }

        public Builder setTimeOfOrder(LocalDateTime timeOfOrder) {
            Order.this.timeOfOrder = timeOfOrder;
            return this;
        }

        public Builder setAccepted(int accepted) {
            Order.this.accepted = accepted;
            return this;
        }

        public Builder setClient(User client) {
            Order.this.client = client;
            return this;
        }

        public Builder setIdClient(int idClient) {
            Order.this.idClient = idClient;
            return this;
        }

        public Builder setMenu(List<Menu> menu) {
            Order.this.menu = menu;
            return this;
        }

        public Builder setPrice(double price) {
            Order.this.price = price;
            return this;
        }
    }


}
