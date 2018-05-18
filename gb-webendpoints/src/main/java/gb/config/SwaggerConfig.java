package gb.config;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.google.common.collect.Lists;

import gb.common.config.GuestBookProfiles;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Slf4j
@FieldDefaults(level=PRIVATE)
@Configuration
@EnableSwagger2
@Profile(GuestBookProfiles.DEVELOPMENT)
public class SwaggerConfig {
    /** The title for the spring boot service to be displayed on swagger UI.  */
    @Value("${swagger.title:spring.application.name:app_title}")
    String title;

    /** The description of the spring boot service. */
    @Value("${swagger.description:spring.application.description:app_description}")
    String description;

    /** The version of the service. */
    @Value("${swagger.version:spring.application.version:versionxxx}")
    String version;

    /** The terms of service url for the service if applicable. */
    @Value("${swagger.termsOfServiceUrl:terms_of_service_url:}")
    String termsOfServiceUrl;

    /** The contact name for the service. */
    @Value("${swagger.contact.name:contact_name}")
    String contactName;

    /** The contact url for the service. */
    @Value("${swagger.contact.url:contact_url}")
    String contactURL;

    /** The contact email for the service. */
    @Value("${swagger.contact.email:email_address}")
    String contactEmail;

    /** The license for the service if applicable. */
    @Value("${swagger.license:license_body}")
    String license;

    /** The license url for the service if applicable. */
    @Value("${swagger.licenseUrl:client_licenseUrl}")
    String licenseURL;

    List<Parameter> listDocketParameters = Lists.newArrayList();


    public SwaggerConfig() {
        // Any parameter or header you want to require for all endpoints.
        Parameter oAuthHeader = new ParameterBuilder()
                .name("Authorization")
                .description("OAUTH JWT Bearer Token")
                .defaultValue("Bearer YourJWTTokenHere")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true)
                .build();

        this.listDocketParameters.add(oAuthHeader);
    }


    /**
     * This method will return the API info object to swagger which will in turn
     * display the information on the swagger UI.
     *
     * See the active application.properties file for adjusting attributes.
     *
     * @return the API information object
     */
    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .contact(new Contact(contactName, contactURL, contactEmail))
                .license(license)
                .licenseUrl(licenseURL)
                .version(version)
                .build();
    }


    @Bean
    public Docket guestBookApi() {
        final Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(listDocketParameters)
                .groupName("guestBookApi")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/api/.*"))
                .build()
                .apiInfo(apiEndPointsInfo());

        log.debug("Configured SWAGGER Group: [{}]", docket.getGroupName());

        return docket;
    }
}
