package com.blocadminmicromodern.operationservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blocadminmicromodern.operationservice.dto.RequestDTO;
import com.blocadminmicromodern.operationservice.entity.Household;
import com.blocadminmicromodern.operationservice.entity.Request;
import com.blocadminmicromodern.operationservice.entity.RequestByHousehold;
import com.blocadminmicromodern.operationservice.entity.RequestByHouseholdKey;
import com.blocadminmicromodern.operationservice.repository.HouseholdRepository;
import com.blocadminmicromodern.operationservice.repository.RequestByHouseholdRepository;
import com.blocadminmicromodern.operationservice.repository.RequestRepository;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select.Where;
import com.datastax.driver.core.utils.UUIDs;

@Service
public class RequestServiceImpl implements RequestService {

	private final RequestRepository requestDAO;
	private final HouseholdRepository householdDAO;
	private final RequestByHouseholdRepository requestByHouseholdDAO;
	private final CassandraTemplate cassandraTemplate;

	@Autowired
	public RequestServiceImpl(RequestRepository requestDAO, HouseholdRepository householdDAO,
			RequestByHouseholdRepository requestByHouseholdDAO, CassandraTemplate cassandraTemplate) {
		this.requestDAO = requestDAO;
		this.householdDAO = householdDAO;
		this.requestByHouseholdDAO = requestByHouseholdDAO;
		this.cassandraTemplate = cassandraTemplate;
	}

	@Transactional
	@Override
	public List<RequestDTO> getRequests() {
		List<RequestDTO> dtos = new ArrayList<>();

		List<Request> entities = new ArrayList<Request>();
		requestDAO.findAll().forEach(entities::add);
		for (Request entity : entities) {
			RequestDTO dto = getDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	private RequestDTO getDTO(Request entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		RequestDTO entityDTO = new RequestDTO();
		entityDTO.setDetails(entity.getDetails());
		entityDTO.setDueDate(entity.getDueDate());
		entityDTO.setResolved(entity.isResolved());
		entityDTO.setName(entity.getName());
		entityDTO.setRequestType(entity.getRequestType());

		if (entity.getUuid() != null) {

			List<Household> households = householdDAO.findAll();

			for (Household h : households) {

				Where select = QueryBuilder.select().from("requests_by_household")
						.where(QueryBuilder.eq("household_id", h.getUuid()))
						.and(QueryBuilder.eq("request_id", entity.getUuid())); // Cassandra template demo usage
				RequestByHousehold requestByHousehold = cassandraTemplate.selectOne(select, RequestByHousehold.class);

				if (requestByHousehold != null) {
					entityDTO.setHouseholdId(requestByHousehold.getKey().getHouseholdId());
					entityDTO.setHouseholdAddress(requestByHousehold.getAddress());
				}
			}
		}

		entityDTO.setUuid(entity.getUuid());
		return entityDTO;
	}

	@Override
	public void saveRequest(RequestDTO requestDTO) {
		if (requestDTO == null) {
			throw new IllegalArgumentException("Cannot save the item because it's null.");
		}
		Request request = new Request();
		request = getEntity(requestDTO);
		if (request.getUuid() == null)
			request.setUuid(UUIDs.timeBased());
		requestDAO.save(request);
	}

	private Request getEntity(RequestDTO entityDTO) {
		if (entityDTO == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}

		Request entity = new Request();
		entity.setDueDate(entityDTO.getDueDate());
		entity.setName(entityDTO.getName());
		entity.setResolved(entityDTO.isResolved());
		entity.setRequestType(entityDTO.getRequestType());

		if (entityDTO.getHouseholdId() != null) {
			if (entityDTO.getUuid() != null) {
				Household household = householdDAO.findById(entityDTO.getHouseholdId()).orElse(null);
				if (household != null) {
					RequestByHousehold requestHousehold = new RequestByHousehold();
					requestHousehold.setKey(new RequestByHouseholdKey(entityDTO.getHouseholdId(), entityDTO.getUuid()));
					requestHousehold.setAddress("B.".concat(String.valueOf(household.getBuildingNr())).concat(", Ap.")
							.concat(String.valueOf(household.getAppartmentNr())));
					requestHousehold.setDueDate(entityDTO.getDueDate());
					requestHousehold.setName(entityDTO.getName());
					requestHousehold.setResolved(entityDTO.isResolved());
					requestHousehold.setType(entityDTO.getRequestType());
					requestByHouseholdDAO.save(requestHousehold);
				}
			}
			entity.setUuid(entityDTO.getUuid());
		}

		if (entityDTO.getDetails() != null && !entityDTO.getDetails().isEmpty())
			entity.setDetails(entityDTO.getDetails());

		return entity;
	}

	@Override
	public void deleteRequest(UUID uuid) {
		if (uuid == null) {
			throw new IllegalArgumentException("Cannot delete the item because the id is null.");
		}

		RequestDTO dto = getRequest(uuid);

		if (dto.getHouseholdId() != null) {
			RequestByHousehold requestByHousehold = requestByHouseholdDAO.findByKeyHouseholdId(dto.getHouseholdId());

			if (requestByHousehold != null)
				cassandraTemplate.delete(requestByHousehold);
		}

		requestDAO.deleteById(uuid);
	}

	@Override
	public RequestDTO getRequest(UUID uuid) {
		if (uuid == null) {
			throw new IllegalArgumentException("Cannot retrieve the item because the id is null.");
		}

		Optional<Request> entity = requestDAO.findById(uuid);
		RequestDTO entityDTO = getDTO(entity.get());
		return entityDTO;
	}
}
