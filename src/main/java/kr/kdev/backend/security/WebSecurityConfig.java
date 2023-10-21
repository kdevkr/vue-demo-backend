package kr.kdev.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return new HttpSessionCsrfTokenRepository();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // NOTE: Protection Against Exploits
        http.csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository())
                .csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler()));

        http.securityContext(sc -> sc.requireExplicitSave(false));

        http.authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.GET, "/csrf").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .anyRequest().authenticated());
        return http.build();
    }
}
