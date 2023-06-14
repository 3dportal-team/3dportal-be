package ru.itis.tdportal.core.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"ru.itis.tdportal.core", "ru.itis.tdportal.common.models"})
public class PortalCoreConfig {
}
