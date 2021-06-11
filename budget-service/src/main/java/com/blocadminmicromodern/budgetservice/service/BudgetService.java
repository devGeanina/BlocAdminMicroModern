package com.blocadminmicromodern.budgetservice.service;

import java.util.List;
import java.util.UUID;

import com.blocadminmicromodern.budgetservice.dto.BudgetDTO;

public interface BudgetService {

	public abstract List<BudgetDTO> getBudgets();

	public abstract void saveBudget(BudgetDTO budgetDTO);

	public abstract void deleteBudget(UUID budgetUuid);

	public abstract BudgetDTO getBudget(UUID budgetUuid);

	public abstract void updateBudget(short type, double sum);
}
