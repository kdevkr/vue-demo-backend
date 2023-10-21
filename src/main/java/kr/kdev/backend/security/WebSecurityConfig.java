package kr.kdev.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.WebSessionServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.XorServerCsrfTokenRequestAttributeHandler;

@EnableWebFluxSecurity
@Configuration
public class WebSecurityConfig {

    @Bean
    public ServerCsrfTokenRepository csrfTokenRepository() {
        return new WebSessionServerCsrfTokenRepository();
    }

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        // NOTE: Protection Against Exploits
        http.csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository())
                .csrfTokenRequestHandler(new XorServerCsrfTokenRequestAttributeHandler()));

        http.securityContextRepository(new WebSessionServerSecurityContextRepository());

        http.authorizeExchange(exchanges ->
                exchanges.pathMatchers(HttpMethod.GET, "/csrf").permitAll()
                        .pathMatchers("/ws/**").permitAll()
                        .anyExchange().authenticated());
        return http.build();
    }
}
