package com.tfg.servicio_fhir.rest.auth;

import ca.uhn.fhir.interceptor.api.Hook;
import ca.uhn.fhir.interceptor.api.Interceptor;
import ca.uhn.fhir.interceptor.api.Pointcut;
import ca.uhn.fhir.rest.api.RequestTypeEnum;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.exceptions.AuthenticationException;
import io.jsonwebtoken.Jwts;

@Interceptor
public class JwtAuthInterceptor {

    private final String SECRET_KEY = "ClaveSecretaSuperSeguraParaElTfgDeAism2026";

    @Hook(Pointcut.SERVER_INCOMING_REQUEST_PRE_HANDLED)
    public void validarToken(RequestDetails requestDetails) {
    	if (RequestTypeEnum.OPTIONS.equals(requestDetails.getRequestType())) {
            return; 
        }
    	
        String authHeader = requestDetails.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AuthenticationException("Falta el token de autorización");
        }

        String token = authHeader.substring(7);

        try {
            Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token);
        } catch (Exception e) {
            throw new AuthenticationException("Token inválido o expirado");
        }
    }
}