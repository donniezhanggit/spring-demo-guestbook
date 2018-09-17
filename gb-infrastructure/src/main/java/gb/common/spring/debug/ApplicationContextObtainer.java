package gb.common.spring.debug;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lombok.NonNull;


/**
 * ONLY FOR DEBUG PURPOSES! NEVER USE IT IN PRODUCTION CODE!
 * THINK ANOTHER ONE TIME AND REREAD THIS TEXT!
 *
 */
@Component
public final class ApplicationContextObtainer{
    private static ApplicationContext context;


    public ApplicationContextObtainer( // NOSONAR
            @NonNull final ApplicationContext context) {

        setApplicationContext(context);
    }


    public static ApplicationContext obtain() {
        return context;
    }


    private static void
    setApplicationContext(final ApplicationContext context) {
        ApplicationContextObtainer.context = context;
    }
}
