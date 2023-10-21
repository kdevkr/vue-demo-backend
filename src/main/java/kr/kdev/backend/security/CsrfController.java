package kr.kdev.backend.security;

import lombok.AllArgsConstructor;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
public class CsrfController {

    private final ServerCsrfTokenRepository csrfTokenRepository;

    @GetMapping("/csrf")
    public Mono<CsrfToken> csrfToken(ServerWebExchange exchange, CsrfToken csrfToken) {
        return this.csrfTokenRepository.loadToken(exchange)
                .switchIfEmpty(generateToken(exchange));
    }

    private Mono<CsrfToken> generateToken(ServerWebExchange exchange) {
        return this.csrfTokenRepository.generateToken(exchange)
                .doOnSuccess(csrfToken -> saveToken(exchange, csrfToken));
    }

    private void saveToken(ServerWebExchange exchange, CsrfToken csrfToken) {
        this.csrfTokenRepository.saveToken(exchange, csrfToken).block();
    }
}
