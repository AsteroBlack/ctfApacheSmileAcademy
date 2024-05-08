                                                                															
/*
 * Java business for entity table ation_to_notifiable 
 * Created on 2023-06-29 ( Time 14:15:46 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.business;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import ci.smile.simswaporange.utils.*;
import ci.smile.simswaporange.utils.dto.*;
import ci.smile.simswaporange.utils.enums.*;
import ci.smile.simswaporange.utils.contract.*;
import ci.smile.simswaporange.utils.contract.IBasicBusiness;
import ci.smile.simswaporange.utils.contract.Request;
import ci.smile.simswaporange.utils.contract.Response;
import ci.smile.simswaporange.utils.dto.transformer.*;
import ci.smile.simswaporange.dao.entity.AtionToNotifiable;
import ci.smile.simswaporange.dao.entity.User;
import ci.smile.simswaporange.dao.entity.ActionParametrable;
import ci.smile.simswaporange.dao.entity.Status;
import ci.smile.simswaporange.dao.entity.User;
import ci.smile.simswaporange.dao.entity.*;
import ci.smile.simswaporange.dao.repository.*;

/**
BUSINESS for table "ation_to_notifiable"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class AtionToNotifiableBusiness implements IBasicBusiness<Request<AtionToNotifiableDto>, Response<AtionToNotifiableDto>> {

	@PersistenceContext
	private EntityManager em;
	private SimpleDateFormat dateFormat;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserRepository user2Repository;
	private SimpleDateFormat dateTimeFormat;
	@Autowired
	private FunctionalError functionalError;
	@Autowired
	private StatusRepository statusRepository;
	private Response<AtionToNotifiableDto> response;
	@Autowired
	private ActionSimswapRepository actionParametrableRepository;
	@Autowired
	private AtionToNotifiableRepository ationToNotifiableRepository;

	public AtionToNotifiableBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create AtionToNotifiable by using AtionToNotifiableDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<AtionToNotifiableDto> create(Request<AtionToNotifiableDto> request, Locale locale)  throws ParseException {
		log.info("----begin create AtionToNotifiable-----");

		Response<AtionToNotifiableDto> response = new Response<AtionToNotifiableDto>();
		List<AtionToNotifiable>        items    = new ArrayList<AtionToNotifiable>();
			
		for (AtionToNotifiableDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("idStatus", dto.getIdStatus());
			fieldsToVerify.put("idUserDemand", dto.getIdUserDemand());
			fieldsToVerify.put("idUserValid", dto.getIdUserValid());
			fieldsToVerify.put("numero", dto.getNumero());
			fieldsToVerify.put("isNotify", dto.getIsNotify());
			fieldsToVerify.put("isEnMasse", dto.getIsEnMasse());
			fieldsToVerify.put("statutAction", dto.getStatutAction());
			fieldsToVerify.put("idAction", dto.getIdAction());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if ationToNotifiable to insert do not exist
			AtionToNotifiable existingEntity = null;
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("ationToNotifiable id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// verif unique numero in db
			existingEntity = ationToNotifiableRepository.findByNumero(dto.getNumero(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("ationToNotifiable numero -> " + dto.getNumero(), locale));
				response.setHasError(true);
				return response;
			}
			// verif unique numero in items to save
			if (items.stream().anyMatch(a -> a.getNumero().equalsIgnoreCase(dto.getNumero()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" numero ", locale));
				response.setHasError(true);
				return response;
			}

			// Verify if user exist
			User existingUser = null;
			if (dto.getIdUserDemand() != null && dto.getIdUserDemand() > 0){
				existingUser = userRepository.findOne(dto.getIdUserDemand(), false);
				if (existingUser == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("user idUserDemand -> " + dto.getIdUserDemand(), locale));
					response.setHasError(true);
					return response;
				}
			}
			// Verify if actionParametrable exist
			ActionSimswap existingActionParametrable = null;
			if (dto.getIdAction() != null && dto.getIdAction() > 0){
				existingActionParametrable = actionParametrableRepository.findOne(dto.getIdAction(), false);
				if (existingActionParametrable == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("actionParametrable idAction -> " + dto.getIdAction(), locale));
					response.setHasError(true);
					return response;
				}
			}
			// Verify if status exist
			Status existingStatus = null;
			if (dto.getIdStatus() != null && dto.getIdStatus() > 0){
				existingStatus = statusRepository.findOne(dto.getIdStatus(), false);
				if (existingStatus == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("status idStatus -> " + dto.getIdStatus(), locale));
					response.setHasError(true);
					return response;
				}
			}
			// Verify if user2 exist
			User existingUser2 = null;
			if (dto.getIdUserValid() != null && dto.getIdUserValid() > 0){
				existingUser2 = user2Repository.findOne(dto.getIdUserValid(), false);
				if (existingUser2 == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("user2 idUserValid -> " + dto.getIdUserValid(), locale));
					response.setHasError(true);
					return response;
				}
			}
				AtionToNotifiable entityToSave = null;
			entityToSave = AtionToNotifiableTransformer.INSTANCE.toEntity(dto, existingUser, existingActionParametrable, existingStatus, existingUser2);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<AtionToNotifiable> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = ationToNotifiableRepository.saveAll(items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("ationToNotifiable", locale));
				response.setHasError(true);
				return response;
			}
			List<AtionToNotifiableDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? AtionToNotifiableTransformer.INSTANCE.toLiteDtos(itemsSaved) : AtionToNotifiableTransformer.INSTANCE.toDtos(itemsSaved);

			final int size = itemsSaved.size();
			List<String>  listOfError      = Collections.synchronizedList(new ArrayList<>());
			itemsDto.parallelStream().forEach(dto -> {
				try {
					dto = getFullInfos(dto, size, request.getIsSimpleLoading(), locale);
				} catch (Exception e) {
					listOfError.add(e.getMessage());
					e.printStackTrace();
				}
			});
			if (Utilities.isNotEmpty(listOfError)) {
				Object[] objArray = listOfError.stream().distinct().toArray();
				throw new RuntimeException(StringUtils.join(objArray, ", "));
			}
			response.setStatus(functionalError.SUCCESS("", locale));
			response.setItems(itemsDto);
			response.setHasError(false);
		}

		log.info("----end create AtionToNotifiable-----");
		return response;
	}

	/**
	 * update AtionToNotifiable by using AtionToNotifiableDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<AtionToNotifiableDto> update(Request<AtionToNotifiableDto> request, Locale locale)  throws ParseException {
		log.info("----begin update AtionToNotifiable-----");

		Response<AtionToNotifiableDto> response = new Response<AtionToNotifiableDto>();
		List<AtionToNotifiable>        items    = new ArrayList<AtionToNotifiable>();
			
		for (AtionToNotifiableDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la ationToNotifiable existe
			AtionToNotifiable entityToSave = null;
			entityToSave = ationToNotifiableRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("ationToNotifiable id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}
			// Verify if user exist
			if (dto.getIdUserDemand() != null && dto.getIdUserDemand() > 0){
				User existingUser = userRepository.findOne(dto.getIdUserDemand(), false);
				if (existingUser == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("user idUserDemand -> " + dto.getIdUserDemand(), locale));
					response.setHasError(true);
					return response;
				}
				entityToSave.setUser(existingUser);
			}
			// Verify if actionParametrable exist
			if (dto.getIdAction() != null && dto.getIdAction() > 0){
				ActionSimswap existingActionParametrable = actionParametrableRepository.findOne(dto.getIdAction(), false);
				if (existingActionParametrable == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("actionParametrable idAction -> " + dto.getIdAction(), locale));
					response.setHasError(true);
					return response;
				}
				entityToSave.setActionSimswap(existingActionParametrable);
			}
			// Verify if status exist
			if (dto.getIdStatus() != null && dto.getIdStatus() > 0){
				Status existingStatus = statusRepository.findOne(dto.getIdStatus(), false);
				if (existingStatus == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("status idStatus -> " + dto.getIdStatus(), locale));
					response.setHasError(true);
					return response;
				}
				entityToSave.setStatus(existingStatus);
			}
			// Verify if user2 exist
			if (dto.getIdUserValid() != null && dto.getIdUserValid() > 0){
				User existingUser2 = user2Repository.findOne(dto.getIdUserValid(), false);
				if (existingUser2 == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("user2 idUserValid -> " + dto.getIdUserValid(), locale));
					response.setHasError(true);
					return response;
				}
				entityToSave.setUser2(existingUser2);
			}
			if (Utilities.notBlank(dto.getNumero())) {
				entityToSave.setNumero(dto.getNumero());
			}
			if (dto.getIsNotify() != null) {
				entityToSave.setIsNotify(dto.getIsNotify());
			}
			if (dto.getIsEnMasse() != null) {
				entityToSave.setIsEnMasse(dto.getIsEnMasse());
			}
			if (Utilities.notBlank(dto.getStatutAction())) {
				entityToSave.setStatutAction(dto.getStatutAction());
			}
			if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
				entityToSave.setCreatedBy(dto.getCreatedBy());
			}
			if (dto.getUpdatedBy() != null && dto.getUpdatedBy() > 0) {
				entityToSave.setUpdatedBy(dto.getUpdatedBy());
			}
			entityToSave.setUpdatedAt(Utilities.getCurrentDate());
			entityToSave.setUpdatedBy(request.getUser());
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<AtionToNotifiable> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = ationToNotifiableRepository.saveAll((Iterable<AtionToNotifiable>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("ationToNotifiable", locale));
				response.setHasError(true);
				return response;
			}
			List<AtionToNotifiableDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? AtionToNotifiableTransformer.INSTANCE.toLiteDtos(itemsSaved) : AtionToNotifiableTransformer.INSTANCE.toDtos(itemsSaved);

			final int size = itemsSaved.size();
			List<String>  listOfError      = Collections.synchronizedList(new ArrayList<String>());
			itemsDto.parallelStream().forEach(dto -> {
				try {
					dto = getFullInfos(dto, size, request.getIsSimpleLoading(), locale);
				} catch (Exception e) {
					listOfError.add(e.getMessage());
					e.printStackTrace();
				}
			});
			if (Utilities.isNotEmpty(listOfError)) {
				Object[] objArray = listOfError.stream().distinct().toArray();
				throw new RuntimeException(StringUtils.join(objArray, ", "));
			}
			response.setStatus(functionalError.SUCCESS("", locale));
			response.setItems(itemsDto);
			response.setHasError(false);
		}

		log.info("----end update AtionToNotifiable-----");
		return response;
	}

	/**
	 * delete AtionToNotifiable by using AtionToNotifiableDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<AtionToNotifiableDto> delete(Request<AtionToNotifiableDto> request, Locale locale)  {
		log.info("----begin delete AtionToNotifiable-----");

		Response<AtionToNotifiableDto> response = new Response<AtionToNotifiableDto>();
		List<AtionToNotifiable>        items    = new ArrayList<AtionToNotifiable>();
			
		for (AtionToNotifiableDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la ationToNotifiable existe
			AtionToNotifiable existingEntity = null;
			existingEntity = ationToNotifiableRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("ationToNotifiable -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------



			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			ationToNotifiableRepository.saveAll((Iterable<AtionToNotifiable>) items);
			response.setStatus(functionalError.SUCCESS("", locale));

			response.setHasError(false);
		}

		log.info("----end delete AtionToNotifiable-----");
		return response;
	}

	/**
	 * get AtionToNotifiable by using AtionToNotifiableDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<AtionToNotifiableDto> getByCriteria(Request<AtionToNotifiableDto> request, Locale locale)  throws Exception {
		log.info("----begin get AtionToNotifiable-----");
		Response<AtionToNotifiableDto> response = new Response<AtionToNotifiableDto>();
		List<AtionToNotifiable> items 			 = ationToNotifiableRepository.getByCriteria(request, em, locale);
		if (items != null && !items.isEmpty()) {
			List<AtionToNotifiableDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? AtionToNotifiableTransformer.INSTANCE.toLiteDtos(items) : AtionToNotifiableTransformer.INSTANCE.toDtos(items);
			final int size = items.size();
			List<String>  listOfError      = Collections.synchronizedList(new ArrayList<String>());
			itemsDto.parallelStream().forEach(dto -> {
				try {
					dto = getFullInfos(dto, size, request.getIsSimpleLoading(), locale);
				} catch (Exception e) {
					listOfError.add(e.getMessage());
					e.printStackTrace();
				}
			});
			if (Utilities.isNotEmpty(listOfError)) {
				Object[] objArray = listOfError.stream().distinct().toArray();
				throw new RuntimeException(StringUtils.join(objArray, ", "));
			}
			response.setItems(itemsDto);
			response.setCount(ationToNotifiableRepository.count(request, em, locale));
			response.setHasError(false);
			response.setStatus(functionalError.SUCCESS("", locale));
		} else {
			response.setStatus(functionalError.DATA_EMPTY("ationToNotifiable", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get AtionToNotifiable-----");
		return response;
	}

	/**
	 * get full AtionToNotifiableDto by using AtionToNotifiable as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private AtionToNotifiableDto getFullInfos(AtionToNotifiableDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	public Response<AtionToNotifiableDto> custom(Request<AtionToNotifiableDto> request, Locale locale) {
		log.info("----begin custom AtionToNotifiableDto-----");
		Response<AtionToNotifiableDto> response = new Response<AtionToNotifiableDto>();
		
		response.setHasError(false);
		response.setCount(1L);
		response.setItems(Arrays.asList(new AtionToNotifiableDto()));
		log.info("----end custom AtionToNotifiableDto-----");
		return response;
	}
}
