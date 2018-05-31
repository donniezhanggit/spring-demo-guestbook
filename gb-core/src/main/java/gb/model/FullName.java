package gb.model;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;


@Value
@AllArgsConstructor
@NoArgsConstructor(access=PRIVATE, force=true)
public final class FullName {
    public static final int FIRST_NAME_MIN_LENGTH = 2;
    public static final int FIRST_NAME_MAX_LENGTH = 60;
    public static final int LAST_NAME_MIN_LENGTH = 2;
    public static final int LAST_NAME_MAX_LENGTH = 60;


    @NonNull String firstName;
    @NonNull String lastName;


    public FullName(final FullName other) {
        this(other.getFirstName(), other.getLastName());
    }
}
