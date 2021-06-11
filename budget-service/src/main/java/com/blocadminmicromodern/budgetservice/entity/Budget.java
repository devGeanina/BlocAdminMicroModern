package com.blocadminmicromodern.budgetservice.entity;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("budgets")
public class Budget implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKey(value = "budget_id")
	protected UUID uuid;

	@Column(value = "budget_type")
	private short type;

	@Column(value = "total_sum")
	private double totalSum;

	@Column(value = "leftover_sum")
	private double leftoverSum;

	@Column(value = "details")
	private String details;

	@Column(value = "status")
	private short status;

	public Budget() {
	}

	public Budget(UUID uuid, short type, double totalSum, double leftoverSum, String details, short status) {
		super();
		this.uuid = uuid;
		this.type = type;
		this.totalSum = totalSum;
		this.leftoverSum = leftoverSum;
		this.details = details;
		this.status = status;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
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

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Budget [uuid=" + uuid + ", type=" + type + ", totalSum=" + totalSum + ", leftoverSum=" + leftoverSum
				+ ", details=" + details + ", status=" + status + "]";
	}
}
