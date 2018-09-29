package gb.common.validation;

import lombok.NonNull;


/**
 * Business rules validation checker.
 *
 * This type of checks needed for business rules validation.
 * Imagine you have a service for hotel booking.
 * You need to check that:
 *  - day of occupancy is not empty;
 *  - day of leaving is not empty;
 *  - day of occupancy is before day of leaving;
 *  - booking interval has free rooms.
 *
 * First three checks can be done by simple bean validation without
 * touching a database, but last one can't.
 *
 * This class should helps with the last one check. You can use your
 * business layer to check that last rule had been passed. If not then
 * this checker will throw an {@link InvalidArgumentException} which will
 * be converted to an validation error representation according to
 * transport protocol.
 *
 * @see InvalidArgumentException
 */
public final class Check { // NOSONAR
    public static void that(final boolean assertion,
            @NonNull final String code,
            @NonNull final String message) {
        if(!assertion) {
            throw new InvalidArgumentException(code, message);
        }
    }
}
