package gb.domain;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import gb.common.domain.GeneratedIdDomainEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;


@Entity
@Table(name="gbuser")
@Getter
@Setter(value=PROTECTED)
@FieldDefaults(level=PRIVATE)
@NoArgsConstructor(access=PROTECTED)
@ToString
@FieldNameConstants
@EqualsAndHashCode(callSuper=false, onlyExplicitlyIncluded=true)
@NaturalIdCache
@Cache(usage=READ_WRITE)
public class User
extends GeneratedIdDomainEntity<User> {
    private static final long serialVersionUID = 1L;

    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 40;
    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 100;
    public static final int EMAIL_MIN_LENGTH = 4;
    public static final int EMAIL_MAX_LENGTH = 40;


    @NaturalId
    @EqualsAndHashCode.Include
    @NonNull String userName;
    @NonNull String password;
    @NonNull String email;
    LocalDateTime createdAt = LocalDateTime.now();
    boolean active = true;
    FullName fullName;


    User(@NonNull final UserBuilder ub) {
        setUserName(ub.userName);
        setPassword(ub.password);
        setEmail(ub.email);
        setCreatedAt(ub.createdAt);
        setActive(ub.active);
        setFullName(ub.fullName);

        registerEventProvider(NewUserRegistered::of);
    }


    public Optional<FullName> getFullName() {
        return Optional.ofNullable(fullName);
    }


    public void deactivate() {
        registerEventProvider(UserDeactivated::of);

        active = false;
    }


    public void activate() {
        registerEventProvider(UserActivated::of);

        active = true;
    }


    public void changeName(@Nullable final FullName newFullName) {
        registerEvent(UserFullNameChanged.of(this, newFullName));

        setFullName(newFullName);
    }


    public void deleteName() {
        registerEvent(UserFullNameDeleted.of(this));

        setFullName(null);
    }


    public static UserBuilder builder() {
        return new UserBuilder();
    }
}
