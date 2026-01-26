package cat.itacademy.s05.t01.n01.blackjack_api.common.infrastructure.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {


    @GetMapping
    public Mono<ResponseEntity<Map<String, Object>>> checkHealth() {
        Map<String, Object> status = new HashMap<>();

        status.put("status", "UP");
        status.put("timestamp", LocalDateTime.now());
        status.put("service", "Blackjack API");
        status.put("version", "1.0.0");

        return Mono.just(ResponseEntity.ok(status));
    }
}
