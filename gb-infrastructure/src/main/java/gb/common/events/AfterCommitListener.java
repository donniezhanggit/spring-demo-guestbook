package gb.common.events;

import static lombok.AccessLevel.PRIVATE;

import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;


@Service
@AllArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
public class AfterCommitListener {
    @NonNull EventDelegator delegator;


    @TransactionalEventListener
    public void passAnyEvent(final Object event) {
        delegator.delegate(event);
    }
}
