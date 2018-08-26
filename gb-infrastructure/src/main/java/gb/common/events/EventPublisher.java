package gb.common.events;


public interface EventPublisher {
    void raise(Object event);
}
