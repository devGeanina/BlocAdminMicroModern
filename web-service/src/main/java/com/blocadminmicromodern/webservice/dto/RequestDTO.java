package com.blocadminmicromodern.webservice.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import com.blocadminmicromodern.webservice.utils.HouseholdRequestType;
import com.blocadminmicromodern.webservice.utils.Utils;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@Builder
@EqualsAndHashCode
public class RequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID uuid;
	private short requestType;
	private HouseholdRequestType requestTypeEnum;
	private String name;
	private String details;
	private boolean resolved;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dueDate;

	private String formattedDueDate;
	private String householdAddress;
	private UUID householdId;

	public RequestDTO() {
	}

	public RequestDTO(UUID uuid, short requestType, HouseholdRequestType requestTypeEnum, String name, String details,
			boolean resolved, Date dueDate, String formattedDueDate, String householdAddress, UUID householdId) {
		super();
		this.uuid = uuid;
		this.requestType = requestType;
		this.requestTypeEnum = requestTypeEnum;
		this.name = name;
		this.details = details;
		this.resolved = resolved;
		this.dueDate = dueDate;
		this.formattedDueDate = formattedDueDate;
		this.householdAddress = householdAddress;
		this.householdId = householdId;
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
		return householdAddress;
	}

	public void setHouseholdAddress(String householdAddress) {
		this.householdAddress = householdAddress;
	}

	public String getFormattedDueDate() {
		this.formattedDueDate = Utils.convertDateToString(dueDate);
		return formattedDueDate;
	}

	public void setFormattedDueDate(String formattedDueDate) {
		this.formattedDueDate = Utils.convertDateToString(dueDate);
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
		setRequestTypeEnum(HouseholdRequestType.getNameByCode(requestType));
	}

	public HouseholdRequestType getRequestTypeEnum() {
		return requestTypeEnum;
	}

	public void setRequestTypeEnum(HouseholdRequestType requestTypeEnum) {
		this.requestTypeEnum = requestTypeEnum;
	}

	public UUID getHouseholdId() {
		return householdId;
	}

	public void setHouseholdId(UUID householdId) {
		this.householdId = householdId;
	}
}
