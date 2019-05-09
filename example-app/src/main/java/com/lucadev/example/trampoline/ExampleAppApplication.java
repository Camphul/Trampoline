package com.lucadev.example.trampoline;

import com.lucadev.trampoline.EnableTrampoline;
import com.lucadev.trampoline.security.logging.EnableUserActivityLogging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableTrampoline
@EnableUserActivityLogging
public class ExampleAppApplication {

	/*
	 * Autowire to run the importer
	 */
	@Autowired
	private DummyUserImporter dummyUserImporter;

	public static void main(String[] args) {
		SpringApplication.run(ExampleAppApplication.class, args);
	}

}
