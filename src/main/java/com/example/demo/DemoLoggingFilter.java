package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class DemoLoggingFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange).transformDeferred(call -> doFilter(exchange, call));
    }

    private Publisher<Void> doFilter(ServerWebExchange exchange, Mono<Void> call) {
        long start = System.currentTimeMillis();
        return Mono.fromRunnable(() -> doBeforeRequest(exchange))
                .then(call)
                .doOnSuccess((done) -> doAfterRequest(exchange, start))
                .doOnError((throwable -> doAfterRequestWithError(exchange, start, throwable)));
    }

    private void doBeforeRequest(ServerWebExchange exchange) {
        String uri = exchange.getRequest().getURI().toString();
        log.info(String.format("[request] uri: %s", uri));
    }

    private void doAfterRequest(ServerWebExchange exchange, long start) {
        String uri = exchange.getRequest().getURI().toString();
        long end = System.currentTimeMillis();
        log.info(String.format("[response] uri: %s, processing time: %dms", uri, end - start));
    }

    private void doAfterRequestWithError(ServerWebExchange exchange, long start, Throwable throwable) {
        String uri = exchange.getRequest().getURI().toString();
        long end = System.currentTimeMillis();
        log.info(String.format("[response] uri: %s, processing time: %dms, error: %s", uri, end - start, throwable.toString()));
    }
}
