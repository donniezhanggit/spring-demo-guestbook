package gb.common.endpoint;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.status;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import gb.common.dto.CodeAndMessage;
import gb.common.exceptions.InvalidArgumentException;
import lombok.NonNull;


@ControllerAdvice
public class IllegalArgumentAdvice {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CodeAndMessage>
    notValid(@NonNull final IllegalArgumentException e) {
        return status(BAD_REQUEST).body(new CodeAndMessage(e));
    }


    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<CodeAndMessage>
    notValid(@NonNull final InvalidArgumentException e) {
        return status(BAD_REQUEST).body(new CodeAndMessage(e));
    }
}
