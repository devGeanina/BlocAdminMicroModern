package com.blocadminmicromodern.operationservice.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.blocadminmicromodern.operationservice.entity.Household;


@Repository
public interface HouseholdRepository extends CassandraRepository<Household, UUID>{

}
