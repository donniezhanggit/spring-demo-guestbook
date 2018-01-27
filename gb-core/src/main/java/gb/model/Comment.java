package gb.model;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.Length;

import gb.common.domain.AbstractDomainEntity;
import gb.dto.CommentInput;


@Entity
@Immutable
public class Comment extends AbstractDomainEntity {
    private static final long serialVersionUID = 1L;

    public static final String CREATED_PROPERTY = "created";
    public static final String NAME_PROPERTY = "name";
    public static final String MESSAGE_PROPERTY = "message";
    public static final String USER_PROPERTY = "user";

    public static final int NAME_MIN_LENGTH = 2;
    public static final int NAME_MAX_LENGTH = 20;
    public static final int MESSAGE_MIN_LENGTH = 1;
    public static final int MESSAGE_MAX_LENGTH = 2048;

    @NotNull
    private LocalDateTime created = LocalDateTime.now();

    @Length(min=NAME_MIN_LENGTH, max=NAME_MAX_LENGTH)
    private String name;

    @NotNull
    @Length(min=MESSAGE_MIN_LENGTH, max=MESSAGE_MAX_LENGTH)
    private String message;

    @ManyToOne(fetch=FetchType.LAZY, optional=true, targetEntity=User.class)
    @JoinColumn(name="gbuser_id")
    private User user;


    protected Comment() {}

    public Comment(@Nonnull final CommentInput input) {
        this.name = input.getName();
        this.message = input.getMessage();
    }

    public Comment(@Nonnull final CommentBuilder cb) {
        this.created = cb.created;
        this.name    = cb.name;
        this.message = cb.message;
        this.user    = cb.user;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(@Nonnull LocalDateTime date) {
        this.created = date;
    }

    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(@Nonnull String message) {
        this.message = message;
    }

    public Optional<User> getUser() {
        return Optional.ofNullable(this.user);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
