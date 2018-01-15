package model.entity;

import java.time.LocalDate;

public class User {
    private int id;
    private String nickname;
    private LocalDate birthDate;
    private Role role;

    private User(){}

    public int getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public static Builder builder() {
        return new User().new Builder();
    }

    public class Builder {
        private Builder(){}

        public User build() {
            return User.this;
        }

        public Builder setId(int id) {
            User.this.id = id;
            return this;
        }

        public Builder setNickname(String nickname) {
            User.this.nickname = nickname;
            return this;
        }

        public Builder setBirthDate(LocalDate birthDate) {
            User.this.birthDate = birthDate;
            return this;
        }

        public Builder setRole(Role role) {
            User.this.role = role;
            return this;
        }
    }
}
