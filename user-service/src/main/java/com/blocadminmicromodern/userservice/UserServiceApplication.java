package com.blocadminmicromodern.userservice;

import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.blocadminmicromodern.userservice.entity.User;
import com.blocadminmicromodern.userservice.repository.UserRepository;

@SpringBootApplication(exclude = { ErrorMvcAutoConfiguration.class })
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(UserRepository repository) {
		return args -> {
			repository.save(new User(UUID.fromString("242da200-c509-11eb-bb1a-7fcb0f28101d"), "geanina_c", "passC",
					"Geanina", "Chiricuta", (short) 2, 1, "", 1));
			repository.save(new User(UUID.fromString("9f80c2a0-c50b-11eb-8c7a-cd43982e9fe5"), "paul_r", "passP", "Paul",
					"Rudd", (short) 2, 2, "", 1));
			repository.save(new User(UUID.fromString("242febf0-c509-11eb-bb1a-7fcb0f28101d"), "stephen_t", "passS",
					"Stephen", "Talasu", (short) 3, 1, "", 2));
			repository.save(new User(UUID.fromString("9f78d360-c50b-11eb-8c7a-cd43982e9fe5"), "john_b", "passB", "John",
					"Bush", (short) 4, 2, "", 2));
			repository.save(new User(UUID.fromString("24269d20-c509-11eb-bb1a-7fcb0f28101d"), "alan_m", "passM", "Alan",
					"Michel", (short) 2, 1, "", 3));
		};
	}
}
