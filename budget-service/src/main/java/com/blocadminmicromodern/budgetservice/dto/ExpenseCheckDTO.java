package com.blocadminmicromodern.budgetservice.dto;

public class ExpenseCheckDTO {

	private short expenseType;
	private double totalSum;

	public ExpenseCheckDTO() {
	}

	public double getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(double totalSum) {
		this.totalSum = totalSum;
	}

	public short getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(short expenseType) {
		this.expenseType = expenseType;
	}

	@Override
	public String toString() {
		return "ExpenseCheckDTO [expenseType=" + expenseType + ", totalSum=" + totalSum + "]";
	}
}