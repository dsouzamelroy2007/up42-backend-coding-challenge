import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.Collections

@Configuration
@EnableSwagger2
class SwaggerConfiguration {

    @Bean
    open fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.up42.codingchallenge."))
        .paths(PathSelectors.any())
        .build()
        .pathMapping("")
        .apiInfo(apiInfo())

    fun apiInfo(): ApiInfo? {
        return ApiInfo(
            "Feature Service",
            "Gives details of features that have satellite metadata.",
            "1.0",
            "Terms of service",
            Contact("Dev Engineer", "www.example.com", "dev@company.com"),
            "License of API",
            "API license URL", Collections.emptyList()
        )
    }
}
