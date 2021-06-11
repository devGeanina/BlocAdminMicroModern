package com.blocadminmicromodern.operationservice.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Builder
public class ExpenseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID uuid;
	private short expenseType;
	private double totalSum;
	private double leftoverSum;
	private boolean payedInFull;
	private String details;
	private Date dueDate;
	private List<UUID> householdIds;
	private List<String> householdsAddresses;

	public ExpenseDTO() {
	}

	public ExpenseDTO(UUID uuid, short expenseType, double totalSum, double leftoverSum, boolean payedInFull,
			String details, Date dueDate, List<UUID> householdIds, List<String> householdsAddresses) {
		super();
		this.uuid = uuid;
		this.expenseType = expenseType;
		this.totalSum = totalSum;
		this.leftoverSum = leftoverSum;
		this.payedInFull = payedInFull;
		this.details = details;
		this.dueDate = dueDate;
		this.householdIds = householdIds;
		this.householdsAddresses = householdsAddresses;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public double getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(double totalSum) {
		this.totalSum = totalSum;
	}

	public double getLeftoverSum() {
		return leftoverSum;
	}

	public void setLeftoverSum(double leftoverSum) {
		this.leftoverSum = leftoverSum;
	}

	public boolean isPayedInFull() {
		return payedInFull;
	}

	public void setPayedInFull(boolean payedInFull) {
		this.payedInFull = payedInFull;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public List<UUID> getHouseholdIds() {
		return householdIds;
	}

	public void setHouseholdIds(List<UUID> householdIds) {
		this.householdIds = householdIds;
	}

	public List<String> getHouseholdsAddresses() {
		return householdsAddresses;
	}

	public void setHouseholdsAddresses(List<String> householdsAddresses) {
		this.householdsAddresses = householdsAddresses;
	}

	public short getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(short expenseType) {
		this.expenseType = expenseType;
	}
}
