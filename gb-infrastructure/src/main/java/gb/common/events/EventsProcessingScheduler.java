package gb.common.events;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
class EventsProcessingScheduler {
    @NonNull PersistentEventsProcessor processor;
    @NonNull DomainEventsRepository eventsRepo;


    @Scheduled(fixedDelay=3*60*1000)
    public void processUnhandeledEvents() {
        log.info("Started processing pending events.");

        final List<DomainEvent> pendingEvents = eventsRepo.findPendingEvents();

        log.info("Pending events: {}", pendingEvents);

        pendingEvents.forEach(processor::handleEvent);

        log.info("Finished processing pending events.");
    }
}
