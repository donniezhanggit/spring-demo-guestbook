package gb.model;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class CommentBuilder {
    LocalDateTime created = LocalDateTime.now();
    String name;
    String message;
    User user;


    public CommentBuilder created(@Nonnull LocalDateTime created) {
        this.created = created;
        return this;
    }


    public CommentBuilder name(@Nullable String name) {
        this.name = name;
        return this;
    }


    public CommentBuilder message(@Nonnull String message) {
        this.message = message;
        return this;
    }


    public CommentBuilder user(@Nullable User user) {
        this.user = user;
        return this;
    }


    public CommentBuilder user(@Nonnull Optional<User> user) {
        this.user = user.orElse(null);
        return this;
    }


    public Comment build() {
        return new Comment(this);
    }
}
