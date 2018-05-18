package gb.common.endpoint;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.Optional;

import org.springframework.http.ResponseEntity;


public abstract class BaseController {
    protected <T> ResponseEntity<T> responseFrom(final Optional<T> entry) {
        return entry.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(NOT_FOUND).body(null));
    }
}
