                                    								
/*
 * Java business for entity table civilite 
 * Created on 2022-07-01 ( Time 17:34:13 )
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
import ci.smile.simswaporange.dao.entity.Civilite;
import ci.smile.simswaporange.dao.entity.*;
import ci.smile.simswaporange.dao.repository.*;

/**
BUSINESS for table "civilite"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class CiviliteBusiness implements IBasicBusiness<Request<CiviliteDto>, Response<CiviliteDto>> {

	@PersistenceContext
	private EntityManager em;
	private SimpleDateFormat dateFormat;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TechnicalError technicalError;
	@Autowired
	private ExceptionUtils exceptionUtils;
	private Response<CiviliteDto> response;
	private SimpleDateFormat dateTimeFormat;
	@Autowired
	private FunctionalError functionalError;
	@Autowired
	private CiviliteRepository civiliteRepository;


	public CiviliteBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create Civilite by using CiviliteDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<CiviliteDto> create(Request<CiviliteDto> request, Locale locale)  throws ParseException {
		log.info("----begin create Civilite-----");

		Response<CiviliteDto> response = new Response<CiviliteDto>();
		List<Civilite>        items    = new ArrayList<Civilite>();
			
		for (CiviliteDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("libelle", dto.getLibelle());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if civilite to insert do not exist
			Civilite existingEntity = null;
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("civilite id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// verif unique libelle in db
			existingEntity = civiliteRepository.findByLibelle(dto.getLibelle(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("civilite libelle -> " + dto.getLibelle(), locale));
				response.setHasError(true);
				return response;
			}
			// verif unique libelle in items to save
			if (items.stream().anyMatch(a -> a.getLibelle().equalsIgnoreCase(dto.getLibelle()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" libelle ", locale));
				response.setHasError(true);
				return response;
			}

				Civilite entityToSave = null;
			entityToSave = CiviliteTransformer.INSTANCE.toEntity(dto);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<Civilite> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = civiliteRepository.saveAll((Iterable<Civilite>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("civilite", locale));
				response.setHasError(true);
				return response;
			}
			List<CiviliteDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? CiviliteTransformer.INSTANCE.toLiteDtos(itemsSaved) : CiviliteTransformer.INSTANCE.toDtos(itemsSaved);

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
		log.info("----end create Civilite-----");
		return response;
	}

	/**
	 * update Civilite by using CiviliteDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<CiviliteDto> update(Request<CiviliteDto> request, Locale locale)  throws ParseException {
		log.info("----begin update Civilite-----");

		Response<CiviliteDto> response = new Response<CiviliteDto>();
		List<Civilite>        items    = new ArrayList<Civilite>();
			
		for (CiviliteDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la civilite existe
			Civilite entityToSave = null;
			entityToSave = civiliteRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("civilite id -> " + dto.getId(), locale));
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
			List<Civilite> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = civiliteRepository.saveAll((Iterable<Civilite>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("civilite", locale));
				response.setHasError(true);
				return response;
			}
			List<CiviliteDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? CiviliteTransformer.INSTANCE.toLiteDtos(itemsSaved) : CiviliteTransformer.INSTANCE.toDtos(itemsSaved);

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
		log.info("----end update Civilite-----");
		return response;
	}

	/**
	 * delete Civilite by using CiviliteDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<CiviliteDto> delete(Request<CiviliteDto> request, Locale locale)  {
		log.info("----begin delete Civilite-----");

		Response<CiviliteDto> response = new Response<CiviliteDto>();
		List<Civilite>        items    = new ArrayList<Civilite>();
			
		for (CiviliteDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la civilite existe
			Civilite existingEntity = null;
			existingEntity = civiliteRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("civilite -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------

			// user
			List<User> listOfUser = userRepository.findByIdCivilite(existingEntity.getId(), false);
			if (listOfUser != null && !listOfUser.isEmpty()){
				response.setStatus(functionalError.DATA_NOT_DELETABLE("(" + listOfUser.size() + ")", locale));
				response.setHasError(true);
				return response;
			}


			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			civiliteRepository.saveAll((Iterable<Civilite>) items);

			response.setHasError(false);
		}
		response.setStatus(functionalError.SUCCESS("operation effectué", locale));
		log.info("----end delete Civilite-----");
		return response;
	}

	/**
	 * get Civilite by using CiviliteDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<CiviliteDto> getByCriteria(Request<CiviliteDto> request, Locale locale)  throws Exception {
		log.info("----begin get Civilite-----");

		Response<CiviliteDto> response = new Response<CiviliteDto>();
		List<Civilite> items 			 = civiliteRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<CiviliteDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? CiviliteTransformer.INSTANCE.toLiteDtos(items) : CiviliteTransformer.INSTANCE.toDtos(items);

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
			response.setCount(civiliteRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("civilite", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get Civilite-----");
		return response;
	}

	/**
	 * get full CiviliteDto by using Civilite as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private CiviliteDto getFullInfos(CiviliteDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	public Response<CiviliteDto> custom(Request<CiviliteDto> request, Locale locale) {
		log.info("----begin custom CiviliteDto-----");
		Response<CiviliteDto> response = new Response<CiviliteDto>();
		
		response.setHasError(false);
		response.setCount(1L);
		response.setItems(Arrays.asList(new CiviliteDto()));
		log.info("----end custom CiviliteDto-----");
		return response;
	}
}
