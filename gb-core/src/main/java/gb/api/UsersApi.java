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
    /**
     * Get user info by user name.
     *
     * @category query
     * @param userName a unique user name of given user.
     * @return if user with userName exists return filled optional with
     *         {@link UserEntry}, otherwise {@code Optional.empty()}
     */
    Optional<UserEntry> getUser(final String userName);


    /**
     * Idempotent blocking user by user name. User won't be able to login
     * after all.
     *
     * @category command
     * @param userName a unique user name of given user.
     */
    void deactivateUser(final String userName);


    /**
     * Idempotent unblocking user by user name.
     *
     * @category command
     * @param userName a unique user name of given user.
     */
    void activateUser(final String userName);


    /**
     * Idempotent changing user's full name by user name.
     *
     * @category command
     * @param userName
     * @param input
     * @throws ConstraintViolationException if input is invalid.
     */
    void changeName(final String userName, @Valid final FullNameInput input);
    void deleteName(@NonNull final String userName);
}
