package gb.common.events;

import static lombok.AccessLevel.PRIVATE;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.experimental.FieldDefaults;


@Service
@FieldDefaults(level=PRIVATE, makeFinal=true)
public class AfterCommitListener {
    EventDelegator delegator;


    public AfterCommitListener(@NonNull EventDelegator delegator) {
        this.delegator = delegator;
    }


    @EventListener
    public void passAnyEvent(final DomainEvent event) {
        delegator.delegate(event);
    }
}
