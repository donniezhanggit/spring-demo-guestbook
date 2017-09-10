package gb.config;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@Configuration
public class MainSpringConfig {
    private final String jacksonDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @Value("${spring.jackson.serialization.INDENT_OUTPUT}")
    private boolean prettyPrint;


    @Bean
    public ObjectMapper objectMapper() {
        final ObjectMapper mapper = new ObjectMapper();

        // Serialize LocalDateTime to format available for javascript.
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat(this.jacksonDateFormat));

        // Enable pretty print.
        if(this.prettyPrint) {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
        }

        return mapper;
    }


    @Value("${spring.security.cors.api.origin}")
    private String origin;


    @Bean
    public WebMvcConfigurer corsConfigurer() {

        // CORS setup.
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                .allowedOrigins(origin)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(60 * 60);
            }
        };
    }
}
