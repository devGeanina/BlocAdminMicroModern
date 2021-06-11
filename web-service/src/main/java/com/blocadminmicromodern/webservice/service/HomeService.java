package com.blocadminmicromodern.webservice.service;

import java.util.List;

import com.blocadminmicromodern.webservice.dto.BudgetDTO;
import com.blocadminmicromodern.webservice.dto.ExpenseDTO;
import com.blocadminmicromodern.webservice.dto.HouseholdDTO;
import com.blocadminmicromodern.webservice.dto.RequestDTO;

public interface HomeService {

	public abstract String createBudgetSummary(List<BudgetDTO> budgets);

	public abstract String createRequestSummary(List<RequestDTO> requests);

	public abstract String createHouseholdSummary(List<HouseholdDTO> houses);

	public abstract String creatExpenseSummary(List<ExpenseDTO> expenses);

	public abstract List<ExpenseDTO> getExpensesFromLastMonth(List<ExpenseDTO> allExpenses);
}
