package model.entity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Order {
    private int id;
    private LocalDateTime timeOfOrder;
    private boolean accepted;
    private User client;
    private List<Menu> menu;

    private Order(){}

    public int getId() {
        return id;
    }

    public LocalDateTime getTimeOfOrder() {
        return timeOfOrder;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public User getClient() {
        return client;
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Builder builder() {
        return new Order().new Builder();
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

        public Builder setAccepted(boolean accepted) {
            Order.this.accepted = accepted;
            return this;
        }

        public Builder setClient(User client) {
            Order.this.client = client;
            return this;
        }

        public Builder setMenu(List<Menu> menu) {
            Order.this.menu = menu;
            return this;
        }
    }


}
