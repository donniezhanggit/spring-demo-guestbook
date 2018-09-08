package gb.common.events;


class NullEventHandler implements PersistentEventHandler<DomainEvent> {
    @Override
    public void handleEvent(DomainEvent event) {
        // Do nothing.
    }
}
