                            							
/*
 * Java business for entity table type_parametrage 
 * Created on 2023-06-21 ( Time 19:28:14 )
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
import ci.smile.simswaporange.dao.entity.TypeParametrage;
import ci.smile.simswaporange.dao.entity.*;
import ci.smile.simswaporange.dao.repository.*;

/**
BUSINESS for table "type_parametrage"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class TypeParametrageBusiness implements IBasicBusiness<Request<TypeParametrageDto>, Response<TypeParametrageDto>> {

	private Response<TypeParametrageDto> response;
	@Autowired
	private TypeParametrageRepository typeParametrageRepository;
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

	public TypeParametrageBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create TypeParametrage by using TypeParametrageDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<TypeParametrageDto> create(Request<TypeParametrageDto> request, Locale locale)  throws ParseException {
		log.info("----begin create TypeParametrage-----");

		Response<TypeParametrageDto> response = new Response<TypeParametrageDto>();
		List<TypeParametrage>        items    = new ArrayList<TypeParametrage>();
			
		for (TypeParametrageDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("code", dto.getCode());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if typeParametrage to insert do not exist
			TypeParametrage existingEntity = null;
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("typeParametrage id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// verif unique code in db
			existingEntity = typeParametrageRepository.findByCode(dto.getCode(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("typeParametrage code -> " + dto.getCode(), locale));
				response.setHasError(true);
				return response;
			}
			// verif unique code in items to save
			if (items.stream().anyMatch(a -> a.getCode().equalsIgnoreCase(dto.getCode()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" code ", locale));
				response.setHasError(true);
				return response;
			}

				TypeParametrage entityToSave = null;
			entityToSave = TypeParametrageTransformer.INSTANCE.toEntity(dto);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setIsDeleted(false);
			entityToSave.setCreatedBy(request.getUser());
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<TypeParametrage> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = typeParametrageRepository.saveAll((Iterable<TypeParametrage>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("typeParametrage", locale));
				response.setHasError(true);
				return response;
			}
			List<TypeParametrageDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? TypeParametrageTransformer.INSTANCE.toLiteDtos(itemsSaved) : TypeParametrageTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create TypeParametrage-----");
		return response;
	}

	/**
	 * update TypeParametrage by using TypeParametrageDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<TypeParametrageDto> update(Request<TypeParametrageDto> request, Locale locale)  throws ParseException {
		log.info("----begin update TypeParametrage-----");

		Response<TypeParametrageDto> response = new Response<TypeParametrageDto>();
		List<TypeParametrage>        items    = new ArrayList<TypeParametrage>();
			
		for (TypeParametrageDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la typeParametrage existe
			TypeParametrage entityToSave = null;
			entityToSave = typeParametrageRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("typeParametrage id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			if (Utilities.notBlank(dto.getCode())) {
				entityToSave.setCode(dto.getCode());
			}
			if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
				entityToSave.setCreatedBy(dto.getCreatedBy());
			}
			entityToSave.setUpdatedAt(Utilities.getCurrentDate());
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<TypeParametrage> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = typeParametrageRepository.saveAll((Iterable<TypeParametrage>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("typeParametrage", locale));
				response.setHasError(true);
				return response;
			}
			List<TypeParametrageDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? TypeParametrageTransformer.INSTANCE.toLiteDtos(itemsSaved) : TypeParametrageTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update TypeParametrage-----");
		return response;
	}

	/**
	 * delete TypeParametrage by using TypeParametrageDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<TypeParametrageDto> delete(Request<TypeParametrageDto> request, Locale locale)  {
		log.info("----begin delete TypeParametrage-----");

		Response<TypeParametrageDto> response = new Response<TypeParametrageDto>();
		List<TypeParametrage>        items    = new ArrayList<TypeParametrage>();
			
		for (TypeParametrageDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la typeParametrage existe
			TypeParametrage existingEntity = null;
			existingEntity = typeParametrageRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("typeParametrage -> " + dto.getId(), locale));
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
			typeParametrageRepository.saveAll((Iterable<TypeParametrage>) items);
			response.setStatus(functionalError.SUCCESS("", locale));
			response.setHasError(false);
		}

		log.info("----end delete TypeParametrage-----");
		return response;
	}

	/**
	 * get TypeParametrage by using TypeParametrageDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<TypeParametrageDto> getByCriteria(Request<TypeParametrageDto> request, Locale locale)  throws Exception {
		log.info("----begin get TypeParametrage-----");

		Response<TypeParametrageDto> response = new Response<TypeParametrageDto>();
		List<TypeParametrage> items 			 = typeParametrageRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<TypeParametrageDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? TypeParametrageTransformer.INSTANCE.toLiteDtos(items) : TypeParametrageTransformer.INSTANCE.toDtos(items);

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
			response.setCount(typeParametrageRepository.count(request, em, locale));
			response.setHasError(false);
			response.setStatus(functionalError.SUCCESS("", locale));
		} else {
			response.setStatus(functionalError.DATA_EMPTY("typeParametrage", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get TypeParametrage-----");
		return response;
	}

	/**
	 * get full TypeParametrageDto by using TypeParametrage as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private TypeParametrageDto getFullInfos(TypeParametrageDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	public Response<TypeParametrageDto> custom(Request<TypeParametrageDto> request, Locale locale) {
		log.info("----begin custom TypeParametrageDto-----");
		Response<TypeParametrageDto> response = new Response<TypeParametrageDto>();
		
		response.setHasError(false);
		response.setCount(1L);
		response.setItems(Arrays.asList(new TypeParametrageDto()));
		log.info("----end custom TypeParametrageDto-----");
		return response;
	}
}
