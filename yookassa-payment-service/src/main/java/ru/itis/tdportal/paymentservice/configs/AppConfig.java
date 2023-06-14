package ru.itis.tdportal.paymentservice.configs;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11Nio2Protocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.itis.tdportal.common.clients.configs.OpenFeignConfig;
import ru.itis.tdportal.core.configs.PortalCoreConfig;
import ru.itis.tdportal.liquibase.configs.LiquibaseConfig;

@Configuration
@Import({PortalCoreConfig.class, LiquibaseConfig.class, OpenFeignConfig.class})
public class AppConfig {

    @Value("${server.http.port}")
    private int httpPort;

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createStandardConnector());
        return tomcat;
    }

    private Connector createStandardConnector() {
        Connector connector = new Connector(Http11Nio2Protocol.class.getName());
        connector.setPort(httpPort);
        return connector;
    }

}
