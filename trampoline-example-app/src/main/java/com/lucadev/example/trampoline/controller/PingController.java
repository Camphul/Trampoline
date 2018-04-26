package com.lucadev.example.trampoline.controller;

import com.lucadev.trampoline.service.time.TimeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@RestController
public class PingController {

    private TimeProvider timeProvider;

    @Autowired
    public PingController(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    @RequestMapping("/ping")
    public String ping() {
        return "Pong: " + timeProvider.unix();
    }

}
