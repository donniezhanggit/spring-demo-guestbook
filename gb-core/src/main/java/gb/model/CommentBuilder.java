package gb.model;


import java.time.LocalDateTime;
import java.util.Optional;


public class CommentBuilder {
    LocalDateTime createdAt;
    String anonName;
    String message;
    User user;


    public CommentBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }


    public CommentBuilder anonName(String anonName) {
        this.anonName = anonName;
        return this;
    }


    public CommentBuilder message(String message) {
        this.message = message;
        return this;
    }


    public CommentBuilder user(User user) {
        this.user = user;
        return this;
    }


    public CommentBuilder user(Optional<User> user) {
        this.user = user.orElse(null);
        return this;
    }


    public Comment build() {
        return new Comment(this);
    }
}
