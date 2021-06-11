package com.blocadminmicromodern.operationservice.service;

import java.util.List;
import java.util.UUID;

import com.blocadminmicromodern.operationservice.dto.HouseholdDTO;

public interface HouseholdService {

	public abstract List<HouseholdDTO> getHouseholds();

	public abstract void saveHousehold(HouseholdDTO householdDTO);

	public abstract void deleteHousehold(UUID uuid);

	public abstract HouseholdDTO getHousehold(UUID uuid);

	public abstract List<HouseholdDTO> getHouseholdsWithDebt();
}
