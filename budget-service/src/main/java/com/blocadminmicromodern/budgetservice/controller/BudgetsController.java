package com.blocadminmicromodern.budgetservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blocadminmicromodern.budgetservice.dto.BudgetDTO;
import com.blocadminmicromodern.budgetservice.service.BudgetService;

@RestController("/budgets")
public class BudgetsController {

	private final BudgetService budgetService;

	public BudgetsController(BudgetService budgetService) {
		this.budgetService = budgetService;
	}

	@GetMapping
	public List<BudgetDTO> getBudgets() {
		return budgetService.getBudgets();
	}

	@GetMapping("/budgets/{uuid}")
	BudgetDTO findUser(@PathVariable UUID uuid) {
		return budgetService.getBudget(uuid);
	}

	@PostMapping("/budgets/save")
	public List<BudgetDTO> saveOrUpdateBudget(@RequestBody BudgetDTO budgetDTO) {
		budgetService.saveBudget(budgetDTO);
		return budgetService.getBudgets();
	}

	@GetMapping("/budgets/delete/{uuid}")
	public List<BudgetDTO> deleteBudget(@PathVariable UUID uuid) {
		budgetService.deleteBudget(uuid);
		return budgetService.getBudgets();
	}
}