package com.simonlangbak.agendo;

import org.springframework.boot.SpringApplication;

public class TestAgendoApplication {

	public static void main(String[] args) {
		SpringApplication.from(AgendoServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
