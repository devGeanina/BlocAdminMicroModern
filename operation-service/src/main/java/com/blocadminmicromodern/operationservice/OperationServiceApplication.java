package com.blocadminmicromodern.operationservice;

import java.util.Date;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;

import com.blocadminmicromodern.operationservice.entity.Expense;
import com.blocadminmicromodern.operationservice.entity.ExpenseByHousehold;
import com.blocadminmicromodern.operationservice.entity.ExpenseByHouseholdKey;
import com.blocadminmicromodern.operationservice.entity.Household;
import com.blocadminmicromodern.operationservice.entity.Request;
import com.blocadminmicromodern.operationservice.entity.RequestByHousehold;
import com.blocadminmicromodern.operationservice.entity.RequestByHouseholdKey;
import com.blocadminmicromodern.operationservice.repository.ExpenseByHouseholdRepository;
import com.blocadminmicromodern.operationservice.repository.ExpenseRepository;
import com.blocadminmicromodern.operationservice.repository.HouseholdRepository;
import com.blocadminmicromodern.operationservice.repository.RequestByHouseholdRepository;
import com.blocadminmicromodern.operationservice.repository.RequestRepository;

@SpringBootApplication(exclude = { ErrorMvcAutoConfiguration.class })
@EnableKafka
public class OperationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OperationServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(ExpenseRepository expenseRepository, RequestRepository reqRepository,
			HouseholdRepository houseRepository, RequestByHouseholdRepository requestByHouseholdRepository,
			ExpenseByHouseholdRepository expenseByHouseholdRepository) {
		return args -> {
			expenseRepository.save(new Expense(UUID.fromString("242da200-c509-11eb-bb1a-7fcb0f28101d"), (short) 1, 22.0,
					0.0, true, "", new Date()));
			expenseRepository.save(new Expense(UUID.fromString("9f80c2a0-c50b-11eb-8c7a-cd43982e9fe5"), (short) 2, 50.0,
					30.0, false, "", new Date()));
			expenseRepository.save(new Expense(UUID.fromString("2f80c2a0-c50b-11eb-8c7a-cd43982e9fe5"), (short) 3, 30.0,
					0.0, true, "", new Date()));
			expenseRepository.save(new Expense(UUID.fromString("9f78d360-c50b-11eb-8c7a-cd43982e9fe5"), (short) 4,
					100.5, 30.0, false, "", new Date()));

			reqRepository.save(new Request(UUID.fromString("242da200-c509-11eb-bb1a-7fcb0f28101d"), (short) 1,
					"Broken door", "Broken door knob.", true, new Date()));
			reqRepository.save(new Request(UUID.fromString("9f80c2a0-c50b-11eb-8c7a-cd43982e9fe5"), (short) 2,
					"Owner change", "", false, new Date()));
			reqRepository.save(new Request(UUID.fromString("242febf0-c509-11eb-bb1a-7fcb0f28101d"), (short) 3,
					"Insurance docs prep", "", true, new Date()));
			reqRepository.save(new Request(UUID.fromString("9f78d360-c50b-11eb-8c7a-cd43982e9fe5"), (short) 1,
					"Mailbox malfunction", "Doesn't close.", false, new Date()));
			reqRepository.save(new Request(UUID.fromString("24269d20-c509-11eb-bb1a-7fcb0f28101d"), (short) 2,
					"May receipt", "Make a copy of the may receipt.", true, new Date()));

			houseRepository.save(new Household(UUID.fromString("242da200-c509-11eb-bb1a-7fcb0f28101d"), 1,
					"Geanina Chiricuta", 1, "", 3, 2, 3));
			houseRepository.save(new Household(UUID.fromString("9f80c2a0-c50b-11eb-8c7a-cd43982e9fe5"), 2, "Paul Rudd",
					1, "", 1, 1, 1));
			houseRepository.save(new Household(UUID.fromString("242febf0-c509-11eb-bb1a-7fcb0f28101d"), 1,
					"Stephen Talasu", 2, "", 2, 2, 2));
			houseRepository.save(new Household(UUID.fromString("9f78d360-c50b-11eb-8c7a-cd43982e9fe5"), 2, "John Bush",
					2, "", 1, 2, 1));
			houseRepository.save(new Household(UUID.fromString("24269d20-c509-11eb-bb1a-7fcb0f28101d"), 1,
					"Alan Michel", 3, "", 2, 2, 1));

			expenseByHouseholdRepository.insert(new ExpenseByHousehold(
					new ExpenseByHouseholdKey(UUID.fromString("fca3d425-7bdb-41d0-85ea-c667ecca1076"),
							UUID.fromString("69f5e394-b9ef-4284-9432-9fb05d58fbd8")),
					1, 1, "Geanina Chiricuta", 200.0, 300.0, false));
			expenseByHouseholdRepository.insert(new ExpenseByHousehold(
					new ExpenseByHouseholdKey(UUID.fromString("9f80c2a0-c50b-11eb-8c7a-cd43982e9fe5"),
							UUID.fromString("9f78d360-c50b-11eb-8c7a-cd43982e9fe5")),
					1, 2, "John Smith", 100.0, 50.0, true));
			expenseByHouseholdRepository.insert(new ExpenseByHousehold(
					new ExpenseByHouseholdKey(UUID.fromString("242febf0-c509-11eb-bb1a-7fcb0f28101d"),
							UUID.fromString("2f80c2a0-c50b-11eb-8c7a-cd43982e9fe5")),
					2, 3, "Alan Byrd", 400.0, 100.0, false));
			expenseByHouseholdRepository.insert(new ExpenseByHousehold(
					new ExpenseByHouseholdKey(UUID.fromString("0102cd11-e9b8-4c9e-b405-f3c11341c5fd"),
							UUID.fromString("88703282-c4b5-476d-9723-6ea29d0c4e09")),
					5, 1, "George Constanza", 100.0, 50.0, true));

			requestByHouseholdRepository.insert(new RequestByHousehold(
					new RequestByHouseholdKey(UUID.fromString("9f78d360-c50b-11eb-8c7a-cd43982e9fe5"),
							UUID.fromString("24269d20-c509-11eb-bb1a-7fcb0f28101d")),
					"Stair repair", "B.2,Ap.1", (short) 1, false, new Date()));
		};
	}
}
