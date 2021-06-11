package com.blocadminmicromodern.budgetservice.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
public class BudgetDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID uuid;
	private short budgetType;
	private double totalSum;
	private double leftoverSum;
	private String details;
	private short status;
	
	public BudgetDTO() {}
	
	public BudgetDTO(UUID uuid, short budgetType, double totalSum, double leftoverSum, String details, short status) {
		super();
		this.uuid = uuid;
		this.budgetType = budgetType;
		this.totalSum = totalSum;
		this.leftoverSum = leftoverSum;
		this.details = details;
		this.status = status;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public short getBudgetType() {
		return budgetType;
	}

	public void setBudgetType(short budgetType) {
		this.budgetType = budgetType;
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

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}
}
