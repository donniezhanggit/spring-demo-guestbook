package gb.model;

import static lombok.AccessLevel.PRIVATE;

import javax.persistence.Embeddable;

import gb.dto.FullNameInput;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.FieldNameConstants;


@Value
@AllArgsConstructor
@NoArgsConstructor(access=PRIVATE, force=true)
@FieldNameConstants
@Embeddable
public class FullName {
    public static final int FIRST_NAME_MIN_LENGTH = 2;
    public static final int FIRST_NAME_MAX_LENGTH = 60;
    public static final int LAST_NAME_MIN_LENGTH = 2;
    public static final int LAST_NAME_MAX_LENGTH = 60;


    @NonNull String firstName;
    @NonNull String lastName;


    public FullName(final FullName other) {
        this(other.getFirstName(), other.getLastName());
    }


    public static final FullName of(final FullNameInput input) {
        return new FullName(input.getFirstName(), input.getLastName());
    }
}
