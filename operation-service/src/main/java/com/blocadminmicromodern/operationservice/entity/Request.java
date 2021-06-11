package com.blocadminmicromodern.operationservice.entity;

import java.io.Serializable;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("requests")
public class Request implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKey(value = "request_id")
	protected UUID uuid;

	@Column(value = "request_type")
	private short requestType;

	@Column(value = "name")
	private String name;

	@Column(value = "details")
	private String details;

	@Column(value = "is_resolved")
	private boolean isResolved;

	@Column(value = "due_date")
	private Date dueDate;

	public Request() {
	}

	public Request(UUID uuid, short requestType, String name, String details, boolean isResolved, Date dueDate) {
		super();
		this.uuid = uuid;
		this.requestType = requestType;
		this.name = name;
		this.details = details;
		this.isResolved = isResolved;
		this.dueDate = dueDate;
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

	public short getRequestType() {
		return requestType;
	}

	public void setRequestType(short requestType) {
		this.requestType = requestType;
	}

	public boolean isResolved() {
		return isResolved;
	}

	public void setResolved(boolean isResolved) {
		this.isResolved = isResolved;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "Request [requestType=" + requestType + ", name=" + name + ", details=" + details + ", isResolved="
				+ isResolved + ", dueDate=" + dueDate + "]";
	}
}
