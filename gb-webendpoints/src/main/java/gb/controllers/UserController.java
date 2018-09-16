package gb.controllers;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import gb.api.UserApi;
import gb.dto.FullNameInput;
import gb.dto.UserEntry;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
@RestController
@RequestMapping("/api/user")
public class UserController {
    @NonNull UserApi userApi;


    @GetMapping
    public UserEntry getUser() {
        return userApi.getCurrentUser();
    }


    @PutMapping("/fullName")
    @ResponseStatus(NO_CONTENT)
    public void changeName(@RequestBody final FullNameInput newName) {
        userApi.changeNameOfCurrentUser(newName);
    }


    @DeleteMapping("/fullName")
    @ResponseStatus(NO_CONTENT)
    public void deleteName() {
        userApi.deleteNameOfCurrentUser();
    }
}
