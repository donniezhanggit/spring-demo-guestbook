package gb.api;

import static gb.common.exceptions.Exceptions.notFound;
import static lombok.AccessLevel.PRIVATE;

import org.springframework.transaction.annotation.Transactional;

import gb.common.annotations.Api;
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
        final String currentUserName = getCurrentLoggedUserNameOrThrow();

        return usersRepo
                .findByUserNameOrThrow(currentUserName, UserEntry.class);
    }

    @Override
    public void changeNameOfCurrentUser(FullNameInput newNameInput) {
        final String username = getCurrentLoggedUserNameOrThrow();
        final User currentUser = usersRepo.findByUserNameOrThrow(username);
        final FullName newName = newNameInput.toFullName();

        currentUser.changeName(newName);
    }


    @Override
    public void deleteNameOfCurrentUser() {
        final String username = getCurrentLoggedUserNameOrThrow();
        final User currentUser = usersRepo.findByUserNameOrThrow(username);

        currentUser.deleteName();
    }


    private String getCurrentLoggedUserNameOrThrow() {
        return currentPrincipalService.getCurrentUser()
                .orElseThrow(() -> notFound(null))
                .getUserName();
    }
}
