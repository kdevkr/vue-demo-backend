package kr.kdev.backend.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;

@EnableWebSocketSecurity
@Configuration
public class WebSocketSecurityConfig {
    @Bean
    public AuthorizationManager<Message<?>> messageAuthorizationManager(
            MessageMatcherDelegatingAuthorizationManager.Builder messages) {
        // NOTE: Only require csrf token.
        messages.nullDestMatcher().permitAll()
                .simpSubscribeDestMatchers("/user/queue/errors").permitAll()
                .simpDestMatchers("/app").authenticated()
                .simpSubscribeDestMatchers("/topic/**", "/queue/**").permitAll()
                .anyMessage().denyAll();
        return messages.build();
    }
}
