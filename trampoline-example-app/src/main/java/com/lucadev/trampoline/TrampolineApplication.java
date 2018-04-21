package com.lucadev.trampoline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@SpringBootApplication
public class TrampolineApplication {
    @Autowired
    private TestDataImporter testDataImporter;

    public static void main(String[] args) {
        SpringApplication.run(TrampolineApplication.class, args);
    }

}
