package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/")
public class DemoController {

    @GetMapping("heavy")
    public Mono<String> getHeavyMsg() {
        try {
            // Graceful Shutdownの挙動確認のために時間のかかる処理を再現
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Mono.just("Heavy processing completed!!");
    }

    @GetMapping("light")
    public Mono<String> getLightMsg() {
        return Mono.just("light processing completed!!");
    }
}
