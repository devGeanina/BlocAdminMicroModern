package com.blocadminmicromodern.operationservice.repository;


import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.blocadminmicromodern.operationservice.entity.Request;


@Repository
public interface RequestRepository extends CassandraRepository<Request, UUID>{

}
