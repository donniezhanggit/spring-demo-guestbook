package gb.model;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;


@Getter
@NoArgsConstructor(access=PROTECTED)
@AllArgsConstructor
@FieldDefaults(level=PRIVATE)
@Builder
@EqualsAndHashCode
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
