package kr.kdev.backend.session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSession;
import org.springframework.session.ReactiveMapSessionRepository;
import org.springframework.session.ReactiveSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.config.annotation.web.server.EnableSpringWebSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@EnableSpringWebSession
@Configuration(proxyBeanMethods = false)
public class SessionConfig {
    private final Map<String, Session> store = new ConcurrentHashMap<>();

    @Bean
    public ReactiveSessionRepository<MapSession> sessionRepository() {
        return new ReactiveMapSessionRepository(store);
    }
}
