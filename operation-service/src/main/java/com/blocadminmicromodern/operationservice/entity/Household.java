package com.blocadminmicromodern.operationservice.entity;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("households")
public class Household implements Serializable {
	private static final long serialVersionUID = 1L;

	@PrimaryKey(value = "household_id")
	protected UUID uuid;

	@Column(value = "building_nr")
	private int buildingNr;

	@Column(value = "owner_name")
	private String ownerName;

	@Column(value = "appartment_nr")
	private int appartmentNr;

	@Column(value = "details")
	private String details;

	@Column(value = "rooms_nr")
	private int roomsNr;

	@Column(value = "nr_curr_occupants")
	private int nrCurrentOccupants;

	@Column(value = "total_capacity")
	private int totalCapacity;

	public Household() {
	}

	public Household(UUID uuid, int buildingNr, String ownerName, int appartmentNr, String details, int roomsNr,
			int nrCurrentOccupants, int totalCapacity) {
		this.uuid = uuid;
		this.buildingNr = buildingNr;
		this.ownerName = ownerName;
		this.appartmentNr = appartmentNr;
		this.details = details;
		this.roomsNr = roomsNr;
		this.nrCurrentOccupants = nrCurrentOccupants;
		this.totalCapacity = totalCapacity;
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

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	@Override
	public String toString() {
		return "Household [uuid=" + uuid + ", buildingNr=" + buildingNr + ", ownerName=" + ownerName + ", appartmentNr="
				+ appartmentNr + ", details=" + details + ", roomsNr=" + roomsNr + ", nrCurrentOccupants="
				+ nrCurrentOccupants + ", totalCapacity=" + totalCapacity + "]";
	}
}
