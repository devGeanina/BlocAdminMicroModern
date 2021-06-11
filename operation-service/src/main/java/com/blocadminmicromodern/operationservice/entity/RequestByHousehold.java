package com.blocadminmicromodern.operationservice.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("requests_by_household")
public class RequestByHousehold implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKey
	private RequestByHouseholdKey key;

	@Column(value = "name")
	private String name;

	@Column(value = "address")
	private String address;

	@Column(value = "request_type")
	private short type;

	@Column(value = "resolved")
	private boolean resolved;

	@Column(value = "due_date")
	private Date dueDate;

	public RequestByHousehold() {
	}

	public RequestByHousehold(RequestByHouseholdKey key, String name, String address, short type, boolean resolved,
			Date dueDate) {
		this.key = key;
		this.name = name;
		this.address = address;
		this.type = type;
		this.resolved = resolved;
		this.dueDate = dueDate;
	}

	public RequestByHouseholdKey getKey() {
		return key;
	}

	public void setKey(RequestByHouseholdKey key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public boolean isResolved() {
		return resolved;
	}

	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@Override
	public String toString() {
		return "RequestByHousehold [key=" + key + ", name=" + name + ", address=" + address + ", type=" + type
				+ ", resolved=" + resolved + ", dueDate=" + dueDate + "]";
	}
}
