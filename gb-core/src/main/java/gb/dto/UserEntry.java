package gb.dto;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDate;
import java.util.function.Function;

import gb.model.FullName;
import gb.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.val;
import lombok.experimental.Wither;


@Value
@Wither(value=PRIVATE)
@AllArgsConstructor
@NoArgsConstructor(access=PRIVATE, force=true)
public class UserEntry {
    Long id;
    Short version;
    String userName;
    String firstName;
    String lastName;
    String email;
    LocalDate registeredAt;
    Boolean active;


    public static UserEntry from(@NonNull final User user) {
        val firstName = fieldOfOptional(user, FullName::getFirstName);
        val lastName = fieldOfOptional(user, FullName::getLastName);

        return new UserEntry()
                .withId(user.getId())
                .withVersion(user.getVersion())
                .withUserName(user.getUsername())
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(user.getEmail())
                .withRegisteredAt(user.getCreated().toLocalDate())
                .withActive(user.isActive());
    }


    private static <T> T fieldOfOptional(final User user,
            final Function<FullName, T> extractor) {
        return user.getFullName().map(extractor).orElse(null);
    }
}
