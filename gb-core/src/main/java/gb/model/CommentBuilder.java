package gb.model;

import java.time.LocalDateTime;

import javax.annotation.Nullable;

import com.google.common.base.Preconditions;


public class CommentBuilder {
    LocalDateTime created = LocalDateTime.now();
    String name;
    String message;
    User user;


    public CommentBuilder created(LocalDateTime created) {
        this.created = created;
        return this;
    }


    public CommentBuilder name(String name) {
        this.name = name;
        return this;
    }


    public CommentBuilder message(String message) {
        this.message = message;
        return this;
    }


    public CommentBuilder user(@Nullable User user) {
        this.user = user;
        return this;
    }


    public Comment build() {
        Preconditions.checkNotNull(this.message);
        Preconditions.checkNotNull(this.created);

        return new Comment(this);
    }
}
