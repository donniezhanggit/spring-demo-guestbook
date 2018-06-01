package gb.model;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;
import static lombok.AccessLevel.PUBLIC;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import gb.common.domain.AbstractDomainEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.PackagePrivate;


@Entity
@Table(name="gbuser")
@Getter
@Setter(value=PROTECTED)
@FieldDefaults(level=PRIVATE)
@NoArgsConstructor(access=PROTECTED)
@ToString
public class User extends AbstractDomainEntity {
    private static final long serialVersionUID = 1L;

    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 40;
    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 100;
    public static final int EMAIL_MIN_LENGTH = 4;
    public static final int EMAIL_MAX_LENGTH = 40;


    @NaturalId
    @NonNull String username;
    @NonNull String password;
    @NonNull String email;
    LocalDateTime created = LocalDateTime.now();
    boolean active = true;

    @Setter(value=PUBLIC)
    @Embedded
    FullName fullName;


    @PackagePrivate
    User(@NonNull final UserBuilder ub) {
        setUsername(ub.username);
        setPassword(ub.password);
        setEmail(ub.email);
        setCreated(ub.created);
        setActive(ub.active);
        setFullName(ub.fullName);
    }


    public Optional<FullName> getFullName() {
        return Optional.ofNullable(fullName);
    }


    public void deactivate() {
        active = false;
    }


    public void activate() {
        active = true;
    }


    public void changeName(final FullName newFullName) {
        fullName = newFullName;
    }
}
