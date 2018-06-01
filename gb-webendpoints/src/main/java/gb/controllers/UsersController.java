package gb.controllers;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import gb.common.endpoint.BaseController;
import gb.dto.UserEntry;
import gb.model.FullName;
import gb.model.User;
import gb.repos.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@AllArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
@RestController
@RequestMapping("/api/users")
public class UsersController extends BaseController {
    @NonNull UsersRepository usersRepo;


    @GetMapping("/{userName}")
    @ResponseStatus(OK)
    public ResponseEntity<UserEntry>
    getComment(@PathVariable final String userName) {
        final Optional<User> user = usersRepo.findByUsername(userName);
        final Optional<UserEntry> entry = user.map(UserEntry::from);

        return responseFrom(entry);
    }


    @PutMapping("/{userName}/blocked")
    @ResponseStatus(NO_CONTENT)
    public void deactivate(@PathVariable final String userName) {
        final Optional<User> user = usersRepo.findByUsername(userName);

        user.ifPresent(User::deactivate);
        user.ifPresent(usersRepo::save);

        log.info("user: {}", user.orElse(null));
    }


    @DeleteMapping("/{userName}/blocked")
    @ResponseStatus(NO_CONTENT)
    public void activate(@PathVariable final String userName) {
        final Optional<User> user = usersRepo.findByUsername(userName);

        user.ifPresent(User::activate);
        user.ifPresent(usersRepo::save);

        log.info("user: {}", user.orElse(null));
    }


    @PutMapping("/{userName}/fullName")
    @ResponseStatus(NO_CONTENT)
    public void changeName(@PathVariable final String userName,
            @RequestBody final FullName newFullName) {
        final Optional<User> user = usersRepo.findByUsername(userName);

        user.ifPresent(u -> u.changeName(newFullName));
        user.ifPresent(usersRepo::save);

        log.info("user: {}", user.orElse(null));
    }
}
