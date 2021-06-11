package com.blocadminmicromodern.webservice.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.blocadminmicromodern.webservice.dto.BudgetDTO;
import com.blocadminmicromodern.webservice.dto.ExpenseDTO;
import com.blocadminmicromodern.webservice.dto.HouseholdDTO;
import com.blocadminmicromodern.webservice.dto.RequestDTO;
import com.blocadminmicromodern.webservice.utils.ExpenseType;

@Service
public class HomeServiceImpl implements HomeService {

	@Override
	public String createBudgetSummary(List<BudgetDTO> budgets) {
		StringBuilder builder = new StringBuilder();
		if (budgets != null && !budgets.isEmpty()) {
			double totalSum = budgets.stream().mapToDouble(o -> o.getTotalSum()).sum();
			double leftoverSum = budgets.stream().mapToDouble(o -> o.getLeftoverSum()).sum();
			builder.append("Leftover sum to spend from current budget is of ").append(leftoverSum)
					.append(" from a total of ").append(totalSum).append(".");
		} else {
			builder.append("No information on the budgets found yet.");
		}
		return builder.toString();
	}

	@Override
	public String createRequestSummary(List<RequestDTO> requests) {
		StringBuilder builder = new StringBuilder();
		if (requests != null && !requests.isEmpty()) {
			List<RequestDTO> unresolvedRequests = requests.stream().filter(o -> !o.isResolved())
					.collect(Collectors.toList());
			builder.append("There are a total of ").append(requests.size()).append(" requests, out of which ")
					.append(unresolvedRequests.size()).append(" are unresolved.");
		} else {
			builder.append("No information on the requests found yet.");
		}
		return builder.toString();
	}

	@Override
	public String createHouseholdSummary(List<HouseholdDTO> households) {
		StringBuilder builder = new StringBuilder();
		if (households != null && !households.isEmpty()) {
			List<HouseholdDTO> householdsWithDebts = households.stream().filter(o -> o.getTotalDebt() > 0.0)
					.collect(Collectors.toList());
			double totalDebt = households.stream().mapToDouble(o -> o.getTotalDebt()).sum();
			builder.append("There are a total of ").append(households.size())
					.append(" households, with a total debt of ").append(totalDebt).append(" from ")
					.append(householdsWithDebts.size()).append(".");
			if (householdsWithDebts != null && !householdsWithDebts.isEmpty()) {
				builder.append(" Households with debts: ");
				List<String> householdsAddresses = new ArrayList<String>();
				for (HouseholdDTO householdDTO : householdsWithDebts) {
					householdsAddresses.add(" B: ".concat(String.valueOf(householdDTO.getBuildingNr())).concat(", Ap: ")
							.concat(String.valueOf(householdDTO.getAppartmentNr())).concat(" Owner: ")
							.concat(householdDTO.getOwnerName()));
				}
				String householdAddressesFormatted = householdsAddresses.stream().map(Object::toString)
						.collect(Collectors.joining("/"));
				builder.append(householdAddressesFormatted);
				builder.append(".");
			}
		} else {
			builder.append("No information on the households found yet.");
		}
		return builder.toString();
	}

	@Override
	public String creatExpenseSummary(List<ExpenseDTO> expenses) {
		StringBuilder builder = new StringBuilder();
		if (expenses != null && !expenses.isEmpty()) {
			List<ExpenseDTO> monthlyExpenses = expenses.stream()
					.filter(o -> o.getExpenseTypeEnum().getName().equalsIgnoreCase(ExpenseType.MONTHLY.getName()))
					.collect(Collectors.toList());
			List<ExpenseDTO> yearlyExpenses = expenses.stream()
					.filter(o -> o.getExpenseTypeEnum().getName().equalsIgnoreCase(ExpenseType.YEARLY.getName()))
					.collect(Collectors.toList());
			double leftoverSum = expenses.stream().mapToDouble(o -> o.getLeftoverSum()).sum();
			builder.append("There are a total of ").append(expenses.size())
					.append(" expenses registered, out of which ").append(monthlyExpenses.size())
					.append(" monthly and ").append(yearlyExpenses.size())
					.append(" yearly. Leftover sum to recover from tenants: ").append(leftoverSum).append(".");
		} else {
			builder.append("No information on the expenses found yet.");
		}
		return builder.toString();
	}

	@Override
	public List<ExpenseDTO> getExpensesFromLastMonth(List<ExpenseDTO> allExpenses) {
		List<ExpenseDTO> lastMonthExpenses = new ArrayList<>();

		if (!allExpenses.isEmpty()) {
			for (ExpenseDTO entityDTO : allExpenses) {
				if (!entityDTO.isPayedInFull()) {

					LocalDate expenseDueDate = Instant.ofEpochMilli(entityDTO.getDueDate().getTime())
							.atZone(ZoneId.systemDefault()).toLocalDate();

					Calendar calBefore = Calendar.getInstance();
					calBefore.add(Calendar.MONTH, -1);
					LocalDate minusAMonth = Instant.ofEpochMilli(calBefore.getTime().getTime())
							.atZone(ZoneId.systemDefault()).toLocalDate();

					Calendar calAfter = Calendar.getInstance();
					calAfter.add(Calendar.MONTH, +1);
					LocalDate plusAMonthA = Instant.ofEpochMilli(calAfter.getTime().getTime())
							.atZone(ZoneId.systemDefault()).toLocalDate();

					if (expenseDueDate.isAfter(minusAMonth) && expenseDueDate.isBefore(plusAMonthA)) {
						lastMonthExpenses.add(entityDTO);
					}
				}
			}

			if (!lastMonthExpenses.isEmpty()) {
				lastMonthExpenses.sort((o1, o2) -> o1.getDueDate().compareTo(o2.getDueDate()));
				List<ExpenseDTO> lastFiveElem = lastMonthExpenses.subList(Math.max(lastMonthExpenses.size() - 5, 0),
						lastMonthExpenses.size());
				lastMonthExpenses = lastFiveElem;
			}
		}

		return allExpenses;
	}
}
