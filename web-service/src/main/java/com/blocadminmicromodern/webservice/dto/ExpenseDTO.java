package com.blocadminmicromodern.webservice.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;

import com.blocadminmicromodern.webservice.utils.ExpenseType;
import com.blocadminmicromodern.webservice.utils.Utils;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@Builder
@EqualsAndHashCode
public class ExpenseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID uuid;
	private short expenseType;
	private ExpenseType expenseTypeEnum;
	private double totalSum;
	private double leftoverSum;
	private boolean payedInFull;
	private String details;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dueDate;

	private List<UUID> householdIds;
	private List<String> householdsAddresses;
	private String formattedDueDate;
	private String expenseAddressesFormatted;

	public ExpenseDTO() {
	}

	public ExpenseDTO(UUID uuid, short expenseType, ExpenseType expenseTypeEnum, double totalSum, double leftoverSum,
			boolean payedInFull, String details, Date dueDate, List<UUID> householdIds,
			List<String> householdsAddresses, String formattedDueDate, String expenseAddressesFormatted) {
		super();
		this.uuid = uuid;
		this.expenseType = expenseType;
		this.expenseTypeEnum = expenseTypeEnum;
		this.totalSum = totalSum;
		this.leftoverSum = leftoverSum;
		this.payedInFull = payedInFull;
		this.details = details;
		this.dueDate = dueDate;
		this.householdIds = householdIds;
		this.householdsAddresses = householdsAddresses;
		this.formattedDueDate = formattedDueDate;
		this.expenseAddressesFormatted = expenseAddressesFormatted;
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

	public String getFormattedDueDate() {
		this.formattedDueDate = Utils.convertDateToString(dueDate);
		return formattedDueDate;
	}

	public short getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(short expenseType) {
		this.expenseType = expenseType;
		setExpenseTypeEnum(ExpenseType.getNameByCode(expenseType));
	}

	public ExpenseType getExpenseTypeEnum() {
		return expenseTypeEnum;
	}

	public void setExpenseTypeEnum(ExpenseType expenseTypeEnum) {
		this.expenseTypeEnum = expenseTypeEnum;
	}

	public String getExpenseAddressesFormatted() {
		if (householdsAddresses != null && !householdsAddresses.isEmpty())
			this.expenseAddressesFormatted = householdsAddresses.stream().map(String::valueOf)
					.collect(Collectors.joining("/"));
		else
			this.expenseAddressesFormatted = "-";
		return expenseAddressesFormatted;
	}
}
