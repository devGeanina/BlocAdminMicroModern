package com.blocadminmicromodern.operationservice.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
@EqualsAndHashCode
@ToString
public class RequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID uuid;
	private short requestType;
	private String name;
	private String householdAddress;
	private UUID householdId;
	private String details;
	private boolean resolved;
	private Date dueDate;

	public RequestDTO() {
	}

	public RequestDTO(UUID uuid, short requestType, String name, String householdAddress, UUID householdId,
			String details, boolean resolved, Date dueDate) {
		super();
		this.uuid = uuid;
		this.requestType = requestType;
		this.name = name;
		this.householdAddress = householdAddress;
		this.householdId = householdId;
		this.details = details;
		this.resolved = resolved;
		this.dueDate = dueDate;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getHouseholdAddress() {
		if (householdAddress != null)
			return householdAddress;
		else
			return "-";
	}

	public void setHouseholdAddress(String householdAddress) {
		this.householdAddress = householdAddress;
	}

	public UUID getHouseholdId() {
		return householdId;
	}

	public void setHouseholdId(UUID householdId) {
		this.householdId = householdId;
	}

	public boolean isResolved() {
		return resolved;
	}

	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	public short getRequestType() {
		return requestType;
	}

	public void setRequestType(short requestType) {
		this.requestType = requestType;
	}
}
