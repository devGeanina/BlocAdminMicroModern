package com.blocadminmicromodern.webservice.dto;

import java.io.Serializable;
import java.util.UUID;

import com.blocadminmicromodern.webservice.utils.UserType;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID uuid;
	private String firstName;
	private String lastName;
	private String fullName;
	private String password;
	private int buildingNr;
	private String details;
	private int appartmentNr;
	private String username;
	private UserType userTypeEnum;
	private short userType;

	public UserDTO() {
	}

	public UserDTO(UUID uuid, String firstName, String lastName, String fullName, String password, int buildingNr,
			String details, int appartmentNr, String username, UserType userTypeEnum, short userType) {
		super();
		this.uuid = uuid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = fullName;
		this.password = password;
		this.buildingNr = buildingNr;
		this.details = details;
		this.appartmentNr = appartmentNr;
		this.username = username;
		this.userTypeEnum = userTypeEnum;
		this.userType = userType;
	}

	public UserDTO(String password, String username) {
		super();
		this.password = password;
		this.username = username;
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

	public String getFullName() {
		return getFirstName().concat(" ").concat(getLastName());
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserType getUserTypeEnum() {
		return userTypeEnum;
	}

	public void setUserTypeEnum(UserType userTypeEnum) {
		this.userTypeEnum = userTypeEnum;
	}

	public short getUserType() {
		return userType;
	}

	public void setUserType(short userType) {
		this.userType = userType;
		setUserTypeEnum(UserType.getNameByCode(userType));
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
