package demo.test.common;

import org.springframework.stereotype.Service;


@Service
final class DataInitializationChecker {
    private boolean isInitialized = false;
    
    public boolean getAndSet() {
        final boolean previousState = this.isInitialized;
        this.isInitialized = true;
        
        return previousState;
    }
}