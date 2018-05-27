package gb.services;

import static lombok.AccessLevel.PRIVATE;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import gb.model.User;
import gb.repos.UsersRepository;
import gb.security.CustomUserDetails;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;


@Service("userDetailsService")
@AllArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
@SuppressFBWarnings(value="SIC_INNER_SHOULD_BE_STATIC_ANON")
public class CustomUserDetailsService implements UserDetailsService {
    @NonNull UsersRepository usersRepository;


    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        final Optional<User> user = usersRepository.findByUsername(username);

        if(user.isPresent()) {
            return new CustomUserDetails(user.get(), Arrays.asList(
                grantedAuthorityOf("ROLE_USER"),
                grantedAuthorityOf("ROLE_ADMIN"),
                grantedAuthorityOf("ROLE_ACTUATOR")
            ));
        }

        throw new UsernameNotFoundException(
                "Can not find user by username: " + username);
    }


    @SuppressWarnings("serial")
    private GrantedAuthority grantedAuthorityOf(String role) {
        return new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return role;
            }
        };
    }
}
