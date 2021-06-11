package com.blocadminmicromodern.operationservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blocadminmicromodern.operationservice.dto.HouseholdDTO;
import com.blocadminmicromodern.operationservice.entity.ExpenseByHousehold;
import com.blocadminmicromodern.operationservice.entity.Household;
import com.blocadminmicromodern.operationservice.repository.ExpenseByHouseholdRepository;
import com.blocadminmicromodern.operationservice.repository.HouseholdRepository;
import com.datastax.driver.core.utils.UUIDs;

@Service
public class HouseholdServiceImpl implements HouseholdService {

	private final HouseholdRepository householdDAO;
	private final ExpenseByHouseholdRepository expenseByHouseholdDAO;

	@Autowired
	public HouseholdServiceImpl(HouseholdRepository householdDAO, ExpenseByHouseholdRepository expenseByHouseholdDAO) {
		this.householdDAO = householdDAO;
		this.expenseByHouseholdDAO = expenseByHouseholdDAO;
	}

	@Transactional
	@Override
	public List<HouseholdDTO> getHouseholds() {
		List<HouseholdDTO> dtos = new ArrayList<>();

		List<Household> entities = new ArrayList<Household>();
		householdDAO.findAll().forEach(entities::add);
		for (Household entity : entities) {
			HouseholdDTO dto = getDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	@Transactional
	@Override
	public List<HouseholdDTO> getHouseholdsWithDebt() {
		HashSet<HouseholdDTO> dtos = new HashSet<HouseholdDTO>();
		List<ExpenseByHousehold> expenseByHouseholds = expenseByHouseholdDAO.findAll().stream()
				.filter(e -> e.getLeftoverSum() > 0 && !e.isPayed()).collect(Collectors.toList());

		Map<UUID, List<ExpenseByHousehold>> expenses = new HashMap<UUID, List<ExpenseByHousehold>>();
		for (ExpenseByHousehold expenseByHousehold : expenseByHouseholds) {
			if (expenses.containsKey(expenseByHousehold.getKey().getHouseholdId())) {
				expenses.get(expenseByHousehold.getKey().getHouseholdId()).add(expenseByHousehold);
			} else {
				List<ExpenseByHousehold> householdExpenses = new ArrayList<ExpenseByHousehold>();
				householdExpenses.add(expenseByHousehold);
				expenses.put(expenseByHousehold.getKey().getHouseholdId(), householdExpenses);
			}
		}

		if (expenses != null) {
			for (Entry<UUID, List<ExpenseByHousehold>> entry : expenses.entrySet()) {
				List<ExpenseByHousehold> houseExpenses = entry.getValue();
				HouseholdDTO entityDTO = new HouseholdDTO();
				entityDTO.setAppartmentNr(houseExpenses.get(0).getApartmentNr());
				entityDTO.setBuildingNr(houseExpenses.get(0).getBuildingNr());
				entityDTO.setOwnerName(houseExpenses.get(0).getOwner());

				if (houseExpenses != null) {
					double totalDebt = houseExpenses.stream().filter(o -> !o.isPayed() && o.getLeftoverSum() > 0.0)
							.mapToDouble(o -> o.getLeftoverSum()).sum();
					entityDTO.setTotalDebt(totalDebt);
				}
				dtos.add(entityDTO);
			}
		}

		return new ArrayList<HouseholdDTO>(dtos);
	}

	private HouseholdDTO getDTO(Household entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		HouseholdDTO entityDTO = new HouseholdDTO();
		entityDTO.setDetails(entity.getDetails());
		entityDTO.setAppartmentNr(entity.getAppartmentNr());
		entityDTO.setBuildingNr(entity.getBuildingNr());
		entityDTO.setNrCurrentOccupants(entity.getNrCurrentOccupants());
		entityDTO.setRoomsNr(entity.getRoomsNr());
		entityDTO.setTotalCapacity(entity.getTotalCapacity());
		entityDTO.setOwnerName(entity.getOwnerName());

		List<ExpenseByHousehold> expenseByHouseholds = expenseByHouseholdDAO.findByKeyHouseholdId(entity.getUuid());

		if (expenseByHouseholds != null) {
			double totalDebt = expenseByHouseholds.stream().filter(o -> !o.isPayed() && o.getLeftoverSum() > 0.0)
					.mapToDouble(o -> o.getLeftoverSum()).sum();
			entityDTO.setTotalDebt(totalDebt);
		} else
			entityDTO.setTotalDebt(0.0);

		entityDTO.setUuid(entity.getUuid());
		return entityDTO;
	}

	@Override
	public void saveHousehold(HouseholdDTO householdDTO) {
		if (householdDTO == null) {
			throw new IllegalArgumentException("Cannot save the item because it's null.");
		}
		Household household = new Household();
		household = getEntity(householdDTO);
		if (household.getUuid() == null)
			household.setUuid(UUIDs.timeBased());
		householdDAO.save(household);
	}

	private Household getEntity(HouseholdDTO entityDTO) {
		if (entityDTO == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		Household entity = new Household();
		entity.setAppartmentNr(entityDTO.getAppartmentNr());
		entity.setBuildingNr(entityDTO.getBuildingNr());
		entity.setNrCurrentOccupants(entityDTO.getNrCurrentOccupants());
		entity.setOwnerName(entityDTO.getOwnerName());
		entity.setRoomsNr(entityDTO.getRoomsNr());
		entity.setTotalCapacity(entityDTO.getTotalCapacity());

		if (entityDTO.getDetails() != null && !entityDTO.getDetails().isEmpty())
			entity.setDetails(entityDTO.getDetails());

		if (entityDTO.getUuid() != null)
			entity.setUuid(entityDTO.getUuid());
		return entity;
	}

	@Override
	public void deleteHousehold(UUID uuid) {
		if (uuid == null) {
			throw new IllegalArgumentException("Cannot delete the item because the id is null.");
		}
		householdDAO.deleteById(uuid);
	}

	@Override
	public HouseholdDTO getHousehold(UUID uuid) {
		if (uuid == null) {
			throw new IllegalArgumentException("Cannot retrieve the item because the id is null.");
		}

		Optional<Household> entity = householdDAO.findById(uuid);
		HouseholdDTO entityDTO = getDTO(entity.get());
		return entityDTO;
	}
}
