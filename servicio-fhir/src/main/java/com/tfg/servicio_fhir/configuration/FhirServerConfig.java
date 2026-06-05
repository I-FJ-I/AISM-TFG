package com.tfg.servicio_fhir.configuration;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.tfg.servicio_fhir.rest.FhirRestfulServer;

@Configuration
public class FhirServerConfig {

    @Bean
    public ServletRegistrationBean<FhirRestfulServer> fhirServerRegistration(FhirRestfulServer fhirServer) {
        ServletRegistrationBean<FhirRestfulServer> registration = new ServletRegistrationBean<>(fhirServer, "/fhir/*");
        registration.setLoadOnStartup(1);
        return registration;
    }
}