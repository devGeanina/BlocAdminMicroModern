package com.blocadminmicromodern.budgetservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blocadminmicromodern.budgetservice.dto.BudgetDTO;
import com.blocadminmicromodern.budgetservice.entity.Budget;
import com.blocadminmicromodern.budgetservice.repository.BudgetRepository;
import com.blocadminmicromodern.budgetservice.utils.BudgetStatuses.BUDGET_STATUS;
import com.datastax.driver.core.utils.UUIDs;

@Service
public class BudgetServiceImpl implements BudgetService {

	private final BudgetRepository budgetDAO;

	@Autowired
	public BudgetServiceImpl(BudgetRepository budgetRepository) {
		this.budgetDAO = budgetRepository;
	}

	@Transactional
	@Override
	public List<BudgetDTO> getBudgets() {
		List<BudgetDTO> dtos = new ArrayList<>();
		List<Budget> entities = new ArrayList<Budget>();
		budgetDAO.findAll().forEach(entities::add);

		for (Budget entity : entities) {
			BudgetDTO dto = getDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	private BudgetDTO getDTO(Budget entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		BudgetDTO entityDTO = new BudgetDTO();
		entityDTO.setDetails(entity.getDetails());
		entityDTO.setLeftoverSum(entity.getLeftoverSum());
		if (entity.getLeftoverSum() > 0.0)
			entityDTO.setStatus(BUDGET_STATUS.AVAILABLE.getType());
		else
			entityDTO.setStatus(BUDGET_STATUS.UNAVAILABLE.getType());
		entityDTO.setTotalSum(entity.getTotalSum());
		entityDTO.setBudgetType(entity.getType());
		entityDTO.setUuid(entity.getUuid());
		return entityDTO;
	}

	@Override
	public void saveBudget(BudgetDTO budgetDTO) {
		if (budgetDTO == null) {
			throw new IllegalArgumentException("Cannot save the item because it's null.");
		}
		Budget budget = new Budget();
		budget = getEntity(budgetDTO);
		if (budget.getUuid() == null)
			budget.setUuid(UUIDs.timeBased());
		budgetDAO.save(budget);
	}

	private Budget getEntity(BudgetDTO entityDTO) {
		if (entityDTO == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}

		Budget entity = new Budget();
		entity.setLeftoverSum(entityDTO.getLeftoverSum());

		if (entityDTO.getDetails() != null && !entityDTO.getDetails().isEmpty())
			entity.setDetails(entityDTO.getDetails());
		entity.setTotalSum(entityDTO.getTotalSum());
		entity.setType(entityDTO.getBudgetType());

		if (entityDTO.getLeftoverSum() > 0.0)
			entity.setStatus(BUDGET_STATUS.AVAILABLE.getType());
		else
			entity.setStatus(BUDGET_STATUS.UNAVAILABLE.getType());

		if (entityDTO.getUuid() != null)
			entity.setUuid(entityDTO.getUuid());
		return entity;
	}

	@Override
	public void deleteBudget(UUID budgetuuid) {
		if (budgetuuid == null) {
			throw new IllegalArgumentException("Cannot delete the item because the id is null.");
		}
		budgetDAO.deleteById(budgetuuid);
	}

	@Override
	public BudgetDTO getBudget(UUID budgetuuid) {
		if (budgetuuid == null) {
			throw new IllegalArgumentException("Cannot retrieve the item because the id is null.");
		}

		Optional<Budget> entity = budgetDAO.findById(budgetuuid);
		BudgetDTO entityDTO = getDTO(entity.get());
		return entityDTO;
	}

	/**
	 * Enhance method by sending back the result with Kafka if there's budget
	 * available to create the expense or not.
	 */
	@Override
	public void updateBudget(short type, double sum) {
		List<Budget> budgets = budgetDAO.findAll().stream().filter(b -> b.getType() == type)
				.collect(Collectors.toList());
		if (budgets != null && !budgets.isEmpty()) {
			for (Budget budget : budgets) {
				if (budget.getStatus() == BUDGET_STATUS.AVAILABLE.getType()) {
					double leftoverSum = budget.getLeftoverSum() - sum;
					if (leftoverSum <= 0) {
						continue;
					} else {
						budget.setLeftoverSum(leftoverSum);
						budgetDAO.save(budget);
					}
				}
			}
		}
	}
}
