package gb.common.events;

import static lombok.AccessLevel.PRIVATE;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.val;
import lombok.experimental.FieldDefaults;


@Service
@AllArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
class EventPublisherImpl implements EventPublisher {
    @NonNull ApplicationEventPublisher publisher;


    @Override
    public void raise(@NonNull final Object event) {
        val adapter = new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                publisher.publishEvent(event);
            }
        };

        TransactionSynchronizationManager.registerSynchronization(adapter);
    }
}
