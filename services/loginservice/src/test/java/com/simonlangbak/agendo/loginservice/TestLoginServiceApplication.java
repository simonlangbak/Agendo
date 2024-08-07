package com.simonlangbak.agendo.loginservice;

import org.springframework.boot.SpringApplication;

public class TestLoginServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(LoginServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
