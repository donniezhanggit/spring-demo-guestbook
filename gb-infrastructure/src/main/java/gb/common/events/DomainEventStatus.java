package gb.common.events;


/**
 * Event status which signals current event processing state.
 *
 * Used in {@link PersistentDomainEvent}.
 */
enum DomainEventStatus {
    /**
     * Status of an event signaling that an event was registered during
     * main aggregate command processing. And waiting when it will be
     * processed by every event subscriber.
     */
    PENDING,

    /**
     * Status of an event signaling that an event was successfully
     * processed by every event subscriber.
     */
    PROCESSED
}
