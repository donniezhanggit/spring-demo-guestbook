package gb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${spring.security.cors.api.origin}")
    private String origin;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins(this.origin)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");

        log.debug("Configured CORS mappings");
    }
}
