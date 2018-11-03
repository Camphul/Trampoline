package com.lucadev.trampoline.indev;

import com.lucadev.trampoline.EnableTrampoline;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3-11-18
 */
@EnableTrampoline
@SpringBootApplication
public class IntegrationTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntegrationTestApplication.class, args);
    }

}
