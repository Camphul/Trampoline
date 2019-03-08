package com.lucadev.example.trampoline;

import com.lucadev.trampoline.EnableTrampoline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Your regular main class for Spring
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@EnableTrampoline /* Required to load necessary Trampoline components */
@SpringBootApplication
public class TrampolineApplication {

    /*
     * Added to automatically import some dummy users into the project.
     */
    @Autowired
    private DummyUserImporter dummyUserImporter;

    public static void main(String[] args) {
        SpringApplication.run(TrampolineApplication.class, args);
    }

}
