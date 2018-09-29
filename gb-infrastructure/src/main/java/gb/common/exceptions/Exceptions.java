package gb.common.exceptions;

import static lombok.AccessLevel.NONE;

import lombok.NoArgsConstructor;


/**
 * Static factory methods provider for common exceptions.
 *
 */
@NoArgsConstructor(access=NONE)
public final class Exceptions { // NOSONAR
    public static NotFoundException notFound(final Object id) {
        return new NotFoundException(id);
    }
}
