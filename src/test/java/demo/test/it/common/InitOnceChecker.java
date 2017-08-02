package demo.test.it.common;

import org.springframework.stereotype.Service;


@Service
final class InitOnceChecker {
    private boolean isInitialized = false;

    public synchronized boolean getAndSet() {
        final boolean previousState = this.isInitialized;
        this.isInitialized = true;

        return previousState;
    }
}
