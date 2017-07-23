package demo.model;

import java.time.LocalDateTime;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

public class CommentBuilder {
    @NotNull
    LocalDateTime created = LocalDateTime.now();

    String name;

    String message;

    User user;


    public CommentBuilder created(@NotNull LocalDateTime created) {
        this.created = created;
        return this;
    }


    public CommentBuilder name(@NotNull String name) {
        this.name = name;
        return this;
    }


    public CommentBuilder message(@NotNull String message) {
        this.message = message;
        return this;
    }


    public CommentBuilder user(@Nullable User user) {
        this.user = user;
        return this;
    }


    public Comment build() {
        return new Comment(this);
    }
}
