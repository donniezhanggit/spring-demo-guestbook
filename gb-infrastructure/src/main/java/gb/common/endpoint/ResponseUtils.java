package gb.common.endpoint;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.status;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.springframework.http.ResponseEntity;

import lombok.NoArgsConstructor;


@NoArgsConstructor(access=PRIVATE)
public final class ResponseUtils { // NOSONAR
    public static <T> ResponseEntity<T>
    wrapOrNotFound(@Nonnull final Optional<T> entry) {
        return entry.map(ResponseEntity::ok)
                .orElse(status(NOT_FOUND).body(null));
    }
}
