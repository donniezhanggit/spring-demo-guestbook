package gb.common.endpoint;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.status;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.springframework.http.ResponseEntity;


public abstract class BaseController {
    protected <T> ResponseEntity<T>
    responseFrom(@Nonnull final Optional<T> entry) {
        return entry.map(ResponseEntity::ok)
                .orElse(status(NOT_FOUND).body(null));
    }
}
