package com.tfg.servicio_fhir.rest.auth;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final String SECRET_KEY = "ClaveSecretaSuperSeguraParaElTfgDeAism2026";

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credenciales) {
        String user = credenciales.get("username");
        String pass = credenciales.get("password");

        if ("admin".equals(user) && "admin".equals(pass)) {
            String jwt = Jwts.builder()
                    .setSubject(user)
                    .setIssuedAt(Date.valueOf(LocalDate.now()))
                    .setExpiration(new Date(System.currentTimeMillis() + 3600000)) 
                    .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .compact();
            
            return ResponseEntity.ok(jwt);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }
}