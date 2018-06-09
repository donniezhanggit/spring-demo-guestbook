package gb.api;

import java.util.Optional;

import javax.validation.Valid;

import gb.dto.FullNameInput;
import gb.dto.UserEntry;
import lombok.NonNull;


/**
 * Public front service of users.
 *
 * @author whitesquall
 *
 */
public interface UsersApi {
    Optional<UserEntry> getUser(final String userName);
    void deactivateUser(final String userName);
    void activateUser(final String userName);
    void changeName(final String userName, @Valid final FullNameInput input);
    void deleteName(@NonNull final String userName);
}
