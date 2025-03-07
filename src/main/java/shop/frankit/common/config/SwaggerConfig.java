package shop.frankit.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.Set;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .version("v0.0.1")
                .title("Frankit Task 김대현 API")
                .description("백엔드 개발자 김대현 과제");

        Server server = new Server();
        server.setUrl("http://localhost:8080");
        //server.setUrl("https://frankit.tonglink.site");

        return new OpenAPI()
                .info(info)
                .servers(Collections.singletonList(server));
    }

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2) // 최신 버전에서는 SWAGGER_2 사용
                .consumes(getConsumeContentTypes()) // 요청 타입 설정
                .produces(getProduceContentTypes()) // 응답 타입 설정
                .apiInfo(swaggerInfo()) // Swagger 정보 설정
                .select()
                .apis(RequestHandlerSelectors.basePackage("shop.frankit.domain")) // Swagger 적용할 패키지 설정
                .paths(PathSelectors.any()) // 모든 경로에 대해 적용
                .build()
                .useDefaultResponseMessages(false); // 기본 응답 메시지 비활성화
    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder()
                .title("프로젝트 API Specification")
                .description("프로젝트 관련 API 설명서 입니다.")
                .version("1.0.0")
                .build();
    }

    private Set<String> getConsumeContentTypes() {
        return Collections.singleton("multipart/form-data");
    }

    private Set<String> getProduceContentTypes() {
        return Collections.singleton("application/json;charset=UTF-8");
    }
}
