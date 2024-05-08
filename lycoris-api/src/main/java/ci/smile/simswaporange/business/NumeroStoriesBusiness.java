
/*
 * Java business for entity table numero_stories 
 * Created on 2022-07-26 ( Time 15:18:21 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.business;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import ci.smile.simswaporange.utils.*;
import ci.smile.simswaporange.utils.dto.*;
import ci.smile.simswaporange.utils.dto.customize._AppRequestDto;
import ci.smile.simswaporange.utils.enums.*;
import javassist.tools.web.BadHttpRequest;
import ci.smile.simswaporange.utils.contract.*;
import ci.smile.simswaporange.utils.contract.IBasicBusiness;
import ci.smile.simswaporange.utils.contract.Request;
import ci.smile.simswaporange.utils.contract.Response;
import ci.smile.simswaporange.utils.dto.transformer.*;
import ci.smile.simswaporange.dao.entity.NumeroStories;
import ci.smile.simswaporange.dao.entity.Status;
import ci.smile.simswaporange.dao.entity.Profil;
import ci.smile.simswaporange.dao.entity.User;
import ci.smile.simswaporange.dao.entity.*;
import ci.smile.simswaporange.dao.repository.*;
import ci.smile.simswaporange.proxy.response.ApiResponse;
import ci.smile.simswaporange.proxy.response.LockUnLockFreezeDto;
import ci.smile.simswaporange.proxy.service.SimSwapServiceProxyService;

//import static ci.smile.simswaporange.business.ActionUtilisateurBusiness.getCell;

/**
 * BUSINESS for table "numero_stories"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class NumeroStoriesBusiness implements IBasicBusiness<Request<NumeroStoriesDto>, Response<NumeroStoriesDto>> {

	private Response<NumeroStoriesDto> response;
	@Autowired
	private NumeroStoriesRepository numeroStoriesRepository;
	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProfilRepository profilRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ParamsUtils paramsUtils;
	@Autowired
	private SimSwapServiceProxyService simSwapServiceProxyService;
	@Autowired
	private FunctionalError functionalError;
	@Autowired
	private TechnicalError technicalError;
	@Autowired
	private TypeNumeroRepository typeNumeroRepository;
	@Autowired
	private ExceptionUtils exceptionUtils;
	@PersistenceContext
	private EntityManager em;

	private SimpleDateFormat dateFormat;
	private SimpleDateFormat dateTimeFormat;

	public NumeroStoriesBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}

	/**
	 * create NumeroStories by using NumeroStoriesDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<NumeroStoriesDto> create(Request<NumeroStoriesDto> request, Locale locale) throws ParseException {
		log.info("----begin create NumeroStories-----");
		Response resApi = null;
		Response<NumeroStoriesDto> response = new Response<NumeroStoriesDto>();
		List<NumeroStories> items = new ArrayList<NumeroStories>();

		for (NumeroStoriesDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("numero", dto.getNumero());
//			fieldsToVerify.put("idProfil", dto.getIdProfil());
//			fieldsToVerify.put("idStatut", dto.getIdStatut());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}
			// verif unique numero in items to save
			if (items.stream().anyMatch(a -> a.getNumero().equalsIgnoreCase(dto.getNumero()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" numero ", locale));
				response.setHasError(true);
				return response;
			}

			// Verify if status exist
			Status existingStatus = null;
			if (dto.getIdStatut() != null && dto.getIdStatut() > 0) {
				existingStatus = statusRepository.findOne(dto.getIdStatut(), false);
				if (existingStatus == null) {
					response.setStatus(
							functionalError.DATA_NOT_EXIST("status idStatut -> " + dto.getIdStatut(), locale));
					response.setHasError(true);
					return response;
				}
			}
			// Verify if profil exist
			Profil existingProfil = null;
			if (dto.getIdProfil() != null && dto.getIdProfil() > 0) {
				existingProfil = profilRepository.findOne(dto.getIdProfil(), false);
				if (existingProfil == null) {
					response.setStatus(
							functionalError.DATA_NOT_EXIST("profil idProfil -> " + dto.getIdProfil(), locale));
					response.setHasError(true);
					return response;
				}
			}
			// Verify if user exist
			User existingUser = null;
			if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
				existingUser = userRepository.findOne(dto.getCreatedBy(), false);
				if (existingUser == null) {
					response.setStatus(
							functionalError.DATA_NOT_EXIST("user createdBy -> " + dto.getCreatedBy(), locale));
					response.setHasError(true);
					return response;
				}
			}

			TypeNumero typeNumero = null;
			if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
				typeNumero = typeNumeroRepository.findOne(dto.getIdTypeNumero(), false);
				if (typeNumero == null) {
					response.setStatus(functionalError
							.DATA_NOT_EXIST("typeNumero typeNumeroId -> " + dto.getIdTypeNumero(), locale));
					response.setHasError(true);
					return response;
				}
			}
			dto.setIsMachine(dto.getIsMachine());
			dto.setNumero(dto.getNumero());
			dto.setCreatedBy(dto.getCreatedBy());
			Category categories = categoryRepository.findOne(dto.getIdCategory() != null ? dto.getIdCategory() : null,
					Boolean.FALSE);
			NumeroStories entityToSave = null;
			entityToSave = NumeroStoriesTransformer.INSTANCE.toEntity(dto, typeNumero, existingProfil, categories,
					existingUser, existingStatus);
			entityToSave.setCategory(categories);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);

//			for(NumeroStories calApi : items){
//				String url = "/" + calApi.getNumero() + "/lock?appId=" + ParamsUtils.simSwapAppId + "&requestId=" + request.getIdentifiantRequest();
//				try {
//					resApi = callApi.ApiBlockNumber(url, request.getUser());
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
		}

		if (!items.isEmpty()) {
			List<NumeroStories> itemsSaved = null;

			// inserer les donnees en base de donnees
			itemsSaved = numeroStoriesRepository.saveAll((Iterable<NumeroStories>) items);

			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("numeroStories", locale));
				response.setHasError(true);
				return response;
			}
			List<NumeroStoriesDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
					? NumeroStoriesTransformer.INSTANCE.toLiteDtos(itemsSaved)
					: NumeroStoriesTransformer.INSTANCE.toDtos(itemsSaved);

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
			response.setItems(itemsDto);
			response.setHasError(false);
		}

		log.info("----end create NumeroStories-----");
		return response;
	}

	/**
	 * update NumeroStories by using NumeroStoriesDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<NumeroStoriesDto> update(Request<NumeroStoriesDto> request, Locale locale) throws ParseException {
		log.info("----begin update NumeroStories-----");

		Response<NumeroStoriesDto> response = new Response<NumeroStoriesDto>();
		List<NumeroStories> items = new ArrayList<NumeroStories>();
		Response resApi = null;
		for (NumeroStoriesDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la numeroStories existe
			NumeroStories entityToSave = null;
			entityToSave = numeroStoriesRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("numeroStories id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if status exist
			if (dto.getIdStatut() != null && dto.getIdStatut() > 0) {
				Status existingStatus = statusRepository.findOne(dto.getIdStatut(), false);
				if (existingStatus == null) {
					response.setStatus(
							functionalError.DATA_NOT_EXIST("status idStatut -> " + dto.getIdStatut(), locale));
					response.setHasError(true);
					return response;
				}
				entityToSave.setStatus(existingStatus);
			}
			// Verify if profil exist
			if (dto.getIdProfil() != null && dto.getIdProfil() > 0) {
				Profil existingProfil = profilRepository.findOne(dto.getIdProfil(), false);
				if (existingProfil == null) {
					response.setStatus(
							functionalError.DATA_NOT_EXIST("profil idProfil -> " + dto.getIdProfil(), locale));
					response.setHasError(true);
					return response;
				}
				entityToSave.setProfil(existingProfil);
			}
			// Verify if user exist
			if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
				User existingUser = userRepository.findOne(dto.getCreatedBy(), false);
				if (existingUser == null) {
					response.setStatus(
							functionalError.DATA_NOT_EXIST("user createdBy -> " + dto.getCreatedBy(), locale));
					response.setHasError(true);
					return response;
				}
				entityToSave.setUser(existingUser);
			}
			if (Utilities.notBlank(dto.getNumero())) {
				entityToSave.setNumero(dto.getNumero());
			}
			if (dto.getUpdatedBy() != null && dto.getUpdatedBy() > 0) {
				entityToSave.setUpdatedBy(dto.getUpdatedBy());
			}
			entityToSave.setUpdatedAt(Utilities.getCurrentDate());
			entityToSave.setUpdatedBy(request.getUser());
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<NumeroStories> itemsSaved = null;
			// maj les donnees en base

			for (NumeroStories item : items) {
				_AppRequestDto _AppRequest = _AppRequestDto.builder().phoneNumber(item.getNumero()).build();

				ApiResponse<LockUnLockFreezeDto> apiResponse = simSwapServiceProxyService.lockPhoneNumber(_AppRequest);

//				if(!resApi.isHasError()){
//					entityToSave.setStatut(StatusApiEnum.DEBLOCK);
//				}
			}

			itemsSaved = numeroStoriesRepository.saveAll((Iterable<NumeroStories>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("numeroStories", locale));
				response.setHasError(true);
				return response;
			}
			List<NumeroStoriesDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
					? NumeroStoriesTransformer.INSTANCE.toLiteDtos(itemsSaved)
					: NumeroStoriesTransformer.INSTANCE.toDtos(itemsSaved);

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
			response.setItems(itemsDto);
			response.setHasError(false);
		}

		log.info("----end update NumeroStories-----");
		return response;
	}

	/**
	 * delete NumeroStories by using NumeroStoriesDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<NumeroStoriesDto> delete(Request<NumeroStoriesDto> request, Locale locale) {
		log.info("----begin delete NumeroStories-----");

		Response<NumeroStoriesDto> response = new Response<NumeroStoriesDto>();
		List<NumeroStories> items = new ArrayList<NumeroStories>();

		for (NumeroStoriesDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}
			// Verifier si la numeroStories existe
			NumeroStories existingEntity = null;
			existingEntity = numeroStoriesRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("numeroStories -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			numeroStoriesRepository.saveAll((Iterable<NumeroStories>) items);

			response.setHasError(false);
		}

		log.info("----end delete NumeroStories-----");
		return response;
	}

	/**
	 * get NumeroStories by using NumeroStoriesDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<NumeroStoriesDto> getByCriteria(Request<NumeroStoriesDto> request, Locale locale) throws Exception {
		log.info("----begin get NumeroStories-----");

		Response<NumeroStoriesDto> response = new Response<NumeroStoriesDto>();
		List<NumeroStories> items = numeroStoriesRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<NumeroStoriesDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
					? NumeroStoriesTransformer.INSTANCE.toLiteDtos(items)
					: NumeroStoriesTransformer.INSTANCE.toDtos(items);

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
			response.setCount(numeroStoriesRepository.count(request, em, locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("numeroStories", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get NumeroStories-----");
		return response;
	}

	/**
	 * get full NumeroStoriesDto by using NumeroStories as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private NumeroStoriesDto getFullInfos(NumeroStoriesDto dto, Integer size, Boolean isSimpleLoading, Locale locale)
			throws Exception {
		// put code here

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	public Response<NumeroStoriesDto> custom(Request<NumeroStoriesDto> request, Locale locale) {
		log.info("----begin custom NumeroStoriesDto-----");
		Response<NumeroStoriesDto> response = new Response<NumeroStoriesDto>();

		response.setHasError(false);
		response.setCount(1L);
		response.setItems(Arrays.asList(new NumeroStoriesDto()));
		log.info("----end custom NumeroStoriesDto-----");
		return response;
	} 

	public Response<NumeroStoriesDto> exportNumberStories(Request<NumeroStoriesDto> request, Locale locale) {
		log.info("----begin exportNumeroStorie -----");
		response = new Response<>();
		try {
			request.getData();
			response = getByCriteria(request, locale);
			if (response.getItems() != null && Utilities.isNotEmpty(response.getItems())) {
				SimpleDateFormat sdfFileName = new SimpleDateFormat(
						"dd_MMMMMMMMMMMMMMMM_yyyy_HH'H'_mm'min'_ss'Sec'_SSS");
				ClassPathResource templateClassPathResource = new ClassPathResource("templates/logs_actions.xlsx");
				InputStream fileInputStream = templateClassPathResource.getInputStream();
				XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
				XSSFSheet sheet = workbook.getSheetAt(0); // la 1ere feuille du classeur excel
				Cell cell = null;
				Row row = null;
				int rowIndex = 1;
				for (NumeroStoriesDto stories : response.getItems()) {
					// Renseigner les ids
					log.info("--row--" + rowIndex);
					row = sheet.getRow(rowIndex);
					if (row == null) {
						row = sheet.createRow(rowIndex);
					}
					cell = Utilities.getCell(row, 0);
					cell.setCellValue(rowIndex);
					cell = Utilities.getCell(row, 1);
					cell.setCellValue(Utilities.notBlank(stories.getNumero()) ? stories.getNumero() : "");
					cell = Utilities.getCell(row, 2);
					cell.setCellValue(Utilities.notBlank(stories.getStatut()) ? stories.getStatut() : "VIDE");
					cell = Utilities.getCell(row, 3);
					cell.setCellValue(stories.getStatusCode() != null ? stories.getStatusCode() : "MISE EN MACHINE");
					cell = Utilities.getCell(row, 4);
					cell.setCellValue(stories.getLogin() );
					cell = Utilities.getCell(row, 5);
					cell.setCellValue(stories.getCreatedAt());
					cell = Utilities.getCell(row, 6);
					cell.setCellValue(Utilities.notBlank(stories.getStatut()) ? stories.getStatut() : "VIDE");
					cell = Utilities.getCell(row, 7);
					cell.setCellValue(stories.getAdresseIp());
					cell = Utilities.getCell(row, 8);
					cell.setCellValue(stories.getContractId());
					cell = Utilities.getCell(row, 9);
					cell.setCellValue(stories.getPortNumber());
					cell = Utilities.getCell(row, 10);
					cell.setCellValue(stories.getSerialNumber());
					cell = Utilities.getCell(row, 11);
					cell.setCellValue(stories.getCategoryCode());
					rowIndex++;
				}
				// Ajuster les colonnes
				for (int i = 0; i <= Utilities.getColumnIndex("I"); i++) {
					sheet.autoSizeColumn(i);
				}

				fileInputStream.close();
				String fileName = Utilities.saveFile("LIST_OF_NUMBERS_STORIES" + sdfFileName.format(new Date()), "xlsx",
						workbook, paramsUtils);
				response.setFilePathDoc(Utilities.getFileUrlLink(fileName, paramsUtils));
				response.setHasError(Boolean.FALSE);
				response.setItems(response.getItems());
			} else {
				response.setItems(new ArrayList<>());
				response.setStatus(functionalError.DATA_EMPTY("Liste vide", locale));
				response.setHasError(Boolean.FALSE);
			}
			log.info("----end exportOlt -----");
		} catch (PermissionDeniedDataAccessException e) {
			exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (DataAccessResourceFailureException e) {
			exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
		} catch (DataAccessException e) {
			exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		} finally {
			if (response.isHasError() && response.getStatus() != null) {
				throw new RuntimeException(response.getStatus().getCode() + ";" + response.getStatus().getMessage());
			}
		}
		return response;
	}
}
