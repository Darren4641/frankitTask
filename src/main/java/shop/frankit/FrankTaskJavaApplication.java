package shop.frankit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import shop.frankit.common.properties.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class FrankTaskJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrankTaskJavaApplication.class, args);
    }

}
