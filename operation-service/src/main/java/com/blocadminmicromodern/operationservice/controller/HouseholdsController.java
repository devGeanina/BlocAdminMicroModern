package com.blocadminmicromodern.operationservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blocadminmicromodern.operationservice.dto.HouseholdDTO;
import com.blocadminmicromodern.operationservice.service.HouseholdService;

@RestController
public class HouseholdsController {

	private final HouseholdService householdService;

	public HouseholdsController(HouseholdService householdService) {
		this.householdService = householdService;
	}

	@GetMapping("/houses")
	public List<HouseholdDTO> getHouseholds() {
		return householdService.getHouseholds();
	}

	@GetMapping("/houses/debt")
	public List<HouseholdDTO> getHouseholdsWithDebt() {
		return householdService.getHouseholdsWithDebt();
	}

	@GetMapping("/houses/{uuid}")
	HouseholdDTO findHousehold(@PathVariable UUID uuid) {
		return householdService.getHousehold(uuid);
	}

	@PostMapping("/houses/save")
	public List<HouseholdDTO> saveOrUpdateHousehold(@RequestBody HouseholdDTO householdDTO) {
		householdService.saveHousehold(householdDTO);
		return householdService.getHouseholds();
	}

	@GetMapping("/houses/delete/{uuid}")
	public List<HouseholdDTO> deleteHousehold(@PathVariable UUID uuid) {
		householdService.deleteHousehold(uuid);
		return householdService.getHouseholds();
	}
}