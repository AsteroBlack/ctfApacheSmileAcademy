                                        									
/*
 * Java business for entity table type_demande 
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
import ci.smile.simswaporange.dao.entity.TypeDemande;
import ci.smile.simswaporange.dao.entity.*;
import ci.smile.simswaporange.dao.repository.*;

/**
BUSINESS for table "type_demande"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class TypeDemandeBusiness implements IBasicBusiness<Request<TypeDemandeDto>, Response<TypeDemandeDto>> {

	private Response<TypeDemandeDto> response;
	@Autowired
	private TypeDemandeRepository typeDemandeRepository;
	@Autowired
	private DemandeRepository demandeRepository;
	@Autowired
	private TacheRepository tacheRepository;
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

	public TypeDemandeBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create TypeDemande by using TypeDemandeDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<TypeDemandeDto> create(Request<TypeDemandeDto> request, Locale locale)  throws ParseException {
		log.info("----begin create TypeDemande-----");

		Response<TypeDemandeDto> response = new Response<TypeDemandeDto>();
		List<TypeDemande>        items    = new ArrayList<TypeDemande>();
			
		for (TypeDemandeDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("code", dto.getCode());
			fieldsToVerify.put("libelle", dto.getLibelle());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if typeDemande to insert do not exist
			TypeDemande existingEntity = null;
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("typeDemande id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// verif unique code in db
			existingEntity = typeDemandeRepository.findByCode(dto.getCode(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("typeDemande code -> " + dto.getCode(), locale));
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
			existingEntity = typeDemandeRepository.findByLibelle(dto.getLibelle(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("typeDemande libelle -> " + dto.getLibelle(), locale));
				response.setHasError(true);
				return response;
			}
			// verif unique libelle in items to save
			if (items.stream().anyMatch(a -> a.getLibelle().equalsIgnoreCase(dto.getLibelle()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" libelle ", locale));
				response.setHasError(true);
				return response;
			}

				TypeDemande entityToSave = null;
			entityToSave = TypeDemandeTransformer.INSTANCE.toEntity(dto);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setIsDeleted(false);
			entityToSave.setCreatedBy(request.getUser());
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<TypeDemande> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = typeDemandeRepository.saveAll((Iterable<TypeDemande>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("typeDemande", locale));
				response.setHasError(true);
				return response;
			}
			List<TypeDemandeDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? TypeDemandeTransformer.INSTANCE.toLiteDtos(itemsSaved) : TypeDemandeTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create TypeDemande-----");
		return response;
	}

	/**
	 * update TypeDemande by using TypeDemandeDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<TypeDemandeDto> update(Request<TypeDemandeDto> request, Locale locale)  throws ParseException {
		log.info("----begin update TypeDemande-----");

		Response<TypeDemandeDto> response = new Response<TypeDemandeDto>();
		List<TypeDemande>        items    = new ArrayList<TypeDemande>();
			
		for (TypeDemandeDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la typeDemande existe
			TypeDemande entityToSave = null;
			entityToSave = typeDemandeRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("typeDemande id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			if (Utilities.notBlank(dto.getCode())) {
				entityToSave.setCode(dto.getCode());
			}
			if (Utilities.notBlank(dto.getLibelle())) {
				entityToSave.setLibelle(dto.getLibelle());
			}
			if (dto.getUpdatedBy() != null && dto.getUpdatedBy() > 0) {
				entityToSave.setUpdatedBy(dto.getUpdatedBy());
			}
			if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
				entityToSave.setCreatedBy(dto.getCreatedBy());
			}
			entityToSave.setUpdatedAt(Utilities.getCurrentDate());
			entityToSave.setUpdatedBy(request.getUser());
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<TypeDemande> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = typeDemandeRepository.saveAll((Iterable<TypeDemande>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("typeDemande", locale));
				response.setHasError(true);
				return response;
			}
			List<TypeDemandeDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? TypeDemandeTransformer.INSTANCE.toLiteDtos(itemsSaved) : TypeDemandeTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update TypeDemande-----");
		return response;
	}

	/**
	 * delete TypeDemande by using TypeDemandeDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<TypeDemandeDto> delete(Request<TypeDemandeDto> request, Locale locale)  {
		log.info("----begin delete TypeDemande-----");

		Response<TypeDemandeDto> response = new Response<TypeDemandeDto>();
		List<TypeDemande>        items    = new ArrayList<TypeDemande>();
			
		for (TypeDemandeDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la typeDemande existe
			TypeDemande existingEntity = null;
			existingEntity = typeDemandeRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("typeDemande -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------

			// demande
			List<Demande> listOfDemande = demandeRepository.findByIdTypeDemande(existingEntity.getId(), false);
			if (listOfDemande != null && !listOfDemande.isEmpty()){
				response.setStatus(functionalError.DATA_NOT_DELETABLE("(" + listOfDemande.size() + ")", locale));
				response.setHasError(true);
				return response;
			}
			// tache
			List<Tache> listOfTache = tacheRepository.findByIdTypeDemande(existingEntity.getId(), false);
			if (listOfTache != null && !listOfTache.isEmpty()){
				response.setStatus(functionalError.DATA_NOT_DELETABLE("(" + listOfTache.size() + ")", locale));
				response.setHasError(true);
				return response;
			}


			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			typeDemandeRepository.saveAll((Iterable<TypeDemande>) items);

			response.setHasError(false);
		}

		log.info("----end delete TypeDemande-----");
		return response;
	}

	/**
	 * get TypeDemande by using TypeDemandeDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<TypeDemandeDto> getByCriteria(Request<TypeDemandeDto> request, Locale locale)  throws Exception {
		log.info("----begin get TypeDemande-----");

		Response<TypeDemandeDto> response = new Response<TypeDemandeDto>();
		List<TypeDemande> items 			 = typeDemandeRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<TypeDemandeDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? TypeDemandeTransformer.INSTANCE.toLiteDtos(items) : TypeDemandeTransformer.INSTANCE.toDtos(items);

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
			response.setCount(typeDemandeRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("typeDemande", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get TypeDemande-----");
		return response;
	}

	/**
	 * get full TypeDemandeDto by using TypeDemande as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private TypeDemandeDto getFullInfos(TypeDemandeDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	public Response<TypeDemandeDto> custom(Request<TypeDemandeDto> request, Locale locale) {
		log.info("----begin custom TypeDemandeDto-----");
		Response<TypeDemandeDto> response = new Response<TypeDemandeDto>();
		
		response.setHasError(false);
		response.setCount(1L);
		response.setItems(Arrays.asList(new TypeDemandeDto()));
		log.info("----end custom TypeDemandeDto-----");
		return response;
	}
}
