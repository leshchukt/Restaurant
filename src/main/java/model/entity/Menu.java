package model.entity;

public class Menu {
    private int id;
    private String title;
    private double price;
    private int weight;
    private Category category;
    private int amount = 1;

    private Menu(){}

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Menu object = (Menu) obj;
        return (id == object.getId());
    }

    @Override
    public int hashCode() {
        int hashCode = 17 * id + title.hashCode();
        hashCode = 17 * hashCode + (int)price;
        hashCode = 17 * hashCode + weight;
        return hashCode;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getWeight() {
        return weight;
    }

    public Category getCategory() {
        return category;
    }

    public int getAmount() {
        return amount;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public static Builder builder(){
        return new Menu().new Builder();
    }

    public class Builder {
        private Builder(){}

        public Menu build(){
            return Menu.this;
        }

        public Builder setId(int id){
            Menu.this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            Menu.this.title = title;
            return this;
        }

        public Builder setPrice(double price) {
            Menu.this.price = price;
            return this;
        }

        public Builder setWeight(int weight) {
            Menu.this.weight = weight;
            return this;
        }

        public Builder setCategory(Category category) {
            Menu.this.category = category;
            return this;
        }

        public Builder setAmount(int amount) {
            Menu.this.amount = amount;
            return this;
        }
    }
}
