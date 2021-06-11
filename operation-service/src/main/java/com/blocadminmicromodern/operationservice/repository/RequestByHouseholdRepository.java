package com.blocadminmicromodern.operationservice.repository;


import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import com.blocadminmicromodern.operationservice.entity.RequestByHousehold;
import com.blocadminmicromodern.operationservice.entity.RequestByHouseholdKey;

@Repository
public interface RequestByHouseholdRepository extends CassandraRepository<RequestByHousehold, RequestByHouseholdKey>{
	
	@Query(allowFiltering = true)
	public abstract RequestByHousehold findByKeyHouseholdId(UUID householdId);
	
	@Query("SELECT * FROM blocadmin.requests_by_household WHERE request_id = ?0 and household_id= ?1 ALLOW FILTERING")
	public abstract RequestByHousehold findByRequestIdAndHouseholdId(UUID requestId, UUID householdId);
}
