package com.blocadminmicromodern.budgetservice.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.blocadminmicromodern.budgetservice.entity.Budget;

@Repository
public interface BudgetRepository extends CassandraRepository<Budget, UUID> {

}
