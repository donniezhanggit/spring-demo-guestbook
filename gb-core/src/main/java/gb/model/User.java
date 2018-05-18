package gb.model;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.common.base.Preconditions;

import gb.common.domain.AbstractDomainEntity;
import lombok.Getter;
import lombok.experimental.FieldDefaults;


@Entity
@Table(name="gbuser")
@Getter
@FieldDefaults(level=PRIVATE)
public class User extends AbstractDomainEntity {
    private static final long serialVersionUID = 1L;

    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 40;
    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 100;
    public static final int EMAIL_MIN_LENGTH = 4;
    public static final int EMAIL_MAX_LENGTH = 40;


    String username;
    String password;
    String email;
    LocalDateTime created = LocalDateTime.now();
    boolean active = true;


    protected User() {}


    public User(@Nonnull final UserBuilder ub) {
        Preconditions.checkNotNull(ub.username);
        Preconditions.checkNotNull(ub.password);
        Preconditions.checkNotNull(ub.email);

        username = ub.username;
        password = ub.password;
        email    = ub.email;
        created  = ub.created;
        active   = ub.active;
    }
}
