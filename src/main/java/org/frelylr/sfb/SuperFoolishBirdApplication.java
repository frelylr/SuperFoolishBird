package org.frelylr.sfb;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SuperFoolishBirdApplication {

    /**
     * SuperFoolishBirdApplication
     */
    public static void main(String[] args) {
        SpringApplication.run(SuperFoolishBirdApplication.class, args);
    }

    /**
     * set http url auto change to https
     */
    @Bean
    public ServletWebServerFactory servletContainer() {

        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createStandardConnector());

        return tomcat;
    }

    private Connector createStandardConnector() {

        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(8080);

        return connector;
    }
}
