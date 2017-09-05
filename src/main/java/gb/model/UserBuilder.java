package gb.model;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;


public class UserBuilder {
    @NotNull
    String username;

    @NotNull
    String password;

    @NotNull
    String email;

    LocalDateTime created = LocalDateTime.now();

    boolean active = true;


    public UserBuilder username(@NotNull final String username) {
        this.username = username;
        return this;
    }


    public UserBuilder password(@NotNull final String password) {
        this.password = password;
        return this;
    }


    public UserBuilder email(@NotNull final String email) {
        this.email = email;
        return this;
    }


    public UserBuilder active(final boolean active) {
        this.active = active;
        return this;
    }


    public User build() {
        final User user = new User(this);

        return user;
    }
}
