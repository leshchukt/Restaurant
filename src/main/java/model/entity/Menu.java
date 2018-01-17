package model.entity;

public class Menu {
    private int id;
    private String title;
    private double price;
    private int weight;
    private Category category;

    private Menu(){}

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

    public void setCategory(Category category) {
        this.category = category;
    }

    public static Builder builder(){
        return new Menu().new Builder();
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", category=" + category +
                '}';
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
    }
}
