package gb.common.exceptions;

import static lombok.AccessLevel.NONE;

import lombok.NoArgsConstructor;


@NoArgsConstructor(access=NONE)
public final class Exceptions {
    public static NotFoundException notFound(final Object id) {
        return new NotFoundException(id);
    }
}
