package com.github.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping(value = "/")
public class DemoController {

    @GetMapping("heavy")
    public Mono<String> getHeavyMsg() {
        // Graceful Shutdownの挙動確認のために時間のかかる処理を再現
        return Mono.just("Heavy processing completed!!")
                .delayElement(Duration.ofSeconds(10));
    }

    @GetMapping("heavier")
    public Mono<String> getHeavierMsg() {
        // Graceful Shutdownの挙動確認のために時間のかかる処理を再現
        return Mono.just("Heavy processing completed!!")
                .delayElement(Duration.ofMinutes(2));
    }

    @GetMapping("light")
    public Mono<String> getLightMsg() {
        return Mono.just("light processing completed!!");
    }
}
