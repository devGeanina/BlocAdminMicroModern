package com.blocadminmicromodern.userservice;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class UserServiceApplicationTests {
	
	public UserServiceApplicationTests () {}
	@Test
	public void contextLoads() {
	}

}
