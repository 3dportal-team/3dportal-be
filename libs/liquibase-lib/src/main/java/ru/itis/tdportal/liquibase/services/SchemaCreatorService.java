package ru.itis.tdportal.liquibase.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Slf4j
@RequiredArgsConstructor
public class SchemaCreatorService implements InitializingBean {

    private final String defaultSchema;
    private final DataSource dataSource;

    @Override
    public void afterPropertiesSet() {
        log.debug("SchemaCreatorService started with configs: {}", defaultSchema);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS " + defaultSchema);
    }
}
