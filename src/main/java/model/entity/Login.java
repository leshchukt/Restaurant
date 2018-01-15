package model.entity;

public class Login {
    private int id;
    private String email;
    private String password;

    private Login(){}

    public int getId() {
        return id;
    }

    public static Builder builder(){
        return new Login().new Builder();
    }

    @Override
    public String toString() {
        return "Login{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public class Builder {
        private Builder(){}

        public Login build(){
            return Login.this;
        }

        public Builder setId(int id) {
            Login.this.id = id;
            return this;
        }

        public Builder setEmail(String email) {
            Login.this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            Login.this.password = password;
            return this;
        }
    }
}