package com.blocadminmicromodern.webservice;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class WebServiceApplicationTests {
	public WebServiceApplicationTests() {}
	
	@Test
	public void contextLoads() {
	}

}
