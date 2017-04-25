package br.edu.ufcg.embedded.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private TokenAuthenticationService tokenService;

    public JWTAuthenticationSuccessHandler(TokenAuthenticationService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        tokenService.addAuthentication(response, authentication);
    }
}
