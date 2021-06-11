package com.blocadminmicromodern.operationservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blocadminmicromodern.operationservice.component.KafkaExpenseSender;
import com.blocadminmicromodern.operationservice.dto.ExpenseCheckDTO;
import com.blocadminmicromodern.operationservice.dto.ExpenseDTO;
import com.blocadminmicromodern.operationservice.service.ExpenseService;

@RestController
public class ExpensesController {

	private final ExpenseService expenseService;
	private final KafkaExpenseSender kafkaExpenseSender;

	@Autowired
	public ExpensesController(ExpenseService expenseService, KafkaExpenseSender kafkaExpenseSender) {
		this.expenseService = expenseService;
		this.kafkaExpenseSender = kafkaExpenseSender;
	}

	@GetMapping("/expenses")
	public List<ExpenseDTO> getExpenses() {
		return expenseService.getExpenses();
	}

	@GetMapping("/expenses/{uuid}")
	ExpenseDTO findExpense(@PathVariable UUID uuid) {
		return expenseService.getExpense(uuid);
	}

	@EventListener
	@PostMapping("/expenses/save")
	public List<ExpenseDTO> saveOrUpdateBudget(@RequestBody ExpenseDTO expenseDTO) {
		
		ExpenseCheckDTO expenseCheckDTO = new ExpenseCheckDTO();
		expenseCheckDTO.setTotalSum(expenseDTO.getTotalSum());
		expenseCheckDTO.setExpenseType(expenseDTO.getExpenseType());
		
		kafkaExpenseSender.sendMessage(expenseCheckDTO); //send new expense update over Kafka
		expenseService.saveExpense(expenseDTO);
		
		return expenseService.getExpenses();
	}

	@GetMapping("/expenses/delete/{uuid}")
	public List<ExpenseDTO> deleteExpense(@PathVariable UUID uuid) {
		expenseService.deleteExpense(uuid);
		return expenseService.getExpenses();
	}
}