package com.simonlangbak.agendo.loginservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class LoginServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
