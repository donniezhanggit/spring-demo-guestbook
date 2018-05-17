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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;


@Entity
@Immutable
@Getter
@Setter
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

    @Setter(value=AccessLevel.NONE)
    @NotNull
    private LocalDateTime created = LocalDateTime.now();

    @Setter(onParam=@__(@Nullable))
    @Length(min=NAME_MIN_LENGTH, max=NAME_MAX_LENGTH)
    private String name;

    @Setter(onParam=@__(@Nonnull))
    @NotNull
    @Length(min=MESSAGE_MIN_LENGTH, max=MESSAGE_MAX_LENGTH)
    private String message;

    @Getter(value=AccessLevel.NONE)
    @Setter(onParam=@__(@Nullable))
    @ManyToOne(fetch=FetchType.LAZY, optional=true, targetEntity=User.class)
    @JoinColumn(name="gbuser_id")
    private User user;


    protected Comment() {}


    public Comment(@Nonnull final CommentBuilder cb) {
        created = cb.created;
        message = cb.message;

        if(cb.user != null) {
            user = cb.user;
        } else {
            name = cb.name;
        }
    }


    public Optional<User> getUser() {
        return Optional.ofNullable(user);
    }
}
