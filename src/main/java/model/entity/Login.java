package model.entity;

public class Login {
    private User user;
    private String email;
    private String password;

    private Login(){}

    public static Builder builder(){
        return new Login().new Builder();
    }

    @Override
    public String toString() {
        return "Login{" +
                "user=" + user +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public class Builder {
        private Builder(){}

        public Login build(){
            return Login.this;
        }

        public Builder setUser(User user) {
            Login.this.user = user;
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
