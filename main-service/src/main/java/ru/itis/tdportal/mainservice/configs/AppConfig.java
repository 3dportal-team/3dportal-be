package ru.itis.tdportal.mainservice.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.itis.tdportal.common.clients.configs.OpenFeignConfig;
import ru.itis.tdportal.core.configs.PortalCoreConfig;
import ru.itis.tdportal.liquibase.configs.LiquibaseConfig;

@Configuration
@Import({PortalCoreConfig.class, LiquibaseConfig.class, OpenFeignConfig.class})
public class AppConfig {

}
