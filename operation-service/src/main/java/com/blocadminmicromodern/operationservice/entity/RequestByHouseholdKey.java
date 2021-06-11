package com.blocadminmicromodern.operationservice.entity;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class RequestByHouseholdKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKeyColumn(name = "household_id", type = PrimaryKeyType.PARTITIONED)
	private UUID householdId;

	@PrimaryKeyColumn(name = "request_id", ordinal = 0, ordering = Ordering.DESCENDING)
	private UUID requestId;

	public RequestByHouseholdKey(UUID householdId, UUID requestId) {
		this.householdId = householdId;
		this.requestId = requestId;
	}

	public UUID getHouseholdId() {
		return householdId;
	}

	public void setHouseholdId(UUID householdId) {
		this.householdId = householdId;
	}

	public UUID getRequestId() {
		return requestId;
	}

	public void setRequestId(UUID requestId) {
		this.requestId = requestId;
	}
}
