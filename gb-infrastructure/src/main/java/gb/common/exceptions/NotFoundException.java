package gb.common.exceptions;

import javax.annotation.Nullable;


public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = 7005398038018870026L;

    private static final String NOT_FOUND_TEMPLATE =
            "Can not identify object by id: %s";


    public NotFoundException(@Nullable final Object id) {
        super(String.format(NOT_FOUND_TEMPLATE, id));
    }
}
