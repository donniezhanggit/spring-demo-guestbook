package gb.common.config;

import static gb.common.config.GuestBookProfiles.DEVELOPMENT;
import static gb.common.config.GuestBookProfiles.PRODUCTION;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.val;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@EnableAsync
@EnableScheduling
@Configuration
@Profile(value={DEVELOPMENT, PRODUCTION})
public class AsyncConfig {
    @Bean
    public TaskExecutor taskExecutor() {
        val taskExecutor = new ThreadPoolTaskExecutor();

        log.info("Configured taskExecutor");

        return taskExecutor;
    }
}
