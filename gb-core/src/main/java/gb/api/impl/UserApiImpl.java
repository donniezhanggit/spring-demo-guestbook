/**
 *
 */
package gb.api.impl;

import static lombok.AccessLevel.PRIVATE;

import org.springframework.transaction.annotation.Transactional;

import gb.api.UserApi;
import gb.common.annotations.Api;
import gb.common.exceptions.Exceptions;
import gb.dto.FullNameInput;
import gb.dto.UserEntry;
import gb.model.FullName;
import gb.model.User;
import gb.repos.UsersRepository;
import gb.services.CurrentPrincipalService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;


@Api
@Transactional
@AllArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
public class UserApiImpl implements UserApi {
    @NonNull UsersRepository usersRepo;
    @NonNull CurrentPrincipalService currentPrincipalService;


    @Override
    public UserEntry getCurrentUser() {
        final String currentUserName = getCurrentLoggedUserOrThrow()
                .getUserName();

        return usersRepo
                .findByUserNameOrThrow(currentUserName, UserEntry.class);
    }

    @Override
    public void changeNameOfCurrentUser(FullNameInput newNameInput) {
        final User currentUser = getCurrentLoggedUserOrThrow();
        final FullName newName = newNameInput.toFullName();

        currentUser.changeName(newName);
    }


    private User getCurrentLoggedUserOrThrow() {
        return currentPrincipalService.getCurrentUser()
                .orElseThrow(() -> Exceptions.notFound(null));
    }
}
