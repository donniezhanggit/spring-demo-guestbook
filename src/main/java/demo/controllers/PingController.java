package demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/ping")
public class PingController {
    private static final String PONG = "[{\"data\":\"pong\"}]";


    @GetMapping(produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> pong() {
        return ResponseEntity.ok(PONG);
    }
}
