package gb.model;

import java.time.LocalDateTime;

import javax.annotation.Nonnull;


public class UserBuilder {
    String username;
    String password;
    String email;
    LocalDateTime created = LocalDateTime.now();
    boolean active = true;


    public UserBuilder username(@Nonnull final String username) {
        this.username = username;
        return this;
    }


    public UserBuilder password(@Nonnull final String password) {
        this.password = password;
        return this;
    }


    public UserBuilder email(@Nonnull final String email) {
        this.email = email;
        return this;
    }


    public UserBuilder active(final boolean active) {
        this.active = active;
        return this;
    }


    public User build() {
        return new User(this);
    }
}
