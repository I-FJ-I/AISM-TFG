package com.tfg.servicio_fhir.client;

import java.util.Optional;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

@Component
public class ExternalTranslatedClient implements ExternalClient {

    private final RestTemplate restTemplate;
    private final FhirContext fhirContext;
    
    @Value("${translator.url}")
    private String fastApiBaseUrl;

    @Autowired
    public ExternalTranslatedClient(FhirContext fhirContext) {
        this.restTemplate = new RestTemplate();
        this.fhirContext = fhirContext; 
    }

    @Override
    public <T extends IBaseResource> Optional<T> fetchResource(String resourceType, String id, Class<T> targetClass) {
        
        String url = String.format("%s/%s/%s", fastApiBaseUrl, resourceType, id);

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                IParser parser = fhirContext.newJsonParser();
                
                T resource = parser.parseResource(targetClass, response.getBody());
                return Optional.of(resource);
            }

        } catch (HttpClientErrorException.NotFound e) {
            System.out.println("Resource " + resourceType + " with ID " + id + " not found in the Python service.");
            return Optional.empty();
            
        } catch (Exception e) {
            System.err.println("Connection error with FastAPI: " + e.getMessage());
            return Optional.empty();
        }

        return Optional.empty();
    }
}