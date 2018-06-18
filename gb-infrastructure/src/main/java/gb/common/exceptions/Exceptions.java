package gb.common.exceptions;


public final class Exceptions { // NOSONAR
    public static NotFoundException notFound(final Object id) {
        return new NotFoundException(id);
    }
}
