package com.blocadminmicromodern.operationservice.entity;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class ExpenseByHouseholdKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKeyColumn(name = "household_id", type = PrimaryKeyType.PARTITIONED)
	private UUID householdId;

	@PrimaryKeyColumn(name = "expense_id", ordinal = 0, ordering = Ordering.DESCENDING)
	private UUID expenseId;

	public ExpenseByHouseholdKey(UUID householdId, UUID expenseId) {
		super();
		this.householdId = householdId;
		this.expenseId = expenseId;
	}

	public UUID getHouseholdId() {
		return householdId;
	}

	public void setHouseholdId(UUID householdId) {
		this.householdId = householdId;
	}

	public UUID getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(UUID expenseId) {
		this.expenseId = expenseId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expenseId == null) ? 0 : expenseId.hashCode());
		result = prime * result + ((householdId == null) ? 0 : householdId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExpenseByHouseholdKey other = (ExpenseByHouseholdKey) obj;
		if (expenseId == null) {
			if (other.expenseId != null)
				return false;
		} else if (!expenseId.equals(other.expenseId))
			return false;
		if (householdId == null) {
			if (other.householdId != null)
				return false;
		} else if (!householdId.equals(other.householdId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ExpenseByHouseholdKey [householdId=" + householdId + ", expenseId=" + expenseId + "]";
	}
}
