package gb.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

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


@Configuration
@EnableSwagger2
@Profile(GuestBookProfiles.DEVELOPMENT)
public class SwaggerConfig {
    private static final Logger LOG = LoggerFactory
            .getLogger(SwaggerConfig.class);


    /** The title for the spring boot service to be displayed on swagger UI.  */
    @Value("${swagger.title:spring.application.name:app_title}")
    private String title;

    /** The description of the spring boot service. */
    @Value("${swagger.description:spring.application.description:app_description}")
    private String description;

    /** The version of the service. */
    @Value("${swagger.version:spring.application.version:versionxxx}")
    private String version;

    /** The terms of service url for the service if applicable. */
    @Value("${swagger.termsOfServiceUrl:terms_of_service_url:}")
    private String termsOfServiceUrl;

    /** The contact name for the service. */
    @Value("${swagger.contact.name:contact_name}")
    private String contactName;

    /** The contact url for the service. */
    @Value("${swagger.contact.url:contact_url}")
    private String contactURL;

    /** The contact email for the service. */
    @Value("${swagger.contact.email:email_address}")
    private String contactEmail;

    /** The license for the service if applicable. */
    @Value("${swagger.license:license_body}")
    private String license;

    /** The license url for the service if applicable. */
    @Value("${swagger.licenseUrl:client_licenseUrl}")
    private String licenseURL;

    private List<Parameter> listDocketParameters = new ArrayList<Parameter>();


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
                .title(this.title)
                .description(this.description)
                .contact(new Contact(this.contactName, this.contactURL, this.contactEmail))
                .license(this.license)
                .licenseUrl(this.licenseURL)
                .version(this.version)
                .build();
    }


    @Bean
    public Docket guestBookApi() {
        final Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(this.listDocketParameters)
                .groupName("guestBookApi")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/api/.*"))
                .build()
                .apiInfo(this.apiEndPointsInfo());

        LOG.info(String.format("Configured SWAGGER Group: [%s]", docket.getGroupName()));

        return docket;
    }
}
