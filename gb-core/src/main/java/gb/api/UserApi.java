package gb.api;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;

import gb.dto.FullNameInput;
import gb.dto.UserEntry;


/**
 * Public front service for current logged user.
 *
 * @author whitesquall
 */
@Validated
public interface UserApi {
    /**
     * Get user info for current logged user.
     *
     * @category query
     * @return {@link UserEntry} mapped from current user domain model
     */
    @PreAuthorize("hasRole('USER')")
    UserEntry getCurrentUser();


    /**
     * Idempotent changing current user's full name.
     *
     * @category command
     * @param newName new first name and last name.
     * @throws ConstraintViolationException if input is invalid
     */
    @PreAuthorize("hasRole('USER')")
    void changeNameOfCurrentUser(@Valid FullNameInput newName);


    /**
     * Idempotent deleting current user's full name.
     *
     * @category command
     */
    @PreAuthorize("hasRole('USER')")
    void deleteNameOfCurrentUser();
}
