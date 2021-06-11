package com.blocadminmicromodern.budgetservice;

import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;

import com.blocadminmicromodern.budgetservice.entity.Budget;
import com.blocadminmicromodern.budgetservice.repository.BudgetRepository;

@SpringBootApplication(exclude = { ErrorMvcAutoConfiguration.class })
@EnableKafka
public class BudgetServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(BudgetRepository repository) {
		return args -> {
			repository.save(new Budget(UUID.fromString("242da200-c509-11eb-bb1a-7fcb0f28101d"), (short) 1, 200.0, 100.0,
					"", (short) 1));
			repository.save(new Budget(UUID.fromString("9f80c2a0-c50b-11eb-8c7a-cd43982e9fe5"), (short) 2, 55.0, 0.0,
					"Salaries", (short) 2));
			repository.save(new Budget(UUID.fromString("242febf0-c509-11eb-bb1a-7fcb0f28101d"), (short) 3, 77.8, 72.3,
					"", (short) 1));
			repository.save(new Budget(UUID.fromString("9f78d360-c50b-11eb-8c7a-cd43982e9fe5"), (short) 4, 1000, 400,
					"", (short) 1));
			repository.save(new Budget(UUID.fromString("24269d20-c509-11eb-bb1a-7fcb0f28101d"), (short) 5, 200, 0.0,
					"Contributions", (short) 2));
		};
	}
}
