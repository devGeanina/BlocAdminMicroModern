package com.blocadminmicromodern.operationservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blocadminmicromodern.operationservice.dto.ExpenseDTO;
import com.blocadminmicromodern.operationservice.entity.Expense;
import com.blocadminmicromodern.operationservice.entity.ExpenseByHousehold;
import com.blocadminmicromodern.operationservice.entity.ExpenseByHouseholdKey;
import com.blocadminmicromodern.operationservice.entity.Household;
import com.blocadminmicromodern.operationservice.repository.ExpenseByHouseholdRepository;
import com.blocadminmicromodern.operationservice.repository.ExpenseRepository;
import com.blocadminmicromodern.operationservice.repository.HouseholdRepository;
import com.datastax.driver.core.utils.UUIDs;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	private final ExpenseRepository expenseDAO;
	private final ExpenseByHouseholdRepository expenseByHouseholdDAO;
	private final HouseholdRepository householdDAO;
	CassandraTemplate cassandraTemplate;

	@Autowired
	public ExpenseServiceImpl(ExpenseRepository expenseDAO, ExpenseByHouseholdRepository expenseByHouseholdDAO,
			HouseholdRepository householdDAO, CassandraTemplate cassandraTemplate) {
		this.expenseDAO = expenseDAO;
		this.expenseByHouseholdDAO = expenseByHouseholdDAO;
		this.householdDAO = householdDAO;
		this.cassandraTemplate = cassandraTemplate;
	}

	@Transactional
	@Override
	public List<ExpenseDTO> getExpenses() {
		List<ExpenseDTO> dtos = new ArrayList<>();

		List<Expense> entities = new ArrayList<Expense>();
		expenseDAO.findAll().forEach(entities::add);
		for (Expense entity : entities) {
			ExpenseDTO dto = getDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	private ExpenseDTO getDTO(Expense entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}

		ExpenseDTO entityDTO = new ExpenseDTO();
		entityDTO.setDetails(entity.getDetails());
		entityDTO.setDueDate(entity.getDueDate());
		entityDTO.setExpenseType(entity.getExpenseType());
		entityDTO.setLeftoverSum(entity.getLeftoverSum());
		entityDTO.setTotalSum(entity.getTotalSum());
		entityDTO.setPayedInFull(entity.isPayedInFull());

		List<UUID> householdIds = new ArrayList<UUID>();
		List<String> householdsAddresses = new ArrayList<String>();

		if (entity.getUuid() != null) {
			List<Household> households = householdDAO.findAll();
			List<ExpenseByHousehold> expensesByHouseholds = new ArrayList<ExpenseByHousehold>();
			for (Household h : households) {
				ExpenseByHousehold expense = expenseByHouseholdDAO.findByExpenseIdAndHouseholdId(entity.getUuid(),
						h.getUuid());
				if (expense != null)
					expensesByHouseholds.add(expense);
			}

			if (expensesByHouseholds != null && !expensesByHouseholds.isEmpty()) {
				for (ExpenseByHousehold expenseByHousehold : expensesByHouseholds) {
					householdIds.add(expenseByHousehold.getKey().getHouseholdId());
					householdsAddresses.add("B.".concat(String.valueOf(expenseByHousehold.getBuildingNr()))
							.concat(", Ap.").concat(String.valueOf(expenseByHousehold.getApartmentNr())));
				}
			}
		}
		entityDTO.setHouseholdIds(householdIds);
		entityDTO.setHouseholdsAddresses(householdsAddresses);
		entityDTO.setUuid(entity.getUuid());
		return entityDTO;
	}

	@Override
	public void saveExpense(ExpenseDTO expenseDTO) {
		if (expenseDTO == null) {
			throw new IllegalArgumentException("Cannot save the item because it's null.");
		}
		Expense expense = new Expense();
		expense = getEntity(expenseDTO);
		if (expense.getUuid() == null)
			expense.setUuid(UUIDs.timeBased());
		expenseDAO.save(expense);
	}

	private Expense getEntity(ExpenseDTO entityDTO) {
		if (entityDTO == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		Expense entity = new Expense();
		entity.setLeftoverSum(entityDTO.getLeftoverSum());

		if (entityDTO.getDetails() != null && !entityDTO.getDetails().isEmpty())
			entity.setDetails(entityDTO.getDetails());
		entity.setTotalSum(entityDTO.getTotalSum());
		entity.setExpenseType(entityDTO.getExpenseType());
		entity.setDueDate(entityDTO.getDueDate());
		entity.setPayedInFull(entityDTO.isPayedInFull());

		if (entityDTO.isPayedInFull())
			entity.setLeftoverSum(0.0);

		if (entityDTO.getHouseholdIds() != null && entityDTO.getHouseholdIds().isEmpty() == false) {
			if (entityDTO.getUuid() != null) {
				List<ExpenseByHousehold> expenseByHouseholds = new ArrayList<ExpenseByHousehold>();
				for (UUID houseId : entityDTO.getHouseholdIds()) {
					Household household = householdDAO.findById(houseId).get();
					if (household != null) {
						ExpenseByHousehold expenseByHousehold = new ExpenseByHousehold();
						expenseByHousehold.setKey(new ExpenseByHouseholdKey(houseId, entityDTO.getUuid()));
						expenseByHousehold.setOwner(household.getOwnerName());
						expenseByHousehold.setLeftoverSum(entityDTO.getLeftoverSum());
						expenseByHousehold.setTotalSum(entityDTO.getTotalSum());
						expenseByHousehold.setPayed(entityDTO.isPayedInFull());
						expenseByHouseholds.add(expenseByHousehold);
					}
				}
				expenseByHouseholdDAO.saveAll(expenseByHouseholds);
				entity.setUuid(entityDTO.getUuid());
			}
		}

		return entity;
	}

	@Override
	public void deleteExpense(UUID uuid) {
		if (uuid == null) {
			throw new IllegalArgumentException("Cannot delete the item because the id is null.");
		}

		ExpenseDTO dto = getExpense(uuid);
		List<ExpenseByHousehold> householdExpenses = new ArrayList<ExpenseByHousehold>();

		if (dto.getHouseholdIds() != null) {
			for (UUID houseId : dto.getHouseholdIds()) {
				householdExpenses.addAll(expenseByHouseholdDAO.findByKeyHouseholdId(houseId));
			}
			List<ExpenseByHousehold> expensesByHousehold = householdExpenses.stream()
					.filter(h -> h.getKey().getExpenseId().equals(uuid)).collect(Collectors.toList());
			if (expensesByHousehold != null)
				expenseByHouseholdDAO.deleteAll(expensesByHousehold);
		}

		expenseDAO.deleteById(uuid);
	}

	@Override
	public ExpenseDTO getExpense(UUID uuid) {
		if (uuid == null) {
			throw new IllegalArgumentException("Cannot retrieve the item because the id is null.");
		}

		Optional<Expense> entity = expenseDAO.findById(uuid);
		ExpenseDTO entityDTO = getDTO(entity.get());
		return entityDTO;
	}
}
