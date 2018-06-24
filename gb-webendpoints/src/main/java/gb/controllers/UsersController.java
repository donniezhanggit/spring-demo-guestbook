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

import gb.api.UsersApi;
import gb.common.endpoint.ResponseUtils;
import gb.dto.FullNameInput;
import gb.dto.UserEntry;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
@RestController
@RequestMapping("/api/users")
public class UsersController {
    @NonNull UsersApi usersApi;


    @GetMapping("/{userName}")
    @ResponseStatus(OK)
    public ResponseEntity<UserEntry>
    getUser(@PathVariable final String userName) {
        final Optional<UserEntry> entry = usersApi.getUser(userName);

        return ResponseUtils.wrapOrNotFound(entry);
    }


    @PutMapping("/{userName}/blocked")
    @ResponseStatus(NO_CONTENT)
    public void deactivate(@PathVariable final String userName) {
        usersApi.deactivateUser(userName);
    }


    @DeleteMapping("/{userName}/blocked")
    @ResponseStatus(NO_CONTENT)
    public void activate(@PathVariable final String userName) {
        usersApi.activateUser(userName);
    }


    @PutMapping("/{userName}/fullName")
    @ResponseStatus(NO_CONTENT)
    public void changeName(@PathVariable final String userName,
            @RequestBody final FullNameInput input) {
        usersApi.changeName(userName, input);
    }


    @DeleteMapping("/{userName}/fullName")
    @ResponseStatus(NO_CONTENT)
    public void deleteName(@PathVariable final String userName) {
        usersApi.deleteName(userName);
    }
}
