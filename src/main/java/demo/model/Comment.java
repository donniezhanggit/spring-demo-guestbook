package demo.model;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Immutable;

import demo.dto.CommentInput;


@Entity
@Immutable
public class Comment extends DomainEntity {
    private static final long serialVersionUID = 1L;

    @NotNull
    private LocalDateTime created = LocalDateTime.now();

    private String name;

    @NotNull
    private String message;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="gbuser_id")
    private User user;


    protected Comment() {}

    public Comment(@NotNull final CommentInput input) {
        this.name = input.getName();
        this.message = input.getMessage();
    }

    public Comment(@NotNull final CommentBuilder cb) {
        this.created = cb.created;
        this.name = cb.name;
        this.message = cb.message;
        this.user = cb.user;
    }

    public Long getId() {
        return id;
    }

    public Short getVersion() {
        return version;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setVersion(short version) {
        this.version = version;
    }

    public void setCreated(@NotNull LocalDateTime date) {
        this.created = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Optional<User> getUser() {
        return Optional.ofNullable(this.user);
    }

    public void setUser(User user) {
        this.user = user;
    }


    @NotNull
    @Override
    public String toString() {
        return "Comment [id=" + id + ", created=" + created + ", name=" + name
            + ", message=" + message + "]";
    }
}
