package gb.common.guard;

import gb.common.exceptions.InvalidArgumentException;
import lombok.NonNull;


public final class Guard { // NOSONAR
    public static void that(final boolean assertion,
            @NonNull final String code,
            @NonNull final String message) {
        if(!assertion) {
            throw new InvalidArgumentException(code, message);
        }
    }
}
