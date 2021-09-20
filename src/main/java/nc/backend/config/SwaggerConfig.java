package nc.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Swagger Configuration.
 *
 * @see "https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api"
 * @see "https://stackoverflow.com/questions/2952196/learning-ant-path-style"
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final String TITLE = "NC backend";
    private static final String DESCRIPTION = "REST API documentation for MIPT NC backend";
    private static final String VERSION = "0.1";
    //fixme
    private static final String TERMS_OF_SERVICE = "Terms of service";
    //fixme
    private static final String LICENSE = "License of API";
    //fixme
    private static final String LICENSE_URL = "API license URL";
    private static final Contact DEVELOPER_CONTACT = new Contact("NoName NoName", "None", "NoName.NoName@phystech.edu");
    /**
     * Provides possibility to use swagger auto-integration
     * Swagger can now be reached via URL v2/api-docs
     * Swagger-UI can now be reached via URL /swagger-ui.html
     *
     * @return builder bean implementing Swagger 2 specification provided by Springfox
     * @see DocumentationPluginsManager
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());

    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                TITLE, DESCRIPTION, VERSION,
                TERMS_OF_SERVICE, DEVELOPER_CONTACT, LICENSE, LICENSE_URL,
                Collections.emptyList());
    }
}
