package gb.common.exceptions;


public final class Exceptions {
    public static NotFoundException notFound(final Object id) {
        return new NotFoundException(id);
    }
}
