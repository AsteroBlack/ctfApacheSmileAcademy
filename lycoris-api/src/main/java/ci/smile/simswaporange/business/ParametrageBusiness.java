
/*
 * Java business for entity table parametrage 
 * Created on 2023-06-28 ( Time 22:33:01 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.business;

import lombok.val;
import lombok.extern.java.Log;
import scala.annotation.elidable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import ci.smile.simswaporange.utils.*;
import ci.smile.simswaporange.utils.dto.*;
import ci.smile.simswaporange.utils.dto.customize.Data;
import ci.smile.simswaporange.utils.dto.customize.Jour;
import ci.smile.simswaporange.utils.dto.customize.PlageHoraire;
import ci.smile.simswaporange.utils.enums.*;
import ci.smile.simswaporange.utils.contract.*;
import ci.smile.simswaporange.utils.contract.IBasicBusiness;
import ci.smile.simswaporange.utils.contract.Request;
import ci.smile.simswaporange.utils.contract.Response;
import ci.smile.simswaporange.utils.dto.transformer.*;
import ci.smile.simswaporange.dao.entity.Parametrage;
import ci.smile.simswaporange.dao.entity.TypeParametrage;
import ci.smile.simswaporange.dao.entity.*;
import ci.smile.simswaporange.dao.repository.*;

/**
 * BUSINESS for table "parametrage"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class ParametrageBusiness implements IBasicBusiness<Request<ParametrageDto>, Response<ParametrageDto>> {

	private Response<ParametrageDto> response;
	@Autowired
	private ParametrageRepository parametrageRepository;
	@Autowired
	private ParametrageProfilRepository parametrageProfilRepository;
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

	public ParametrageBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}

	/**
	 * create Parametrage by using ParametrageDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ParametrageDto> create(Request<ParametrageDto> request, Locale locale) throws ParseException {
		log.info("----begin create Parametrage-----");

		Response<ParametrageDto> response = new Response<ParametrageDto>();
		List<Parametrage> items = new ArrayList<Parametrage>();

		for (ParametrageDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
//			fieldsToVerify.put("delaiAttente", dto.getDelaiAttente());
			fieldsToVerify.put("idTypeParametrage", dto.getIdTypeParametrage());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}
			// Verify if parametrage to insert do not exist
			Parametrage existingEntity = null;
			if (Utilities.isNotEmpty(dto.getHours())) {
				for (HoursDto hours : dto.getHours()) {
					if (hours.getKey() != null) {
						List<plagesDto> plagesDtos = hours.getPlages();
						if (Utilities.isNotEmpty(plagesDtos)) {
							for (plagesDto valuesDtos : hours.getPlages()) {
								List<Integer> hoursDto = valuesDtos.getValues();
								if (Utilities.isNotEmpty(hoursDto)) {
									if (hoursDto.size() == 2) {
										Integer firstElement = hoursDto.get(0);
										Integer secondElkement = hoursDto.get(1);
										if (firstElement > secondElkement) {
											response.setStatus(functionalError.DISALLOWED_OPERATION(
													"Heure de début doit etre inferieur a l'heure de fin", locale));
											response.setHasError(Boolean.TRUE);
											return response;
										}
									}
								}
							}
						}

					} else {
						response.setStatus(functionalError.DISALLOWED_OPERATION("le jour doit être renseigné", locale));
						response.setHasError(Boolean.TRUE);
						return response;
					}
				}
			}
			// Verify if typeParametrage exist
			TypeParametrage existingTypeParametrage = null;
			if (dto.getIdTypeParametrage() != null && dto.getIdTypeParametrage() > 0) {
				existingTypeParametrage = typeParametrageRepository.findOne(dto.getIdTypeParametrage(), false);
				if (existingTypeParametrage == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST(
							"typeParametrage idTypeParametrage -> " + dto.getIdTypeParametrage(), locale));
					response.setHasError(true);
					return response;
				}
			}
			Parametrage entityToSave = null;
			entityToSave = ParametrageTransformer.INSTANCE.toEntity(dto, existingTypeParametrage);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<Parametrage> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = parametrageRepository.saveAll((Iterable<Parametrage>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("parametrage", locale));
				response.setHasError(true);
				return response;
			}
			List<ParametrageDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
					? ParametrageTransformer.INSTANCE.toLiteDtos(itemsSaved)
					: ParametrageTransformer.INSTANCE.toDtos(itemsSaved);

			final int size = itemsSaved.size();
			List<String> listOfError = Collections.synchronizedList(new ArrayList<String>());
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

		log.info("----end create Parametrage-----");
		return response;
	}

	/**
	 * update Parametrage by using ParametrageDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ParametrageDto> update(Request<ParametrageDto> request, Locale locale) throws ParseException {
		log.info("----begin update Parametrage-----");

		Response<ParametrageDto> response = new Response<ParametrageDto>();
		List<Parametrage> items = new ArrayList<Parametrage>();

		for (ParametrageDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}
			

			// Verifier si la parametrage existe
			Parametrage entityToSave = null;
			entityToSave = parametrageRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("parametrage id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}
			String key = null;
			if (Utilities.isNotEmpty(dto.getHours())) {
				for (HoursDto parametrage : dto.getHours()) {
					List<Object> listMapsHours = new ArrayList<>();
					key = parametrage.getKey();
					List<plagesDto> values = parametrage.getPlages();
					for (plagesDto hours : values) {
						Map<String, Object> hoursMap = new HashMap<>();
						hoursMap.put("values", hours.getValues());
						listMapsHours.add(hoursMap);
					}
					if (!verifierPlages(listMapsHours, key)) {
						response.setStatus(functionalError.DISALLOWED_OPERATION("Les plages horaires du " + key + " se chevauchent", locale));
						response.setHasError(true);
						return response;
					}
				}
			}
			
		

			// Verify if typeParametrage exist
			if (dto.getIdTypeParametrage() != null && dto.getIdTypeParametrage() > 0) {
				TypeParametrage existingTypeParametrage = typeParametrageRepository.findOne(dto.getIdTypeParametrage(),
						false);
				if (existingTypeParametrage == null) {
					response.setStatus(functionalError.DATA_NOT_EXIST(
							"typeParametrage idTypeParametrage -> " + dto.getIdTypeParametrage(), locale));
					response.setHasError(true);
					return response;
				}
				entityToSave.setTypeParametrage(existingTypeParametrage);
			}
			if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
				entityToSave.setCreatedBy(dto.getCreatedBy());
			}
			if (Utilities.notBlank(dto.getDelaiAttente())) {
				entityToSave.setDelaiAttente(dto.getDelaiAttente());
			}
			if (dto.getUpdatedBy() != null && dto.getUpdatedBy() > 0) {
				entityToSave.setUpdatedBy(dto.getUpdatedBy());
			}
			entityToSave.setUpdatedAt(Utilities.getCurrentDate());
			entityToSave.setUpdatedBy(request.getUser());
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<Parametrage> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = parametrageRepository.saveAll((Iterable<Parametrage>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("parametrage", locale));
				response.setHasError(true);
				return response;
			}
			List<ParametrageDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
					? ParametrageTransformer.INSTANCE.toLiteDtos(itemsSaved)
					: ParametrageTransformer.INSTANCE.toDtos(itemsSaved);

			final int size = itemsSaved.size();
			List<String> listOfError = Collections.synchronizedList(new ArrayList<String>());
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

		log.info("----end update Parametrage-----");
		return response;
	}

	/**
	 * delete Parametrage by using ParametrageDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ParametrageDto> delete(Request<ParametrageDto> request, Locale locale) {
		log.info("----begin delete Parametrage-----");

		Response<ParametrageDto> response = new Response<ParametrageDto>();
		List<Parametrage> items = new ArrayList<Parametrage>();

		for (ParametrageDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la parametrage existe
			Parametrage existingEntity = null;
			existingEntity = parametrageRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("parametrage -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// -----------------------------------------------------------------------
			// ----------- CHECK IF DATA IS USED
			// -----------------------------------------------------------------------

			// parametrageProfil
			List<ParametrageProfil> listOfParametrageProfil = parametrageProfilRepository
					.findByIdParametrage(existingEntity.getId(), false);
			if (listOfParametrageProfil != null && !listOfParametrageProfil.isEmpty()) {
				response.setStatus(
						functionalError.DATA_NOT_DELETABLE("(" + listOfParametrageProfil.size() + ")", locale));
				response.setHasError(true);
				return response;
			}

			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			parametrageRepository.saveAll((Iterable<Parametrage>) items);
			response.setStatus(functionalError.SUCCESS("", locale));
			response.setHasError(false);
		}

		log.info("----end delete Parametrage-----");
		return response;
	}

	/**
	 * get Parametrage by using ParametrageDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ParametrageDto> getByCriteria(Request<ParametrageDto> request, Locale locale) throws Exception {
		log.info("----begin get Parametrage-----");

		Response<ParametrageDto> response = new Response<ParametrageDto>();
		List<Parametrage> items = parametrageRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<ParametrageDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
					? ParametrageTransformer.INSTANCE.toLiteDtos(items)
					: ParametrageTransformer.INSTANCE.toDtos(items);

			final int size = items.size();
			List<String> listOfError = Collections.synchronizedList(new ArrayList<String>());
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
			response.setCount(parametrageRepository.count(request, em, locale));
			response.setStatus(functionalError.SUCCESS("", locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("parametrage", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get Parametrage-----");
		return response;
	}

	/**
	 * get full ParametrageDto by using Parametrage as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private ParametrageDto getFullInfos(ParametrageDto dto, Integer size, Boolean isSimpleLoading, Locale locale)
			throws Exception {
		// put code here
		if (dto != null) {
			if (Utilities.notBlank(dto.getDelaiAttente())) {
//				dto.setRetry(dto.getDelaiAttente().replaceAll("\\n", ""));
//				System.out.println("ici ------------------------------------> "+dto.getRetry());
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					JsonNode jsonNode = objectMapper.readTree(dto.getDelaiAttente());
					Map<String, Object> resultMap = objectMapper.convertValue(jsonNode, HashMap.class);
//					verifierPlagesHoraires(resultMap);
					dto.setData(resultMap);
					dto.setDelaiAttente(null);
					System.out.println("ici ------------------------------------> " + resultMap);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
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

	public static boolean verifierPlagesHoraires(Map<String, Object> map) {
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> datas = (List<Map<String, Object>>) map.get("datas");
		for (Map<String, Object> data : datas) {
			@SuppressWarnings("unchecked")
			List<Map<String, List<Object>>> hours = (List<Map<String, List<Object>>>) data.get("hours");

			for (Map<String, List<Object>> day : hours) {
				List<Object> plages = day.get("plages");
				if (!verifierPlages(plages, "")) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean verifierPlages(List<Object> plages, String key) {
		List<Map<String, List<Integer>>> listeDePlages = new ArrayList<>();
		for (int i = 0; i < plages.size(); i++) {
			Map<String, List<Integer>> plageMapI = null;
			Object plageObjI = plages.get(i);
			if (plageObjI instanceof Map) {
				plageMapI = (Map<String, List<Integer>>) plageObjI;
			} else {
				System.out.println("Erreur : L'élément extrait de plages n'est pas une Map.");
				return false;
			}
			listeDePlages.add(plageMapI);
		}

		if (plagesSeChevauchent(listeDePlages)) {
			System.out.println("Erreur : Les plages horaires se chevauchent pour la clé " + key);
			return false;
		}
		return true;
	}

	private static boolean plagesSeChevauchent(List<Map<String, List<Integer>>> listeDePlages) {
		if (listeDePlages == null || listeDePlages.size() < 2) {
			System.out.println("Erreur : La liste doit contenir au moins deux maps.");
			return false;
		}
		try {
			for (int i = 0; i < listeDePlages.size(); i++) {
				int debutI = listeDePlages.get(i).get("values").get(0);
				int finI = listeDePlages.get(i).get("values").get(1);

				for (int j = i + 1; j < listeDePlages.size(); j++) {
					int debutJ = listeDePlages.get(j).get("values").get(0);
					int finJ = listeDePlages.get(j).get("values").get(1);

					if (finI >= debutJ && debutI <= finJ) {
						return true; // Il y a chevauchement
					}
				}
			}
			return false; // Aucun chevauchement trouvé
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Erreur : La liste ne contient pas assez d'éléments.");
			return false;
		}
	}




	public Response<ParametrageDto> custom(Request<ParametrageDto> request, Locale locale) {
		log.info("----begin custom ParametrageDto-----");
		Response<ParametrageDto> response = new Response<ParametrageDto>();

		response.setHasError(false);
		response.setCount(1L);
		response.setItems(Arrays.asList(new ParametrageDto()));
		log.info("----end custom ParametrageDto-----");
		return response;
	}
}
