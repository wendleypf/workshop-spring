package br.edu.ufcg.embedded.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenManager {
    public static final String CHAVE_CRIPTOGRAFIA = "chaveAleatoria";

    public static String getToken(String subject){
        String token = Jwts.builder()
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, CHAVE_CRIPTOGRAFIA)
                .compact();
        return token;
    }
}
