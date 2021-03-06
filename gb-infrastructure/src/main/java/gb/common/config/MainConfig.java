package gb.common.config;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import gb.common.jackson.StringTrimmer;
import lombok.val;
import lombok.extern.log4j.Log4j2;


@Log4j2
@Configuration
@EnableCaching
public class MainConfig {
    @Bean
    @Primary
    public ObjectMapper objectMapper(
            @Value("${spring.jackson.serialization.INDENT_OUTPUT:false}")
            final boolean prettyPrint) {
        val mapper = new ObjectMapper();

        // Trim leading/trailing white spaces of string inputs.
        // We don't want to write tests for this every time.
        mapper.registerModule(new StringTrimmer());

        // Show date/time as ISO8601 by default.
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mapper.configure(SerializationFeature.INDENT_OUTPUT, prettyPrint);

        // Avoid having to annotate classes. Requires Java 8, pass -parameters
        // to javac and jackson-module-parameter-names as a dependency.
        mapper.registerModule(new ParameterNamesModule(PROPERTIES));

        // Make private fields of classes visible to Jackson.
        mapper.setVisibility(FIELD, ANY);

        // Ignore unknown fields.
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        log.info("Configuring of global objectMapper finished");

        return mapper;
    }


    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        val processor = new MethodValidationPostProcessor();

        processor.setBeforeExistingAdvisors(true);

        return processor;
    }


    @Bean
    @Qualifier("conversionService")
    public ConversionService conversionService() {
        val bean = new ConversionServiceFactoryBean();

        bean.afterPropertiesSet();

        return bean.getObject();
    }
}
