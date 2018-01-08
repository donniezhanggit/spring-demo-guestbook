package gb.services;

import java.util.Arrays;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import gb.model.User;
import gb.repos.UsersRepository;
import gb.security.CustomUserDetails;


@Service("userDetailsService")
@SuppressFBWarnings(value="SIC_INNER_SHOULD_BE_STATIC_ANON")
public class CustomUserDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;


    public CustomUserDetailsService(@Nonnull final UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @SuppressWarnings("serial")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> user = this.usersRepository.findByUsername(username);

        if(user.isPresent()) {
            return new CustomUserDetails(user.get(), Arrays.asList(
                new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return "ROLE_USER";
                    }
                },
                new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return "ROLE_ADMIN";
                    }
                },
                new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return "ROLE_ACTUATOR";
                    }
                }));
        }

        return null;
    }

}
