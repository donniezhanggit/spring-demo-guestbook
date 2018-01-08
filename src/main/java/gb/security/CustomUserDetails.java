package gb.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import gb.model.User;


public class CustomUserDetails
extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = -2595723933003639235L;

    private User user;


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
        return this.user;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        CustomUserDetails other = (CustomUserDetails) obj;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }



}
