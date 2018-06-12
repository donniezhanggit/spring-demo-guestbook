package gb.common.guard;

import static lombok.AccessLevel.NONE;

import lombok.NoArgsConstructor;
import lombok.NonNull;


@NoArgsConstructor(access=NONE)
public final class Guard {
    public static void that(final boolean assertion,
            @NonNull final String code,
            @NonNull final String message) {
        if(!assertion) {
            throw new InvalidArgumentException(code, message);
        }
    }
}
