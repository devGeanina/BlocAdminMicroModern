package com.blocadminmicromodern.operationservice.repository;


import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import com.blocadminmicromodern.operationservice.entity.ExpenseByHousehold;
import com.blocadminmicromodern.operationservice.entity.ExpenseByHouseholdKey;

@Repository
public interface ExpenseByHouseholdRepository extends CassandraRepository<ExpenseByHousehold, ExpenseByHouseholdKey>{
	
	@Query(allowFiltering = true)
	public abstract List<ExpenseByHousehold> findByKeyHouseholdId(UUID householdId);
	
	@Query("SELECT * FROM blocadmin.expenses_by_household WHERE expense_id = ?0 and household_id= ?1 ALLOW FILTERING")
	public abstract ExpenseByHousehold findByExpenseIdAndHouseholdId(UUID expenseId, UUID householdId);
	
	@Query("SELECT * FROM blocadmin.expenses_by_household where leftover_sum > 0 and payed_in_full = false")
	List<ExpenseByHousehold> getHouseholdsWithDebt();
}
