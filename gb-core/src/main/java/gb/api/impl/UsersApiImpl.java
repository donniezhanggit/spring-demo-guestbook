package gb.api.impl;

import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.transaction.annotation.Transactional;

import gb.api.UsersApi;
import gb.common.annotations.Api;
import gb.dto.FullNameInput;
import gb.dto.UserEntry;
import gb.model.FullName;
import gb.model.User;
import gb.repos.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.val;
import lombok.experimental.FieldDefaults;


@Api
@Transactional
@AllArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
public class UsersApiImpl implements UsersApi {
    @NonNull UsersRepository usersRepo;


    @Override
    @Transactional(readOnly=true)
    public Optional<UserEntry> getUser(@NonNull String userName) {
        return usersRepo.findByUserName(userName, UserEntry.class);
    }


    @Override
    public void deactivateUser(@NonNull String userName) {
        final User user = usersRepo.findByUserNameOrThrow(userName);

        user.deactivate();
    }


    @Override
    public void activateUser(@NonNull String userName) {
        final User user = usersRepo.findByUserNameOrThrow(userName);

        user.activate();
    }


    @Override
    public void changeName(@NonNull String userName,
            @NonNull @Valid FullNameInput input) {
        final User user = usersRepo.findByUserNameOrThrow(userName);
        val newName = FullName.of(input);

        user.changeName(newName);
    }


    @Override
    public void deleteName(@NonNull String userName) {
        final User user = usersRepo.findByUserNameOrThrow(userName);

        user.deleteName();
    }
}
