package ru.itis.tdportal.mainservice.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.itis.tdportal.core.configs.PortalCoreConfig;
import ru.itis.tdportal.liquibase.configs.LiquibaseConfig;

@Configuration
@ComponentScan(basePackages = "ru.itis.tdportal.payment.*")
@Import({PortalCoreConfig.class, LiquibaseConfig.class})
public class AppConfig {

}
