package com.blocadminmicromodern.operationservice;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class OperationServiceApplicationTests {
	
	public OperationServiceApplicationTests() {}

	@Test
	public void contextLoads() {
	}

}
