package model.entity;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Order {
    private int id;
    private LocalDateTime timeOfOrder;
    private boolean accepted;
    private User client;
    private Menu[] meals;

    private Order(){}

    public int getId() {
        return id;
    }

    public static Builder builder() {
        return new Order().new Builder();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", timeOfOrder=" + timeOfOrder +
                ", accepted=" + accepted +
                ", client=" + client +
                ", meals=" + Arrays.toString(meals) +
                '}';
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

        public Builder setMeals(Menu[] meals) {
            Order.this.meals = Arrays.copyOf(meals, meals.length);
            return this;
        }
    }


}
