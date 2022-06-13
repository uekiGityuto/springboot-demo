package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/")
public class HelloController {

    @GetMapping("hello")
    public Mono<String> get() {
        try {
            // Graceful Shutdownの挙動確認のために時間のかかる処理を再現
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Mono.just("Hello!!");
    }
}
