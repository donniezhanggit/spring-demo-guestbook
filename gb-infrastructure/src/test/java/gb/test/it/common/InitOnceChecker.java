package gb.test.it.common;

import org.springframework.stereotype.Component;


@Component
final class InitOnceChecker {
    private boolean isInitialized = false;


    public synchronized boolean getAndSetTrue() {
        final boolean previousState = isInitialized;
        isInitialized = true;

        return previousState;
    }
}
