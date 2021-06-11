package com.blocadminmicromodern.operationservice.service;

import java.util.List;
import java.util.UUID;

import com.blocadminmicromodern.operationservice.dto.RequestDTO;

public interface RequestService {

	public abstract List<RequestDTO> getRequests();

	public abstract void saveRequest(RequestDTO requestDTO);

	public abstract void deleteRequest(UUID uuid);

	public abstract RequestDTO getRequest(UUID uuid);
}
