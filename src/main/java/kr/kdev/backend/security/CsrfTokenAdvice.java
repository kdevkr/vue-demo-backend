package kr.kdev.backend.security;

import org.springframework.security.web.reactive.result.view.CsrfRequestDataValueProcessor;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class CsrfTokenAdvice {
	@ModelAttribute
	public Mono<CsrfToken> csrfToken(ServerWebExchange exchange) {
		// NOTE: https://docs.spring.io/spring-security/reference/reactive/exploits/csrf.html#webflux-csrf-include
		Mono<CsrfToken> csrfToken = exchange.getAttribute(CsrfToken.class.getName());
		return csrfToken.doOnSuccess(token -> exchange.getAttributes()
				.put(CsrfRequestDataValueProcessor.DEFAULT_CSRF_ATTR_NAME, token));
	}
}