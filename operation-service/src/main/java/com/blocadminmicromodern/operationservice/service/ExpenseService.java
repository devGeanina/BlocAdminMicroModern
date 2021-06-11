package com.blocadminmicromodern.operationservice.service;

import java.util.List;
import java.util.UUID;

import com.blocadminmicromodern.operationservice.dto.ExpenseDTO;

public interface ExpenseService {

	public abstract List<ExpenseDTO> getExpenses();

	public abstract void saveExpense(ExpenseDTO expenseDTO);

	public abstract void deleteExpense(UUID uuid);

	public abstract ExpenseDTO getExpense(UUID uuid);
}
