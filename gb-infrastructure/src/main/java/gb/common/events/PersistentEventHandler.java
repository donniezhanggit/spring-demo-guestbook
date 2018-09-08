package gb.common.events;

import org.springframework.core.GenericTypeResolver;


public interface PersistentEventHandler<T extends DomainEvent> {
    void handleEvent(T event);


    @SuppressWarnings("unchecked")
    default Class<T> appliesTo() {
        return (Class<T>) GenericTypeResolver
                .resolveTypeArgument(getClass(),
                        PersistentEventHandler.class);
    }
}
