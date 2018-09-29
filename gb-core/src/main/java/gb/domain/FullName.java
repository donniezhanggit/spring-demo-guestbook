package gb.domain;

import static lombok.AccessLevel.PRIVATE;

import java.io.Serializable;

import javax.persistence.Embeddable;

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
public class FullName implements Serializable {
    private static final long serialVersionUID = 5361206595860713777L;

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
