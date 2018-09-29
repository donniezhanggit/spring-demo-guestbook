package gb.common.events;

import org.springframework.core.GenericTypeResolver;


public interface PersistentEventHandler<T extends DomainEvent> {
    void handleEvent(T event);


    /**
     * Returns a supported event class for handler.
     *
     * Uses trick with runtime type deduction. We don't want to enforce
     * every implementation to provide this method.
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    default Class<T> appliesTo() {
        return (Class<T>) GenericTypeResolver
                .resolveTypeArgument(getClass(),
                        PersistentEventHandler.class);
    }
}
