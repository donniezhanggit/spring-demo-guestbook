package gb.model;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.util.Assert;

import gb.common.domain.AbstractDomainEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;


@Entity
@Table(name="gbuser")
@Getter
@FieldDefaults(level=PRIVATE)
@NoArgsConstructor(access=PROTECTED)
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


    public User(@NonNull final UserBuilder ub) {
        Assert.notNull(ub.username, "username must not be null");
        Assert.notNull(ub.password, "password must not be null");
        Assert.notNull(ub.email, "email must not be null");

        username = ub.username;
        password = ub.password;
        email    = ub.email;
        created  = ub.created;
        active   = ub.active;
    }
}
