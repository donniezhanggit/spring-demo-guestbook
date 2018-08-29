package gb.api;

import java.util.Optional;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;

import gb.common.exceptions.NotFoundException;
import gb.dto.FullNameInput;
import gb.dto.UserEntry;


/**
 * Public front service of users.
 *
 * @author whitesquall
 */
@Validated
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
    Optional<UserEntry> getUser(String userName);


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
    void deactivateUser(String userName);


    /**
     * Idempotent unblocking user by user name.
     *
     * @category command
     * @param userName a unique user name of given user.
     * @throws NotFoundException if user by userName not found.
     */
    @CacheEvict(key="{#userName}")
    @PreAuthorize("hasRole('ADMIN')")
    void activateUser(String userName);


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
    void changeName(String userName, @Valid FullNameInput input);


    /**
     * Idempotent removing user's full name by user name.
     *
     * @category command
     * @param userName a unique user name of given user.
     * @throws NotFoundException if user by userName not found.
     */
    @CacheEvict(key="{#userName}")
    @PreAuthorize("hasRole('ADMIN')")
    void deleteName(String userName);
}
