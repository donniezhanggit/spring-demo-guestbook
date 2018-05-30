package gb.common.guard;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.experimental.FieldDefaults;


@Getter
@FieldDefaults(level=PRIVATE, makeFinal=true)
public class InvalidArgumentException extends RuntimeException {
    private static final long serialVersionUID = 7581903639138345615L;


    String code;


    public InvalidArgumentException(final String code, final String message) {
        super(message);

        this.code = code;
    }
}
