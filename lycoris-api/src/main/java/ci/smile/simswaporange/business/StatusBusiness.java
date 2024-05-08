
/*
 * Java business for entity table status
 * Created on 2022-06-28 ( Time 15:22:06 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.business;

import lombok.RequiredArgsConstructor;
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
import ci.smile.simswaporange.dao.entity.Status;
import ci.smile.simswaporange.dao.entity.*;
import ci.smile.simswaporange.dao.repository.*;

/**
 BUSINESS for table "status"
 *
 * @author Geo
 *
 */
@Log
@Component
@RequiredArgsConstructor
public class StatusBusiness implements IBasicBusiness<Request<StatusDto>, Response<StatusDto>> {

	private Response<StatusDto> response;
	private final StatusRepository statusRepository;
	private final ActionUtilisateurRepository actionUtilisateurRepository;
	private final FunctionalError functionalError;
	private final TechnicalError technicalError;
	private final ExceptionUtils exceptionUtils;
	@PersistenceContext
	private EntityManager em;

/*	private SimpleDateFormat dateFormat;
	private SimpleDateFormat dateTimeFormat;

	public StatusBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}*/

	/**
	 * create Status by using StatusDto as object.
	 *
	 * @param request
	 * @return response
	 *
	 */
	@Override
	public Response<StatusDto> create(Request<StatusDto> request, Locale locale)  throws ParseException {
		log.info("----begin create Status-----");

		Response<StatusDto> response = new Response<StatusDto>();
		List<Status>        items    = new ArrayList<Status>();

		for (StatusDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("code", dto.getCode());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if status to insert do not exist
			Status existingEntity = null;

			// verif unique code in db
			existingEntity = statusRepository.findByCode(dto.getCode(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("status code -> " + dto.getCode(), locale));
				response.setHasError(true);
				return response;
			}
			// verif unique code in items to save
			if (items.stream().anyMatch(a -> a.getCode().equalsIgnoreCase(dto.getCode()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" code ", locale));
				response.setHasError(true);
				return response;
			}

			Status entityToSave = null;
			entityToSave = StatusTransformer.INSTANCE.toEntity(dto);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<Status> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = statusRepository.saveAll((Iterable<Status>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("status", locale));
				response.setHasError(true);
				return response;
			}
			List<StatusDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? StatusTransformer.INSTANCE.toLiteDtos(itemsSaved) : StatusTransformer.INSTANCE.toDtos(itemsSaved);

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
			response.setItems(itemsDto);
			response.setHasError(false);
		}
		response.setStatus(functionalError.SUCCESS("operation effectué", locale));
		log.info("----end create Status-----");
		response.setActionEffectue("Creation d'un status");
		return response;
	}


	/**
	 * update Status by using StatusDto as object.
	 *
	 * @param request
	 * @return response
	 *
	 */
	@Override
	public Response<StatusDto> update(Request<StatusDto> request, Locale locale)  throws ParseException {
		log.info("----begin update Status-----");

		Response<StatusDto> response = new Response<StatusDto>();
		List<Status>        items    = new ArrayList<Status>();

		for (StatusDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la status existe
			Status entityToSave = null;
			entityToSave = statusRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("status id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			if (Utilities.notBlank(dto.getCode())) {
				entityToSave.setCode(dto.getCode());
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
			List<Status> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = statusRepository.saveAll((Iterable<Status>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("status", locale));
				response.setHasError(true);
				return response;
			}
			List<StatusDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? StatusTransformer.INSTANCE.toLiteDtos(itemsSaved) : StatusTransformer.INSTANCE.toDtos(itemsSaved);

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
			response.setItems(itemsDto);
			response.setHasError(false);
		}
		response.setStatus(functionalError.SUCCESS("operation effectué", locale));
		log.info("----end update Status-----");
		response.setActionEffectue("Mise à jour d'un status");
		return response;
	}

	/**
	 * delete Status by using StatusDto as object.
	 *
	 * @param request
	 * @return response
	 *
	 */
	@Override
	public Response<StatusDto> delete(Request<StatusDto> request, Locale locale)  {
		log.info("----begin delete Status-----");

		Response<StatusDto> response = new Response<StatusDto>();
		List<Status>        items    = new ArrayList<Status>();

		for (StatusDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la status existe
			Status existingEntity = null;
			existingEntity = statusRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("status -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------

			// actionUtilisateur
			List<ActionUtilisateur> listOfActionUtilisateur = actionUtilisateurRepository.findByIdStatus(existingEntity.getId(), false);
			if (listOfActionUtilisateur != null && !listOfActionUtilisateur.isEmpty()){
				response.setStatus(functionalError.DATA_NOT_DELETABLE("(" + listOfActionUtilisateur.size() + ")", locale));
				response.setHasError(true);
				return response;
			}


			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			statusRepository.saveAll((Iterable<Status>) items);

			response.setHasError(false);
		}
		response.setStatus(functionalError.SUCCESS("operation effectué", locale));
		log.info("----end delete Status-----");
		response.setActionEffectue("Suppression d'un status");
		return response;
	}

	/**
	 * get Status by using StatusDto as object.
	 *
	 * @param request
	 * @return response
	 *
	 */
	@Override
	public Response<StatusDto> getByCriteria(Request<StatusDto> request, Locale locale)  throws Exception {
		log.info("----begin get Status-----");

		Response<StatusDto> response = new Response<StatusDto>();
		List<Status> items 			 = statusRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<StatusDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? StatusTransformer.INSTANCE.toLiteDtos(items) : StatusTransformer.INSTANCE.toDtos(items);

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
			response.setCount(statusRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("status", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get Status-----");
		return response;
	}



	/**
	 * get full StatusDto by using Status as object.
	 *
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private StatusDto getFullInfos(StatusDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}



	public Response<StatusDto> custom(Request<StatusDto> request, Locale locale) {
		log.info("----begin custom StatusDto-----");
		Response<StatusDto> response = new Response<StatusDto>();

		response.setHasError(false);
		response.setCount(1L);
		response.setItems(Arrays.asList(new StatusDto()));
		log.info("----end custom StatusDto-----");
		return response;
	}


}
