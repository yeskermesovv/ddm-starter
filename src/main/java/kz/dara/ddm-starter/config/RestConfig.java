package kz.dara.config;

import kz.dara.service.CatalogService;
import kz.dara.service.impl.CatalogServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Bean
    public CatalogService catalogService() {
        return new CatalogServiceImpl(new RestTemplate());
    }
}
