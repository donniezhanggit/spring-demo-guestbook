package gb.api;

import java.util.Optional;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;

import gb.common.exceptions.NotFoundException;
import gb.dto.FullNameInput;
import gb.dto.UserEntry;
import lombok.NonNull;


/**
 * Public front service of users.
 *
 * @author whitesquall
 *
 */
@CacheConfig(cacheNames="users")
public interface UsersApi {
    /**
     * Get user info by user name.
     *
     * @category query
     * @param userName a unique user name of given user.
     * @return if user with userName exists return filled optional with
     *         {@link UserEntry}, otherwise {@code Optional.empty()}
     */
    @Cacheable(key="{#userName}")
    @PreAuthorize("hasRole('USER')")
    Optional<UserEntry> getUser(final String userName);


    /**
     * Idempotent blocking user by user name. User won't be able to login
     * after all.
     *
     * @category command
     * @param userName a unique user name of given user.
     * @throws NotFoundException if user by userName not found.
     */
    @CacheEvict(key="{#userName}")
    @PreAuthorize("hasRole('ADMIN')")
    void deactivateUser(final String userName);


    /**
     * Idempotent unblocking user by user name.
     *
     * @category command
     * @param userName a unique user name of given user.
     * @throws NotFoundException if user by userName not found.
     */
    @CacheEvict(key="{#userName}")
    @PreAuthorize("hasRole('ADMIN')")
    void activateUser(final String userName);


    /**
     * Idempotent changing user's full name by user name.
     *
     * @category command
     * @param userName a unique user name of given user.
     * @param input new first name and last name.
     * @throws ConstraintViolationException if input is invalid.
     * @throws NotFoundException if user by userName not found.
     */
    @CacheEvict(key="{#userName}")
    @PreAuthorize("hasRole('ADMIN')")
    void changeName(final String userName, @Valid final FullNameInput input);


    /**
     * Idempotent removing user's full name by user name.
     *
     * @category command
     * @param userName a unique user name of given user.
     * @throws NotFoundException if user by userName not found.
     */
    @CacheEvict(key="{#userName}")
    @PreAuthorize("hasRole('ADMIN')")
    void deleteName(@NonNull final String userName);
}
