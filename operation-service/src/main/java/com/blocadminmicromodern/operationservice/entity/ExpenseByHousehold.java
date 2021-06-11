package com.blocadminmicromodern.operationservice.entity;

import java.io.Serializable;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("expenses_by_household")
public class ExpenseByHousehold implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKey
	private ExpenseByHouseholdKey key;

	@Column(value = "apartment_nr")
	private int apartmentNr;

	@Column(value = "building_nr")
	private int buildingNr;

	@Column(value = "owner")
	private String owner;

	@Column(value = "total_sum")
	private double totalSum;

	@Column(value = "leftover_sum")
	private double leftoverSum;

	@Column(value = "payed_in_full")
	private boolean payed;

	public ExpenseByHousehold() {
	}

	public ExpenseByHousehold(ExpenseByHouseholdKey key, int apartmentNr, int buildingNr, String owner, double totalSum,
			double leftoverSum, boolean payed) {
		super();
		this.key = key;
		this.apartmentNr = apartmentNr;
		this.buildingNr = buildingNr;
		this.owner = owner;
		this.totalSum = totalSum;
		this.leftoverSum = leftoverSum;
		this.payed = payed;
	}

	public ExpenseByHouseholdKey getKey() {
		return key;
	}

	public void setKey(ExpenseByHouseholdKey key) {
		this.key = key;
	}

	public int getApartmentNr() {
		return apartmentNr;
	}

	public void setApartmentNr(int apartmentNr) {
		this.apartmentNr = apartmentNr;
	}

	public int getBuildingNr() {
		return buildingNr;
	}

	public void setBuildingNr(int buildingNr) {
		this.buildingNr = buildingNr;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
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

	public boolean isPayed() {
		return payed;
	}

	public void setPayed(boolean payed) {
		this.payed = payed;
	}

	@Override
	public String toString() {
		return "ExpenseByHousehold [key=" + key + ", apartmentNr=" + apartmentNr + ", buildingNr=" + buildingNr
				+ ", owner=" + owner + ", totalSum=" + totalSum + ", leftoverSum=" + leftoverSum + ", payed=" + payed
				+ "]";
	}
}
