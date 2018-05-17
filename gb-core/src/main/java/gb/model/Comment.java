package gb.model;

import static lombok.AccessLevel.NONE;
import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Immutable;

import com.google.common.base.Preconditions;

import gb.common.domain.AbstractDomainEntity;
import lombok.Getter;
import lombok.experimental.FieldDefaults;


@Entity
@Immutable
@Getter
@FieldDefaults(level=PRIVATE)
public class Comment extends AbstractDomainEntity {
    private static final long serialVersionUID = 1L;

    public static final int NAME_MIN_LENGTH = 2;
    public static final int NAME_MAX_LENGTH = 20;
    public static final int MESSAGE_MIN_LENGTH = 1;
    public static final int MESSAGE_MAX_LENGTH = 2048;


    LocalDateTime created = LocalDateTime.now();
    String name;
    String message;

    @Getter(value=NONE)
    @ManyToOne(fetch=FetchType.LAZY, optional=true, targetEntity=User.class)
    @JoinColumn(name="gbuser_id")
    User user;


    protected Comment() {}


    public Comment(@Nonnull final CommentBuilder cb) {
        Preconditions.checkNotNull(cb.message);
        Preconditions.checkNotNull(cb.created);

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
