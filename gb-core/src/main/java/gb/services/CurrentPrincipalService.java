package gb.services;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import gb.domain.User;
import gb.security.CustomUserDetails;


@Service
public class CurrentPrincipalService {
    public Optional<Authentication> getCurrentAuth() {
        final Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();

        return Optional.ofNullable(auth);
    }


    // TODO: add another method which will throw an exception that current
    // user can not be identified and we should return 304 forbidden.
    public Optional<User> getCurrentUser() {
        final Object principal = getCurrentAuth()
                .map(Authentication::getPrincipal)
                .orElse(null);

        if(principal instanceof CustomUserDetails) {
            return Optional.of(((CustomUserDetails) principal).getUser());
        }

        return Optional.empty();
    }
}
