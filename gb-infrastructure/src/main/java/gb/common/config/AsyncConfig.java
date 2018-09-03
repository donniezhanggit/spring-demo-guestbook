package gb.common.config;

import static gb.common.config.GuestBookProfiles.DEVELOPMENT;
import static gb.common.config.GuestBookProfiles.PRODUCTION;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.val;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@EnableAsync
@Configuration
@Profile(value={DEVELOPMENT, PRODUCTION})
public class AsyncConfig {
    @Bean
    public TaskExecutor taskExecutor() {
        val taskExecutor = new ThreadPoolTaskExecutor();

        log.info("Configured taskExecutor");

        return taskExecutor;
    }


    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster() {
        val eventMulticaster = new SimpleApplicationEventMulticaster();

        eventMulticaster.setTaskExecutor(taskExecutor());

        log.info("Configured eventMulticaster");

        return eventMulticaster;
    }
}
