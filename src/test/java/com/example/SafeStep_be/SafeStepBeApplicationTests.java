package com.example.SafeStep_be;

import com.example.SafeStep_be.config.DotenvTestInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = DotenvTestInitializer.class)
class SafeStepBeApplicationTests {

	@Test
	void contextLoads() {
	}

}
