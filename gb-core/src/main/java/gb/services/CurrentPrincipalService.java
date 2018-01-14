package gb.services;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class CurrentPrincipalService {
    public Optional<Authentication> getCurrentAuth() {
        final Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();

        return Optional.ofNullable(auth);
    }
}
