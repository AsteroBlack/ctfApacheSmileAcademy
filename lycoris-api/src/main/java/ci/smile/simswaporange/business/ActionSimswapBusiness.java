                            							
/*
 * Java business for entity table action_simswap 
 * Created on 2023-07-04 ( Time 12:38:30 )
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
import ci.smile.simswaporange.dao.entity.ActionSimswap;
import ci.smile.simswaporange.dao.entity.*;
import ci.smile.simswaporange.dao.repository.*;

/**
BUSINESS for table "action_simswap"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class ActionSimswapBusiness implements IBasicBusiness<Request<ActionSimswapDto>, Response<ActionSimswapDto>> {

	private Response<ActionSimswapDto> response;
	@Autowired
	private ActionSimswapRepository actionSimswapRepository;
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

	public ActionSimswapBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create ActionSimswap by using ActionSimswapDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ActionSimswapDto> create(Request<ActionSimswapDto> request, Locale locale)  throws ParseException {
		log.info("----begin create ActionSimswap-----");

		Response<ActionSimswapDto> response = new Response<ActionSimswapDto>();
		List<ActionSimswap>        items    = new ArrayList<ActionSimswap>();
			
		for (ActionSimswapDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("libelle", dto.getLibelle());
			fieldsToVerify.put("uri", dto.getUri());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if actionSimswap to insert do not exist
			ActionSimswap existingEntity = null;

			// verif unique libelle in db
			existingEntity = actionSimswapRepository.findByLibelle(dto.getLibelle(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("actionSimswap libelle -> " + dto.getLibelle(), locale));
				response.setHasError(true);
				return response;
			}
			// verif unique libelle in items to save
			if (items.stream().anyMatch(a -> a.getLibelle().equalsIgnoreCase(dto.getLibelle()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" libelle ", locale));
				response.setHasError(true);
				return response;
			}

				ActionSimswap entityToSave = null;
			entityToSave = ActionSimswapTransformer.INSTANCE.toEntity(dto);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setIsDeleted(false);
			entityToSave.setCreatedBy(request.getUser());
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<ActionSimswap> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = actionSimswapRepository.saveAll((Iterable<ActionSimswap>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("actionSimswap", locale));
				response.setHasError(true);
				return response;
			}
			List<ActionSimswapDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ActionSimswapTransformer.INSTANCE.toLiteDtos(itemsSaved) : ActionSimswapTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create ActionSimswap-----");
		return response;
	}

	/**
	 * update ActionSimswap by using ActionSimswapDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ActionSimswapDto> update(Request<ActionSimswapDto> request, Locale locale)  throws ParseException {
		log.info("----begin update ActionSimswap-----");

		Response<ActionSimswapDto> response = new Response<ActionSimswapDto>();
		List<ActionSimswap>        items    = new ArrayList<ActionSimswap>();
			
		for (ActionSimswapDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la actionSimswap existe
			ActionSimswap entityToSave = null;
			entityToSave = actionSimswapRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("actionSimswap id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
				entityToSave.setCreatedBy(dto.getCreatedBy());
			}
			if (Utilities.notBlank(dto.getLibelle())) {
				entityToSave.setLibelle(dto.getLibelle());
			}
			entityToSave.setUpdatedAt(Utilities.getCurrentDate());
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<ActionSimswap> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = actionSimswapRepository.saveAll((Iterable<ActionSimswap>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("actionSimswap", locale));
				response.setHasError(true);
				return response;
			}
			List<ActionSimswapDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ActionSimswapTransformer.INSTANCE.toLiteDtos(itemsSaved) : ActionSimswapTransformer.INSTANCE.toDtos(itemsSaved);

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
			response.setStatus(functionalError.SUCCESS("", locale));
		}

		log.info("----end update ActionSimswap-----");
		return response;
	}

	/**
	 * delete ActionSimswap by using ActionSimswapDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ActionSimswapDto> delete(Request<ActionSimswapDto> request, Locale locale)  {
		log.info("----begin delete ActionSimswap-----");

		Response<ActionSimswapDto> response = new Response<ActionSimswapDto>();
		List<ActionSimswap>        items    = new ArrayList<ActionSimswap>();
			
		for (ActionSimswapDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la actionSimswap existe
			ActionSimswap existingEntity = null;
			existingEntity = actionSimswapRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("actionSimswap -> " + dto.getId(), locale));
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
			actionSimswapRepository.saveAll((Iterable<ActionSimswap>) items);
			response.setStatus(functionalError.SUCCESS("", locale));
			response.setHasError(false);
		}

		log.info("----end delete ActionSimswap-----");
		return response;
	}

	/**
	 * get ActionSimswap by using ActionSimswapDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ActionSimswapDto> getByCriteria(Request<ActionSimswapDto> request, Locale locale)  throws Exception {
		log.info("----begin get ActionSimswap-----");

		Response<ActionSimswapDto> response = new Response<ActionSimswapDto>();
		List<ActionSimswap> items 			 = actionSimswapRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<ActionSimswapDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ActionSimswapTransformer.INSTANCE.toLiteDtos(items) : ActionSimswapTransformer.INSTANCE.toDtos(items);

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
			response.setCount(actionSimswapRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("actionSimswap", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get ActionSimswap-----");
		return response;
	}

	/**
	 * get full ActionSimswapDto by using ActionSimswap as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private ActionSimswapDto getFullInfos(ActionSimswapDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	public Response<ActionSimswapDto> custom(Request<ActionSimswapDto> request, Locale locale) {
		log.info("----begin custom ActionSimswapDto-----");
		Response<ActionSimswapDto> response = new Response<ActionSimswapDto>();
		
		response.setHasError(false);
		response.setCount(1L);
		response.setItems(Arrays.asList(new ActionSimswapDto()));
		log.info("----end custom ActionSimswapDto-----");
		return response;
	}
}
