package gb.model;

import java.time.LocalDateTime;


public class UserBuilder {
    String username;
    String password;
    String email;
    LocalDateTime created = LocalDateTime.now();
    boolean active = true;
    FullName fullName;


    public UserBuilder username(final String username) {
        this.username = username;

        return this;
    }


    public UserBuilder password(final String password) {
        this.password = password;

        return this;
    }


    public UserBuilder email(final String email) {
        this.email = email;

        return this;
    }


    public UserBuilder active(final boolean active) {
        this.active = active;

        return this;
    }


    public UserBuilder
    fullName(final String firstName, final String lastName) {
        return fullName(new FullName(firstName, lastName));
    }


    public UserBuilder fullName(final FullName fullName) {
        this.fullName = fullName;

        return this;
    }


    public User build() {
        return new User(this);
    }
}
