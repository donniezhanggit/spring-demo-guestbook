package gb.model;

import java.time.LocalDateTime;

import com.google.common.base.Preconditions;


public class UserBuilder {
    String username;
    String password;
    String email;

    LocalDateTime created = LocalDateTime.now();

    boolean active = true;


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


    public User build() {
        Preconditions.checkNotNull(this.username);
        Preconditions.checkNotNull(this.password);
        Preconditions.checkNotNull(this.email);

        return new User(this);
    }
}
