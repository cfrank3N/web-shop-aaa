package backend2.backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling //Necessary to run scheduled tasks like fetchFromFakeStore
public class AppConfig {
}
