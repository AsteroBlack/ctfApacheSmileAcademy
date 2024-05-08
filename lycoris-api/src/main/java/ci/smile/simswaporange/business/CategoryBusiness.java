                                        									
/*
 * Java business for entity table category 
 * Created on 2022-10-04 ( Time 11:23:37 )
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
import ci.smile.simswaporange.dao.entity.Category;
import ci.smile.simswaporange.dao.entity.*;
import ci.smile.simswaporange.dao.repository.*;

/**
BUSINESS for table "category"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class CategoryBusiness implements IBasicBusiness<Request<CategoryDto>, Response<CategoryDto>> {
	@PersistenceContext
	private EntityManager em;
	private SimpleDateFormat dateFormat;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TechnicalError technicalError;
	@Autowired
	private ExceptionUtils exceptionUtils;
	private Response<CategoryDto> response;
	private SimpleDateFormat dateTimeFormat;
	@Autowired
	private FunctionalError functionalError;
	@Autowired
	private CategoryRepository categoryRepository;

	public CategoryBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create Category by using CategoryDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<CategoryDto> create(Request<CategoryDto> request, Locale locale)  throws ParseException {
		log.info("----begin create Category-----");

		Response<CategoryDto> response = new Response<CategoryDto>();
		List<Category>        items    = new ArrayList<Category>();
			
		for (CategoryDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("code", dto.getCode());
			fieldsToVerify.put("libelle", dto.getLibelle());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if category to insert do not exist
			Category existingEntity = null;
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("category id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// verif unique code in db
			existingEntity = categoryRepository.findByCode(dto.getCode(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("category code -> " + dto.getCode(), locale));
				response.setHasError(true);
				return response;
			}
			// verif unique code in items to save
			if (items.stream().anyMatch(a -> a.getCode().equalsIgnoreCase(dto.getCode()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" code ", locale));
				response.setHasError(true);
				return response;
			}

			// verif unique libelle in db
			existingEntity = categoryRepository.findByLibelle(dto.getLibelle(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("category libelle -> " + dto.getLibelle(), locale));
				response.setHasError(true);
				return response;
			}
			// verif unique libelle in items to save
			if (items.stream().anyMatch(a -> a.getLibelle().equalsIgnoreCase(dto.getLibelle()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" libelle ", locale));
				response.setHasError(true);
				return response;
			}

				Category entityToSave = null;
			entityToSave = CategoryTransformer.INSTANCE.toEntity(dto);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setIsDeleted(false);
			entityToSave.setCreatedBy(request.getUser());
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<Category> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = categoryRepository.saveAll((Iterable<Category>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("category", locale));
				response.setHasError(true);
				return response;
			}
			List<CategoryDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? CategoryTransformer.INSTANCE.toLiteDtos(itemsSaved) : CategoryTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create Category-----");
		response.setActionEffectue("Creer categorie");
		return response;
	}

	/**
	 * update Category by using CategoryDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<CategoryDto> update(Request<CategoryDto> request, Locale locale)  throws ParseException {
		log.info("----begin update Category-----");

		Response<CategoryDto> response = new Response<CategoryDto>();
		List<Category>        items    = new ArrayList<Category>();
			
		for (CategoryDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la category existe
			Category entityToSave = null;
			entityToSave = categoryRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("category id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			if (Utilities.notBlank(dto.getCode())) {
				entityToSave.setCode(dto.getCode());
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
			List<Category> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = categoryRepository.saveAll((Iterable<Category>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("category", locale));
				response.setHasError(true);
				return response;
			}
			List<CategoryDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? CategoryTransformer.INSTANCE.toLiteDtos(itemsSaved) : CategoryTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update Category-----");
		return response;
	}

	/**
	 * delete Category by using CategoryDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<CategoryDto> delete(Request<CategoryDto> request, Locale locale)  {
		log.info("----begin delete Category-----");

		Response<CategoryDto> response = new Response<CategoryDto>();
		List<Category>        items    = new ArrayList<Category>();
			
		for (CategoryDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la category existe
			Category existingEntity = null;
			existingEntity = categoryRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("category -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------

			// user
			List<User> listOfUser = userRepository.findByIdCategory(existingEntity.getId(), false);
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
			categoryRepository.saveAll((Iterable<Category>) items);

			response.setHasError(false);
		}

		log.info("----end delete Category-----");
		return response;
	}

	/**
	 * get Category by using CategoryDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<CategoryDto> getByCriteria(Request<CategoryDto> request, Locale locale)  throws Exception {
		log.info("----begin get Category-----");

		Response<CategoryDto> response = new Response<CategoryDto>();
		List<Category> items 			 = categoryRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<CategoryDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? CategoryTransformer.INSTANCE.toLiteDtos(items) : CategoryTransformer.INSTANCE.toDtos(items);

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
			response.setCount(categoryRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("category", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get Category-----");
		response.setActionEffectue("Vue Categorie");
		return response;
	}

	/**
	 * get full CategoryDto by using Category as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private CategoryDto getFullInfos(CategoryDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	public Response<CategoryDto> custom(Request<CategoryDto> request, Locale locale) {
		log.info("----begin custom CategoryDto-----");
		Response<CategoryDto> response = new Response<CategoryDto>();
		
		response.setHasError(false);
		response.setCount(1L);
		response.setItems(Arrays.asList(new CategoryDto()));
		log.info("----end custom CategoryDto-----");
		return response;
	}
}
