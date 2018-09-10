package gb.common.events;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;
import static lombok.AccessLevel.PRIVATE;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.NonNull;
import lombok.experimental.FieldDefaults;


@Service
@FieldDefaults(level=PRIVATE, makeFinal=true)
public class PersistentEventsProcessor {
    // For every event type there can be more than one handler.
    // Let's store it as list of handlers by event type.
    Map<Class<? extends DomainEvent>,
        List<PersistentEventHandler<DomainEvent>>> handlers;
    DomainEventsRepository eventsRepo;


    // Spring does not support to inject empty list, only null for optional
    // dependencies. But there can be no implementations of
    // PersistentEventHandler interface.
    // Let's use trick (hack) with optional dependency.
    @SuppressWarnings("unchecked")
    public PersistentEventsProcessor(
            final Optional<List<PersistentEventHandler<?
                    extends DomainEvent>>> handlers,
            @NonNull final DomainEventsRepository eventsRepo) {

        this.handlers = handlers
                .orElseGet(Collections::emptyList)
                .stream()
                .map(handler -> (PersistentEventHandler<DomainEvent>) handler)
                .collect(groupingBy(PersistentEventHandler::appliesTo));

        this.eventsRepo = eventsRepo;
    }


    @Async
    @Transactional
    @TransactionalEventListener
    public void handleEvent(@NonNull final DomainEvent event) {
        final List<PersistentEventHandler<DomainEvent>> eventHandlers =
                handlers.getOrDefault(event.getClass(), emptyList());

        eventHandlers.forEach(h -> h.handleEvent(event));

        eventsRepo.get(event.getId()).markProcessed();
    }
}
