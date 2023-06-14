package ru.itis.tdportal.liquibase.configs;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import ru.itis.tdportal.liquibase.properties.LiquibaseProperty;
import ru.itis.tdportal.liquibase.services.SchemaCreatorService;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"ru.itis.tdportal.liquibase"})
public class LiquibaseConfig {

    @Bean
    @DependsOn("SchemaCreatorService")
    public SpringLiquibase liquibase(LiquibaseProperty liquibaseProperties, DataSource dataSource) {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setChangeLog(liquibaseProperties.getChangeLog());
        springLiquibase.setDataSource(dataSource);
        springLiquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
        return springLiquibase;
    }

    @Bean("SchemaCreatorService")
    public SchemaCreatorService schemaCreatorService(LiquibaseProperty liquibaseProperties, DataSource dataSource) {
        return new SchemaCreatorService(liquibaseProperties.getDefaultSchema(), dataSource);
    }
}
