package gb.model;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.NONE;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Immutable;
import org.springframework.util.Assert;

import gb.common.domain.AbstractDomainEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.PackagePrivate;


@Entity
@Immutable
@Getter
@FieldDefaults(level=PRIVATE)
@NoArgsConstructor(access=PROTECTED)
public class Comment extends AbstractDomainEntity {
    private static final long serialVersionUID = 1L;

    public static final int ANON_NAME_MIN_LENGTH = 2;
    public static final int ANON_NAME_MAX_LENGTH = 20;
    public static final int MESSAGE_MIN_LENGTH = 1;
    public static final int MESSAGE_MAX_LENGTH = 2048;


    LocalDateTime created = LocalDateTime.now();
    String anonName;

    @NonNull
    @Setter(value=PROTECTED)
    String message;

    @Getter(value=NONE)
    @ManyToOne(fetch=LAZY, optional=true, targetEntity=User.class)
    @JoinColumn(name="gbuser_id")
    User user;


    @PackagePrivate
    Comment(@NonNull final CommentBuilder cb) {
        throwIfNotProvidedAnonNameAndUserName(cb.anonName, cb.user);

        setMessage(cb.message);

        if(cb.created != null) {
            created = cb.created;
        }

        if(cb.user != null) {
            user = cb.user;
        } else {
            anonName = cb.anonName;
        }
    }


    public Optional<User> getUser() {
        return Optional.ofNullable(user);
    }


    private static void throwIfNotProvidedAnonNameAndUserName(
            @Nullable final String anonName,
            @Nullable final User user) {
        Assert.isTrue(anonName != null || user != null,
                "Can not create a new comment without commenter's name");
    }
}
