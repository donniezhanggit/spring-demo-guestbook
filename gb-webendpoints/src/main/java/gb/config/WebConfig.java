package gb.config;

import static gb.common.config.GuestBookProfiles.DEVELOPMENT;
import static gb.common.config.GuestBookProfiles.NO_DB_INTEGRATION_TESTING;
import static gb.common.config.GuestBookProfiles.PRODUCTION;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Configuration
@Profile(value={DEVELOPMENT, NO_DB_INTEGRATION_TESTING, PRODUCTION})
public class WebConfig implements WebMvcConfigurer {
    @Value("${spring.security.cors.api.origin}")
    private String origin;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins(origin)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");

        log.debug("Configured CORS mappings");
    }
}
