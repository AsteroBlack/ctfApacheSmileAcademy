                                    								
/*
 * Java business for entity table action_parametrable 
 * Created on 2023-06-29 ( Time 14:01:11 )
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
import ci.smile.simswaporange.dao.entity.ActionParametrable;
import ci.smile.simswaporange.dao.entity.*;
import ci.smile.simswaporange.dao.repository.*;

/**
BUSINESS for table "action_parametrable"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class ActionParametrableBusiness implements IBasicBusiness<Request<ActionParametrableDto>, Response<ActionParametrableDto>> {

	private Response<ActionParametrableDto> response;
	@Autowired
	private ActionParametrableRepository actionParametrableRepository;
	@Autowired
	private FunctionalError functionalError;
	@Autowired
	private TechnicalError technicalError;
	@Autowired
	private ExceptionUtils exceptionUtils;
	@PersistenceContext
	private EntityManager em;

	private SimpleDateFormat dateFormat;
	private SimpleDateFormat dateTimeFormat;

	public ActionParametrableBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create ActionParametrable by using ActionParametrableDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ActionParametrableDto> create(Request<ActionParametrableDto> request, Locale locale)  throws ParseException {
		log.info("----begin create ActionParametrable-----");

		Response<ActionParametrableDto> response = new Response<ActionParametrableDto>();
		List<ActionParametrable>        items    = new ArrayList<ActionParametrable>();
			
		for (ActionParametrableDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("libelle", dto.getLibelle());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if actionParametrable to insert do not exist
			ActionParametrable existingEntity = null;

			// verif unique libelle in db
			existingEntity = actionParametrableRepository.findByLibelle(dto.getLibelle(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("actionParametrable libelle -> " + dto.getLibelle(), locale));
				response.setHasError(true);
				return response;
			}
			// verif unique libelle in items to save
			if (items.stream().anyMatch(a -> a.getLibelle().equalsIgnoreCase(dto.getLibelle()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" libelle ", locale));
				response.setHasError(true);
				return response;
			}

				ActionParametrable entityToSave = null;
			entityToSave = ActionParametrableTransformer.INSTANCE.toEntity(dto);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<ActionParametrable> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = actionParametrableRepository.saveAll((Iterable<ActionParametrable>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("actionParametrable", locale));
				response.setHasError(true);
				return response;
			}
			List<ActionParametrableDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ActionParametrableTransformer.INSTANCE.toLiteDtos(itemsSaved) : ActionParametrableTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create ActionParametrable-----");
		return response;
	}

	/**
	 * update ActionParametrable by using ActionParametrableDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ActionParametrableDto> update(Request<ActionParametrableDto> request, Locale locale)  throws ParseException {
		log.info("----begin update ActionParametrable-----");

		Response<ActionParametrableDto> response = new Response<ActionParametrableDto>();
		List<ActionParametrable>        items    = new ArrayList<ActionParametrable>();
			
		for (ActionParametrableDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la actionParametrable existe
			ActionParametrable entityToSave = null;
			entityToSave = actionParametrableRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("actionParametrable id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			if (Utilities.notBlank(dto.getLibelle())) {
				entityToSave.setLibelle(dto.getLibelle());
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
			List<ActionParametrable> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = actionParametrableRepository.saveAll((Iterable<ActionParametrable>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("actionParametrable", locale));
				response.setHasError(true);
				return response;
			}
			List<ActionParametrableDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ActionParametrableTransformer.INSTANCE.toLiteDtos(itemsSaved) : ActionParametrableTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update ActionParametrable-----");
		return response;
	}

	/**
	 * delete ActionParametrable by using ActionParametrableDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ActionParametrableDto> delete(Request<ActionParametrableDto> request, Locale locale)  {
		log.info("----begin delete ActionParametrable-----");

		Response<ActionParametrableDto> response = new Response<ActionParametrableDto>();
		List<ActionParametrable>        items    = new ArrayList<ActionParametrable>();
			
		for (ActionParametrableDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la actionParametrable existe
			ActionParametrable existingEntity = null;
			existingEntity = actionParametrableRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("actionParametrable -> " + dto.getId(), locale));
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
			actionParametrableRepository.saveAll((Iterable<ActionParametrable>) items);
			response.setStatus(functionalError.SUCCESS("", locale));
			response.setHasError(false);
		}

		log.info("----end delete ActionParametrable-----");
		return response;
	}

	/**
	 * get ActionParametrable by using ActionParametrableDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ActionParametrableDto> getByCriteria(Request<ActionParametrableDto> request, Locale locale)  throws Exception {
		log.info("----begin get ActionParametrable-----");

		Response<ActionParametrableDto> response = new Response<ActionParametrableDto>();
		List<ActionParametrable> items 			 = actionParametrableRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<ActionParametrableDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ActionParametrableTransformer.INSTANCE.toLiteDtos(items) : ActionParametrableTransformer.INSTANCE.toDtos(items);

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
			response.setStatus(functionalError.SUCCESS("", locale));
			response.setCount(actionParametrableRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("actionParametrable", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get ActionParametrable-----");
		return response;
	}

	/**
	 * get full ActionParametrableDto by using ActionParametrable as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private ActionParametrableDto getFullInfos(ActionParametrableDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	public Response<ActionParametrableDto> custom(Request<ActionParametrableDto> request, Locale locale) {
		log.info("----begin custom ActionParametrableDto-----");
		Response<ActionParametrableDto> response = new Response<ActionParametrableDto>();
		
		response.setHasError(false);
		response.setCount(1L);
		response.setItems(Arrays.asList(new ActionParametrableDto()));
		log.info("----end custom ActionParametrableDto-----");
		return response;
	}
}
