package com.blocadminmicromodern.userservice.entity;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKey(value = "user_id")
	protected UUID uuid;

	@Column(value = "username")
	private String username;

	@Column(value = "password")
	private String password;

	@Column(value = "first_name")
	private String firstName;

	@Column(value = "last_name")
	private String lastName;

	@Column(value = "user_type")
	private short userType;

	@Column(value = "building_nr")
	private int buildingNr;

	@Column(value = "details")
	private String details;

	@Column(value = "appartment_nr")
	private int appartmentNr;

	public User() {
	}

	public User(UUID uuid, String username, String password, String firstName, String lastName, short userType,
			int buildingNr, String details, int appartmentNr) {
		this.uuid = uuid;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userType = userType;
		this.buildingNr = buildingNr;
		this.details = details;
		this.appartmentNr = appartmentNr;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public short getUserType() {
		return userType;
	}

	public void setUserType(short userType) {
		this.userType = userType;
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

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "User [uuid=" + uuid + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", userType=" + userType + ", buildingNr=" + buildingNr + ", details="
				+ details + ", appartmentNr=" + appartmentNr + "]";
	}
}
