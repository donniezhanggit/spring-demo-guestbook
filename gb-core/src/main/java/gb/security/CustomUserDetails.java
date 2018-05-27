package gb.security;

import static lombok.AccessLevel.PRIVATE;

import java.util.Collection;

import javax.annotation.Nonnull;

import org.springframework.security.core.GrantedAuthority;

import gb.model.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;


@EqualsAndHashCode(callSuper=true)
@FieldDefaults(level=PRIVATE, makeFinal=true)
public class CustomUserDetails
extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = -2595723933003639235L;

    @Getter
    User user;


    public CustomUserDetails(@Nonnull final User user,
            @Nonnull final Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);

        this.user = user;
    }


    public CustomUserDetails(User user, boolean enabled,
            boolean accountNonExpired, boolean credentialsNonExpired,
            boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), enabled,
                accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);

        this.user = user;
    }
}
