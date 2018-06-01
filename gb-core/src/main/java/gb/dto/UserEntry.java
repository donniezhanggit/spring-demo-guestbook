package gb.dto;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDate;
import java.util.function.Function;

import gb.model.FullName;
import gb.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.val;


@Value
@AllArgsConstructor
@NoArgsConstructor(access=PRIVATE, force=true)
@Builder
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

        return UserEntry.builder()
                .id(user.getId())
                .version(user.getVersion())
                .userName(user.getUsername())
                .firstName(firstName)
                .lastName(lastName)
                .email(user.getEmail())
                .registeredAt(user.getCreated().toLocalDate())
                .active(user.isActive())
                .build();
    }


    private static <T> T fieldOfOptional(final User user,
            final Function<FullName, T> extractor) {
        return user.getFullName().map(extractor).orElse(null);
    }
}
