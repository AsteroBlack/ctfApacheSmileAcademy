                                                            														
/*
 * Java business for entity table tache 
 * Created on 2023-07-04 ( Time 13:18:47 )
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
import ci.smile.simswaporange.dao.entity.Tache;
import ci.smile.simswaporange.dao.entity.ActionEnMasse;
import ci.smile.simswaporange.dao.entity.ActionSimswap;
import ci.smile.simswaporange.dao.entity.Demande;
import ci.smile.simswaporange.dao.entity.Status;
import ci.smile.simswaporange.dao.entity.TypeDemande;
import ci.smile.simswaporange.dao.entity.User;
import ci.smile.simswaporange.dao.entity.*;
import ci.smile.simswaporange.dao.repository.*;

/**
BUSINESS for table "tache"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class TacheBusiness implements IBasicBusiness<Request<TacheDto>, Response<TacheDto>> {

	private Response<TacheDto> response;
	@Autowired
	private TacheRepository tacheRepository;
	@Autowired
	private ActionEnMasseRepository actionEnMasseRepository;
	@Autowired
	private ActionSimswapRepository actionSimswapRepository;
	@Autowired
	private DemandeRepository demandeRepository;
	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private TypeDemandeRepository typeDemandeRepository;
	@Autowired
	private UserRepository userRepository;
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

	public TacheBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create Tache by using TacheDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<TacheDto> create(Request<TacheDto> request, Locale locale)  throws ParseException {
		log.info("----begin create Tache-----");

		Response<TacheDto> response = new Response<TacheDto>();
		List<Tache>        items    = new ArrayList<Tache>();
			
		for (TacheDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("idUser", dto.getIdUser());
			fieldsToVerify.put("idTypeDemande", dto.getIdTypeDemande());
			fieldsToVerify.put("idDemande", dto.getIdDemande());
			fieldsToVerify.put("dateDemande", dto.getDateDemande());
			fieldsToVerify.put("idActionEnMasse", dto.getIdActionEnMasse());
			fieldsToVerify.put("idStatus", dto.getIdStatus());
			fieldsToVerify.put("idActionSimswap", dto.getIdActionSimswap());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if tache to insert do not exist
			Tache existingEntity = null;
//			if (existingEntity != null) {
//				response.setStatus(functionalError.DATA_EXIST("tache id -> " + dto.getId(), locale));
//				response.setHasError(true);
//				return response;
//			}

			// Verify if actionEnMasse exist
			ActionEnMasse existingActionEnMasse = null;
			if (dto.getIdActionEnMasse() != null && dto.getIdActionEnMasse() > 0){
				existingActionEnMasse = actionEnMasseRepository.findOne(dto.getIdActionEnMasse(), false);
				if (existingActionEnMasse == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("actionEnMasse idActionEnMasse -> " + dto.getIdActionEnMasse(), locale));
					response.setHasError(true);
					return response;
				}
			}
			// Verify if actionSimswap exist
			ActionSimswap existingActionSimswap = null;
			if (dto.getIdActionSimswap() != null && dto.getIdActionSimswap() > 0){
				existingActionSimswap = actionSimswapRepository.findOne(dto.getIdActionSimswap(), false);
				if (existingActionSimswap == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("actionSimswap idActionSimswap -> " + dto.getIdActionSimswap(), locale));
					response.setHasError(true);
					return response;
				}
			}
			// Verify if demande exist
			Demande existingDemande = null;
			if (dto.getIdDemande() != null && dto.getIdDemande() > 0){
				existingDemande = demandeRepository.findOne(dto.getIdDemande(), false);
				if (existingDemande == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("demande idDemande -> " + dto.getIdDemande(), locale));
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
			// Verify if typeDemande exist
			TypeDemande existingTypeDemande = null;
			if (dto.getIdTypeDemande() != null && dto.getIdTypeDemande() > 0){
				existingTypeDemande = typeDemandeRepository.findOne(dto.getIdTypeDemande(), false);
				if (existingTypeDemande == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("typeDemande idTypeDemande -> " + dto.getIdTypeDemande(), locale));
					response.setHasError(true);
					return response;
				}
			}
			// Verify if user exist
			User existingUser = null;
			if (dto.getIdUser() != null && dto.getIdUser() > 0){
				existingUser = userRepository.findOne(dto.getIdUser(), false);
				if (existingUser == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("user idUser -> " + dto.getIdUser(), locale));
					response.setHasError(true);
					return response;
				}
			}
				Tache entityToSave = null;
			entityToSave = TacheTransformer.INSTANCE.toEntity(dto, existingActionEnMasse, existingActionSimswap, existingDemande, existingStatus, existingTypeDemande, existingUser);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setIsDeleted(false);
			entityToSave.setCreatedBy(request.getUser());
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<Tache> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = tacheRepository.saveAll((Iterable<Tache>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("tache", locale));
				response.setHasError(true);
				return response;
			}
			List<TacheDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? TacheTransformer.INSTANCE.toLiteDtos(itemsSaved) : TacheTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create Tache-----");
		return response;
	}

	/**
	 * update Tache by using TacheDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<TacheDto> update(Request<TacheDto> request, Locale locale)  throws ParseException {
		log.info("----begin update Tache-----");

		Response<TacheDto> response = new Response<TacheDto>();
		List<Tache>        items    = new ArrayList<Tache>();
			
		for (TacheDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la tache existe
			Tache entityToSave = null;
			entityToSave = tacheRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("tache id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if actionEnMasse exist
			if (dto.getIdActionEnMasse() != null && dto.getIdActionEnMasse() > 0){
				ActionEnMasse existingActionEnMasse = actionEnMasseRepository.findOne(dto.getIdActionEnMasse(), false);
				if (existingActionEnMasse == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("actionEnMasse idActionEnMasse -> " + dto.getIdActionEnMasse(), locale));
					response.setHasError(true);
					return response;
				}
				entityToSave.setActionEnMasse(existingActionEnMasse);
			}
			// Verify if actionSimswap exist
			if (dto.getIdActionSimswap() != null && dto.getIdActionSimswap() > 0){
				ActionSimswap existingActionSimswap = actionSimswapRepository.findOne(dto.getIdActionSimswap(), false);
				if (existingActionSimswap == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("actionSimswap idActionSimswap -> " + dto.getIdActionSimswap(), locale));
					response.setHasError(true);
					return response;
				}
				entityToSave.setActionSimswap(existingActionSimswap);
			}
			// Verify if demande exist
			if (dto.getIdDemande() != null && dto.getIdDemande() > 0){
				Demande existingDemande = demandeRepository.findOne(dto.getIdDemande(), false);
				if (existingDemande == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("demande idDemande -> " + dto.getIdDemande(), locale));
					response.setHasError(true);
					return response;
				}
				entityToSave.setDemande(existingDemande);
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
			// Verify if typeDemande exist
			if (dto.getIdTypeDemande() != null && dto.getIdTypeDemande() > 0){
				TypeDemande existingTypeDemande = typeDemandeRepository.findOne(dto.getIdTypeDemande(), false);
				if (existingTypeDemande == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("typeDemande idTypeDemande -> " + dto.getIdTypeDemande(), locale));
					response.setHasError(true);
					return response;
				}
				entityToSave.setTypeDemande(existingTypeDemande);
			}
			// Verify if user exist
			if (dto.getIdUser() != null && dto.getIdUser() > 0){
				User existingUser = userRepository.findOne(dto.getIdUser(), false);
				if (existingUser == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST("user idUser -> " + dto.getIdUser(), locale));
					response.setHasError(true);
					return response;
				}
				entityToSave.setUser(existingUser);
			}
			if (dto.getUpdatedBy() != null && dto.getUpdatedBy() > 0) {
				entityToSave.setUpdatedBy(dto.getUpdatedBy());
			}
			if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
				entityToSave.setCreatedBy(dto.getCreatedBy());
			}
			if (Utilities.notBlank(dto.getDateDemande())) {
				entityToSave.setDateDemande(dateFormat.parse(dto.getDateDemande()));
			}
			entityToSave.setUpdatedAt(Utilities.getCurrentDate());
			entityToSave.setUpdatedBy(request.getUser());
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<Tache> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = tacheRepository.saveAll((Iterable<Tache>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("tache", locale));
				response.setHasError(true);
				return response;
			}
			List<TacheDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? TacheTransformer.INSTANCE.toLiteDtos(itemsSaved) : TacheTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update Tache-----");
		return response;
	}

	/**
	 * delete Tache by using TacheDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<TacheDto> delete(Request<TacheDto> request, Locale locale)  {
		log.info("----begin delete Tache-----");

		Response<TacheDto> response = new Response<TacheDto>();
		List<Tache>        items    = new ArrayList<Tache>();
			
		for (TacheDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}
			// Verifier si la tache existe
			Tache existingEntity = null;
			existingEntity = tacheRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("tache -> " + dto.getId(), locale));
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
			tacheRepository.saveAll((Iterable<Tache>) items);

			response.setHasError(false);
		}

		log.info("----end delete Tache-----");
		return response;
	}

	/**
	 * get Tache by using TacheDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<TacheDto> getByCriteria(Request<TacheDto> request, Locale locale)  throws Exception {
		log.info("----begin get Tache-----");

		Response<TacheDto> response = new Response<TacheDto>();
		List<Tache> items 			 = tacheRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<TacheDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? TacheTransformer.INSTANCE.toLiteDtos(items) : TacheTransformer.INSTANCE.toDtos(items);

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
			response.setCount(tacheRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("tache", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get Tache-----");
		return response;
	}

	/**
	 * get full TacheDto by using Tache as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private TacheDto getFullInfos(TacheDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here
		Integer demandeId = dto.getIdDemande();
		Integer idActionMasse = dto.getIdActionEnMasse();
		if (demandeId != null) {
			Demande demande = demandeRepository.findOne(demandeId, Boolean.FALSE);
			if (demande != null) {
				dto.setDemandeDto(DemandeTransformer.INSTANCE.toDto(demande));
				if (demande.getUpdatedByOmci() != null) {
					User userOne = userRepository.findOne(demande.getUpdatedByOmci(), Boolean.FALSE);
					dto.setUserOmciDto(UserTransformer.INSTANCE.toDto(userOne));
				}
				if (demande.getUpdatedByTelco() != null) {
					User useTxo = userRepository.findOne(demande.getUpdatedByTelco(), Boolean.FALSE);
					dto.setUserOmciDto(UserTransformer.INSTANCE.toDto(useTxo));
				}
			}
		}
		
		if (idActionMasse != null) {
			ActionEnMasse actionEnMasse = actionEnMasseRepository.findOne(idActionMasse, Boolean.FALSE);
			if (actionEnMasse != null) {
				dto.setActionEnMasseDto(ActionEnMasseTransformer.INSTANCE.toDto(actionEnMasse));
			}
		}
		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	public Response<TacheDto> custom(Request<TacheDto> request, Locale locale) {
		log.info("----begin custom TacheDto-----");
		Response<TacheDto> response = new Response<TacheDto>();
		
		response.setHasError(false);
		response.setCount(1L);
		response.setItems(Arrays.asList(new TacheDto()));
		log.info("----end custom TacheDto-----");
		return response;
	}
}
