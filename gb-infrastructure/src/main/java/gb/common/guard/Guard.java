package gb.common.guard;

import lombok.NonNull;
import lombok.experimental.UtilityClass;


@UtilityClass
public final class Guard {
    public void that(final boolean assertion, @NonNull final String code,
            @NonNull final String message) {
        if(!assertion) {
            throw new InvalidArgumentException(code, message);
        }
    }
}
