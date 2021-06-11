package com.blocadminmicromodern.operationservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blocadminmicromodern.operationservice.dto.RequestDTO;
import com.blocadminmicromodern.operationservice.service.RequestService;

@RestController
public class RequestsController {

	private final RequestService requestService;

	public RequestsController(RequestService requestService) {
		this.requestService = requestService;
	}

	@GetMapping("/requests")
	public List<RequestDTO> getRequests() {
		return requestService.getRequests();
	}

	@GetMapping("/requests/{uuid}")
	RequestDTO findRequest(@PathVariable UUID uuid) {
		return requestService.getRequest(uuid);
	}

	@PostMapping("/requests/save")
	public List<RequestDTO> saveOrUpdateBudget(@RequestBody RequestDTO requestDTO) {
		requestService.saveRequest(requestDTO);
		return requestService.getRequests();
	}

	@GetMapping("/requests/delete/{uuid}")
	public List<RequestDTO> deleteExpense(@PathVariable UUID uuid) {
		requestService.deleteRequest(uuid);
		return requestService.getRequests();
	}
}