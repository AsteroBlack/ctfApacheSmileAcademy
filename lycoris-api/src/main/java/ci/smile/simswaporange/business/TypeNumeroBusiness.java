                                    								
/*
 * Java business for entity table type_numero 
 * Created on 2023-06-27 ( Time 13:54:55 )
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
import ci.smile.simswaporange.dao.entity.TypeNumero;
import ci.smile.simswaporange.dao.entity.*;
import ci.smile.simswaporange.dao.repository.*;

/**
BUSINESS for table "type_numero"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class TypeNumeroBusiness implements IBasicBusiness<Request<TypeNumeroDto>, Response<TypeNumeroDto>> {

	private Response<TypeNumeroDto> response;
	@Autowired
	private TypeNumeroRepository typeNumeroRepository;
	@Autowired
	private ActionUtilisateurRepository actionUtilisateurRepository;
	@Autowired
	private NumeroStoriesRepository numeroStoriesRepository;
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

	public TypeNumeroBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create TypeNumero by using TypeNumeroDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<TypeNumeroDto> create(Request<TypeNumeroDto> request, Locale locale)  throws ParseException {
		log.info("----begin create TypeNumero-----");

		Response<TypeNumeroDto> response = new Response<TypeNumeroDto>();
		List<TypeNumero>        items    = new ArrayList<TypeNumero>();
			
		for (TypeNumeroDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("libelle", dto.getLibelle());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if typeNumero to insert do not exist
			TypeNumero existingEntity = null;
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("typeNumero id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// verif unique libelle in db
			existingEntity = typeNumeroRepository.findByLibelle(dto.getLibelle(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("typeNumero libelle -> " + dto.getLibelle(), locale));
				response.setHasError(true);
				return response;
			}
			// verif unique libelle in items to save
			if (items.stream().anyMatch(a -> a.getLibelle().equalsIgnoreCase(dto.getLibelle()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" libelle ", locale));
				response.setHasError(true);
				return response;
			}

				TypeNumero entityToSave = null;
			entityToSave = TypeNumeroTransformer.INSTANCE.toEntity(dto);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<TypeNumero> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = typeNumeroRepository.saveAll((Iterable<TypeNumero>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("typeNumero", locale));
				response.setHasError(true);
				return response;
			}
			List<TypeNumeroDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? TypeNumeroTransformer.INSTANCE.toLiteDtos(itemsSaved) : TypeNumeroTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create TypeNumero-----");
		return response;
	}

	/**
	 * update TypeNumero by using TypeNumeroDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<TypeNumeroDto> update(Request<TypeNumeroDto> request, Locale locale)  throws ParseException {
		log.info("----begin update TypeNumero-----");

		Response<TypeNumeroDto> response = new Response<TypeNumeroDto>();
		List<TypeNumero>        items    = new ArrayList<TypeNumero>();
			
		for (TypeNumeroDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la typeNumero existe
			TypeNumero entityToSave = null;
			entityToSave = typeNumeroRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("typeNumero id -> " + dto.getId(), locale));
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
			List<TypeNumero> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = typeNumeroRepository.saveAll((Iterable<TypeNumero>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("typeNumero", locale));
				response.setHasError(true);
				return response;
			}
			List<TypeNumeroDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? TypeNumeroTransformer.INSTANCE.toLiteDtos(itemsSaved) : TypeNumeroTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update TypeNumero-----");
		return response;
	}

	/**
	 * delete TypeNumero by using TypeNumeroDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<TypeNumeroDto> delete(Request<TypeNumeroDto> request, Locale locale)  {
		log.info("----begin delete TypeNumero-----");

		Response<TypeNumeroDto> response = new Response<TypeNumeroDto>();
		List<TypeNumero>        items    = new ArrayList<TypeNumero>();
			
		for (TypeNumeroDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la typeNumero existe
			TypeNumero existingEntity = null;
			existingEntity = typeNumeroRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("typeNumero -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------

			// actionUtilisateur
			List<ActionUtilisateur> listOfActionUtilisateur = actionUtilisateurRepository.findByTypeNumeroId(existingEntity.getId(), false);
			if (listOfActionUtilisateur != null && !listOfActionUtilisateur.isEmpty()){
				response.setStatus(functionalError.DATA_NOT_DELETABLE("(" + listOfActionUtilisateur.size() + ")", locale));
				response.setHasError(true);
				return response;
			}
			// numeroStories
			List<NumeroStories> listOfNumeroStories = numeroStoriesRepository.findByIdTypeNumero(existingEntity.getId(), false);
			if (listOfNumeroStories != null && !listOfNumeroStories.isEmpty()){
				response.setStatus(functionalError.DATA_NOT_DELETABLE("(" + listOfNumeroStories.size() + ")", locale));
				response.setHasError(true);
				return response;
			}


			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			typeNumeroRepository.saveAll((Iterable<TypeNumero>) items);

			response.setHasError(false);
		}

		log.info("----end delete TypeNumero-----");
		return response;
	}

	/**
	 * get TypeNumero by using TypeNumeroDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<TypeNumeroDto> getByCriteria(Request<TypeNumeroDto> request, Locale locale)  throws Exception {
		log.info("----begin get TypeNumero-----");

		Response<TypeNumeroDto> response = new Response<TypeNumeroDto>();
		List<TypeNumero> items 			 = typeNumeroRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<TypeNumeroDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? TypeNumeroTransformer.INSTANCE.toLiteDtos(items) : TypeNumeroTransformer.INSTANCE.toDtos(items);

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
			response.setCount(typeNumeroRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("typeNumero", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get TypeNumero-----");
		return response;
	}

	/**
	 * get full TypeNumeroDto by using TypeNumero as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private TypeNumeroDto getFullInfos(TypeNumeroDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	public Response<TypeNumeroDto> custom(Request<TypeNumeroDto> request, Locale locale) {
		log.info("----begin custom TypeNumeroDto-----");
		Response<TypeNumeroDto> response = new Response<TypeNumeroDto>();
		
		response.setHasError(false);
		response.setCount(1L);
		response.setItems(Arrays.asList(new TypeNumeroDto()));
		log.info("----end custom TypeNumeroDto-----");
		return response;
	}
}
