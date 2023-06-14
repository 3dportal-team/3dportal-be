package ru.itis.tdportal.common.clients.configs;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
// TODO: добавить Eureka
@ComponentScan("ru.itis.tdportal.common.clients")
@EnableFeignClients(basePackages = {"ru.itis.tdportal.common.clients.feign"})
public class OpenFeignConfig {
}
