package com.blocadminmicromodern.webservice.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@Builder
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
	private String address;

	public HouseholdDTO() {
	}

	public HouseholdDTO(UUID uuid, int buildingNr, int appartmentNr, String details, int roomsNr,
			int nrCurrentOccupants, int totalCapacity, String ownerName, double totalDebt, String address) {
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
		this.address = address;
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

	public String getAddress() {
		address = "B. ".concat(String.valueOf(buildingNr)).concat(", Ap.").concat(String.valueOf(appartmentNr));
		return address;
	}
}
