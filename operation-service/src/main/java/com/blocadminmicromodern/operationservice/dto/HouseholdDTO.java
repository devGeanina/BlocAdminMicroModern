package com.blocadminmicromodern.operationservice.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
public class HouseholdDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID uuid;
	private int buildingNr;
	private int appartmentNr;
	private String details;
	private int roomsNr;
	private int nrCurrentOccupants;
	private int totalCapacity;
	private String ownerName;
	private double totalDebt;

	public HouseholdDTO() {
	}

	public HouseholdDTO(UUID uuid, int buildingNr, int appartmentNr, String details, int roomsNr,
			int nrCurrentOccupants, int totalCapacity, String ownerName, double totalDebt) {
		super();
		this.uuid = uuid;
		this.buildingNr = buildingNr;
		this.appartmentNr = appartmentNr;
		this.details = details;
		this.roomsNr = roomsNr;
		this.nrCurrentOccupants = nrCurrentOccupants;
		this.totalCapacity = totalCapacity;
		this.ownerName = ownerName;
		this.totalDebt = totalDebt;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public int getBuildingNr() {
		return buildingNr;
	}

	public void setBuildingNr(int buildingNr) {
		this.buildingNr = buildingNr;
	}

	public int getAppartmentNr() {
		return appartmentNr;
	}

	public void setAppartmentNr(int appartmentNr) {
		this.appartmentNr = appartmentNr;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getRoomsNr() {
		return roomsNr;
	}

	public void setRoomsNr(int roomsNr) {
		this.roomsNr = roomsNr;
	}

	public int getNrCurrentOccupants() {
		return nrCurrentOccupants;
	}

	public void setNrCurrentOccupants(int nrCurrentOccupants) {
		this.nrCurrentOccupants = nrCurrentOccupants;
	}

	public int getTotalCapacity() {
		return totalCapacity;
	}

	public void setTotalCapacity(int totalCapacity) {
		this.totalCapacity = totalCapacity;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public double getTotalDebt() {
		return totalDebt;
	}

	public void setTotalDebt(double totalDebt) {
		this.totalDebt = totalDebt;
	}
}
