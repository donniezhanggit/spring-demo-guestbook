package demo.common.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class BaseController {
    protected <T> ResponseEntity<T> responseFrom(final Optional<T> entry) {
        return entry.isPresent()
                ? ResponseEntity.ok(entry.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
