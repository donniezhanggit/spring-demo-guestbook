package gb.common.it;

import static lombok.AccessLevel.PRIVATE;

import org.springframework.stereotype.Component;

import lombok.experimental.FieldDefaults;


@Component
@FieldDefaults(level=PRIVATE)
final class InitOnceChecker {
    boolean isInitialized = false;


    public boolean getAndSetTrue() {
        final boolean previousState = isInitialized;

        isInitialized = true;

        return previousState;
    }
}
