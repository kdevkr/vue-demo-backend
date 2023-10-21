package kr.kdev.backend.session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.*;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@EnableSpringHttpSession
@Configuration(proxyBeanMethods = false)
public class SessionConfig {
    private final Map<String, Session> store = new ConcurrentHashMap<>();

    @Bean
    public SessionRepository<MapSession> sessionRepository() {
        return new MapSessionRepository(store);
    }
}
