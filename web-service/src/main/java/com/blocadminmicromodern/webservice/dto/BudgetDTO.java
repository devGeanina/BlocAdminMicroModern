package com.blocadminmicromodern.webservice.dto;

import java.io.Serializable;
import java.util.UUID;

import com.blocadminmicromodern.webservice.utils.BudgetType;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@Builder
@EqualsAndHashCode
public class BudgetDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID uuid;
	private short budgetType;
	private BudgetType budgetTypeEnum;
	private double totalSum;
	private double leftoverSum;
	private String details;

	public BudgetDTO(UUID uuid, short budgetType, BudgetType budgetTypeEnum, double totalSum, double leftoverSum,
			String details) {
		super();
		this.uuid = uuid;
		this.budgetType = budgetType;
		this.budgetTypeEnum = budgetTypeEnum;
		this.totalSum = totalSum;
		this.leftoverSum = leftoverSum;
		this.details = details;
	}

	public BudgetDTO() {
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
		setBudgetTypeEnum(BudgetType.getNameByCode(budgetType));
	}

	public BudgetType getBudgetTypeEnum() {
		return budgetTypeEnum;
	}

	public void setBudgetTypeEnum(BudgetType budgetTypeEnum) {
		this.budgetTypeEnum = budgetTypeEnum;
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
}
