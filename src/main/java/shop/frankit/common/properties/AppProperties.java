package shop.frankit.common.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;


@Slf4j
@Validated
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final Auth auth = new Auth(); // 기본 생성

    @Getter
    @Setter
    public static class Auth {
        private String tokenSecret;
        private long tokenExpiry;
    }
}
