package gb.common.events;

import static lombok.AccessLevel.PRIVATE;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.NonNull;
import lombok.val;
import lombok.experimental.FieldDefaults;


@Service
@FieldDefaults(level=PRIVATE, makeFinal=true)
public class PersistentEventsProcessor {
    Map<Class<DomainEvent>, PersistentEventHandler<DomainEvent>> handlers;
    DomainEventsRepository eventsRepo;
    PersistentEventHandler<DomainEvent> nullHandler;


    public PersistentEventsProcessor(
            final Optional<List<PersistentEventHandler<DomainEvent>>> handlers,
            @NonNull final DomainEventsRepository eventsRepo) {

        this.handlers = handlers
                .orElseGet(Collections::emptyList)
                .stream().collect(Collectors.toMap(
                        PersistentEventHandler::appliesTo,
                        handler -> handler));

        this.eventsRepo = eventsRepo;
        this.nullHandler = new NullEventHandler();
    }


    @Async
    @Transactional
    @TransactionalEventListener
    public void handleEvent(@NonNull final DomainEvent event) {
        val handler = handlers.getOrDefault(event.getClass(), nullHandler);

        handler.handleEvent(event);

        eventsRepo.get(event.getId()).markProcessed();
    }
}
