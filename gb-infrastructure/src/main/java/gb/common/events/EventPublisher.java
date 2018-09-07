package gb.common.events;


public interface EventPublisher {
    void publishEvent(DomainEvent event);
}
