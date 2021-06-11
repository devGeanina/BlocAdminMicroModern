package com.blocadminmicromodern.userservice.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
@EqualsAndHashCode
@ToString
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID uuid;
	private String firstName;
	private String lastName;
	private String password;
	private short userType;
	private int buildingNr;
	private String details;
	private int appartmentNr;
	private String username;

	public UserDTO() {
	}

	public UserDTO(UUID uuid, String firstName, String lastName, String password, short userType, int buildingNr,
			String details, int appartmentNr, String username) {
		super();
		this.uuid = uuid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.userType = userType;
		this.buildingNr = buildingNr;
		this.details = details;
		this.appartmentNr = appartmentNr;
		this.username = username;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getBuildingNr() {
		return buildingNr;
	}

	public void setBuildingNr(int buildingNr) {
		this.buildingNr = buildingNr;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getAppartmentNr() {
		return appartmentNr;
	}

	public void setAppartmentNr(int appartmentNr) {
		this.appartmentNr = appartmentNr;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public short getUserType() {
		return userType;
	}

	public void setUserType(short userType) {
		this.userType = userType;
	}
}
