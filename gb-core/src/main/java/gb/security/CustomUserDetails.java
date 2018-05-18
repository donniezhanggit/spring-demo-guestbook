package gb.security;

import static lombok.AccessLevel.PRIVATE;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import gb.model.User;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;


@EqualsAndHashCode(callSuper=true)
@FieldDefaults(level=PRIVATE, makeFinal=true)
public class CustomUserDetails
extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = -2595723933003639235L;

    User user;


    public CustomUserDetails(User user,
            Collection<? extends GrantedAuthority> authorities) {
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


    public User getUser() {
        return user;
    }
}
