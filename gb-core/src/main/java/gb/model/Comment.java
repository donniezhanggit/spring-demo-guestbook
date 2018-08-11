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

import gb.common.domain.AbstractDomainEntity;
import gb.common.guard.Guard;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.PackagePrivate;


@Entity
@Immutable
@Getter
@FieldDefaults(level=PRIVATE)
@NoArgsConstructor(access=PROTECTED)
@FieldNameConstants
public class Comment extends AbstractDomainEntity {
    private static final long serialVersionUID = 1L;

    public static final int ANON_NAME_MIN_LENGTH = 2;
    public static final int ANON_NAME_MAX_LENGTH = 20;
    public static final int MESSAGE_MIN_LENGTH = 1;
    public static final int MESSAGE_MAX_LENGTH = 2048;


    LocalDateTime createdAt = LocalDateTime.now();
    String anonName;

    @NonNull
    @Setter(value=PROTECTED)
    String message;

    @Getter(value=NONE)
    @ManyToOne(fetch=LAZY, optional=true)
    @JoinColumn(name="gbuser_id")
    User user;

    @PackagePrivate
    Comment(@NonNull final CommentBuilder cb) {
        throwIfNotProvidedAnonNameAndUserName(cb.anonName, cb.user);

        setUserOrAnonName(cb.user, cb.anonName);
        setCreatedAtIfNotNull(cb.createdAt);
        setMessage(cb.message);
    }


    public Optional<User> getUser() {
        return Optional.ofNullable(user);
    }


    private static void throwIfNotProvidedAnonNameAndUserName(
            @Nullable final String anonName,
            @Nullable final User user) {
        Guard.that(anonName != null || user != null,
                "EMPTY_AUTHOR",
                "Can not create new comment without author's name");
    }


    protected void
    setCreatedAtIfNotNull(@Nullable final LocalDateTime createdAt) {
        if(createdAt != null) {
            this.createdAt = createdAt;
        }
    }


    protected void setUserOrAnonName(
            @Nullable final User user,
            @Nullable final String anonName) {
        if(user != null) {
            this.user = user;
        } else {
            this.anonName = anonName;
        }
    }


    public static CommentBuilder builder() {
        return new CommentBuilder();
    }
}
