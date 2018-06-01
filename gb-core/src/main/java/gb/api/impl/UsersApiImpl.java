package gb.api.impl;

import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('USER')")
    @Transactional(readOnly=true)
    public Optional<UserEntry> getUser(String userName) {
        return usersRepo.findByUsername(userName, UserEntry.class);
    }


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deactivateUser(String userName) {
        final Optional<User> user = usersRepo.findByUsername(userName);

        user.ifPresent(User::deactivate);
    }


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void activateUser(String userName) {
        final Optional<User> user = usersRepo.findByUsername(userName);

        user.ifPresent(User::activate);
    }


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void changeName(String userName, @Valid FullNameInput input) {
        final Optional<User> user = usersRepo.findByUsername(userName);
        val newName = FullName.of(input);

        user.ifPresent(u -> u.changeName(newName));
    }
}