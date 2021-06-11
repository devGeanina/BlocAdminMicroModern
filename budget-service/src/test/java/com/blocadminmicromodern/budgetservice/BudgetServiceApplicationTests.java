package com.blocadminmicromodern.budgetservice;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class BudgetServiceApplicationTests {
	
	public BudgetServiceApplicationTests() {}

	@Test
	public void contextLoads() {
	}

}
