package demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    List<Comment> comments = new ArrayList<>();


    public UserBuilder username(@NotNull String username) {
        this.username = username;
        return this;
    }


    // TODO: Change to array of char
    public UserBuilder password(@NotNull String password) {
        this.password = password;
        return this;
    }


    public UserBuilder email(@NotNull String email) {
        this.email = email;
        return this;
    }


    public UserBuilder active(boolean active) {
        this.active = active;
        return this;
    }


    public UserBuilder comments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }


    public User build() {
        return new User(this);
    }
}
