package com.blocadminmicromodern.operationservice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("expenses")
public class Expense implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKey(value = "expense_id")
	protected UUID uuid;

	@Column(value = "expense_type")
	private short expenseType;

	@Column(value = "total_sum")
	private double totalSum;

	@Column(value = "leftover_sum")
	private double leftoverSum;

	@Column(value = "payed_in_full")
	private boolean payedInFull;

	@Column(value = "details")
	private String details;

	@Column(value = "due_date")
	private Date dueDate;

	public Expense() {
	}

	public Expense(UUID uuid, short expenseType, double totalSum, double leftoverSum, boolean payedInFull,
			String details, Date dueDate) {
		super();
		this.uuid = uuid;
		this.expenseType = expenseType;
		this.totalSum = totalSum;
		this.leftoverSum = leftoverSum;
		this.payedInFull = payedInFull;
		this.details = details;
		this.dueDate = dueDate;
	}

	public short getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(short expenseType) {
		this.expenseType = expenseType;
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

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "Expense [expenseType=" + expenseType + ", totalSum=" + totalSum + ", leftoverSum=" + leftoverSum
				+ ", payedInFull=" + payedInFull + ", details=" + details + ", dueDate=" + dueDate + "]";
	}
}
