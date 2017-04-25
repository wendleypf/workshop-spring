package br.edu.ufcg.embedded.config;

import br.edu.ufcg.embedded.security.JWTAuthenticationFailureHandler;
import br.edu.ufcg.embedded.security.JWTAuthenticationSuccessHandler;
import br.edu.ufcg.embedded.security.StatelessAuthenticationFilter;
import br.edu.ufcg.embedded.security.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService) {
        super(true);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        TokenAuthenticationService tokenAuthenticationService = new TokenAuthenticationService("${api.secret}", userDetailsService);
        JWTAuthenticationSuccessHandler athenticationSuccessHandler = new JWTAuthenticationSuccessHandler(tokenAuthenticationService);
        JWTAuthenticationFailureHandler authenticationFailureHandler = new JWTAuthenticationFailureHandler();

        http.formLogin()
                .loginProcessingUrl("/login")
                    .successHandler(athenticationSuccessHandler).failureHandler(authenticationFailureHandler)
                .and()
                    .exceptionHandling()
                .and()
                    .anonymous()
                .and()
                    .authorizeRequests()
                    .antMatchers("/login").permitAll()
                .and()
                    .addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);

    }
}
