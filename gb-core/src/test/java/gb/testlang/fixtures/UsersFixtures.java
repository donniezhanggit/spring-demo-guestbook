package gb.testlang.fixtures;

import static gb.testlang.fixtures.FullNameFixtures.FIRST_NAME;
import static gb.testlang.fixtures.FullNameFixtures.LAST_NAME;
import static lombok.AccessLevel.PRIVATE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gb.dto.UserEntry;
import gb.model.User;
import gb.model.UserBuilder;
import gb.repos.UsersRepository;
import lombok.experimental.FieldDefaults;


@Component
@FieldDefaults(level=PRIVATE)
public class UsersFixtures {
    public static final Long EXISTING_ID = 10053L;
    public static final Short JUST_CREATED_VERSION = 0;
    public static final String EXISTING_USERNAME = "testUser";
    public static final String NON_EXISTENT_USERNAME = "nobody";
    public static final String PASSWORD = "P4ssW0rD";
    public static final String EMAIL = "user@mail.org";
    public static final LocalDateTime CREATED =
            LocalDateTime.of(2018, 01, 02, 12, 31, 41);
    public static final LocalDate REGISTERED_AT = CREATED.toLocalDate();

    @Autowired(required=false)
    UsersRepository usersRepo;


    public User recreateExistingActiveUser() {
        usersRepo.deleteAll();

        final User user = filledUserBuilder().active(true).build();

        return usersRepo.save(user);
    }


    public User recreateExistingInactiveUser() {
        usersRepo.deleteAll();

        final User user = filledUserBuilder().active(false).build();

        return usersRepo.save(user);
    }


    public User recreateUserWithoutFullName() {
        usersRepo.deleteAll();

        final User user = filledUserBuilder().fullName(null).build();

        return usersRepo.save(user);
    }


    public User recreateExistingUser() {
        usersRepo.deleteAll();

        final User newUser = buildUser();

        return usersRepo.save(newUser);
    }


    public static UserBuilder filledUserBuilder() {
        return new UserBuilder()
                .username(EXISTING_USERNAME)
                .password(PASSWORD)
                .email(EMAIL)
                .created(CREATED)
                .fullName(FIRST_NAME, LAST_NAME)
                .active(true);
    }


    public static User buildUser() {
        return filledUserBuilder().build();
    }


    public static User buildUserWithId() {
        final User user = buildUser();

        DomainClassFixtures.setId(user, EXISTING_ID);
        DomainClassFixtures.setVersion(user, JUST_CREATED_VERSION);

        return user;
    }


    public static User stubUser() {
        final User stubbedUser = mock(User.class);

        when(stubbedUser.getId()).thenReturn(EXISTING_ID);
        when(stubbedUser.getUsername()).thenReturn(EXISTING_USERNAME);

        return stubbedUser;
    }


    public static UserEntry buildUserEntry() {
        return UserEntry.builder()
                .id(EXISTING_ID)
                .version(JUST_CREATED_VERSION)
                .userName(EXISTING_USERNAME)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .registeredAt(REGISTERED_AT)
                .active(true)
                .build();
    }
}
