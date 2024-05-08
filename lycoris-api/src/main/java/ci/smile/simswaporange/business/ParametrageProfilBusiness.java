                                        										
/*
 * Java business for entity table parametrage_profil 
 * Created on 2023-06-21 ( Time 19:39:01 )
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
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.io.InputStream;
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
import ci.smile.simswaporange.dao.entity.ParametrageProfil;
import ci.smile.simswaporange.dao.entity.*;
import ci.smile.simswaporange.dao.repository.*;

/**
BUSINESS for table "parametrage_profil"
 * 
 * @author Geo
 *
 */
@Log
@Component
public class ParametrageProfilBusiness implements IBasicBusiness<Request<ParametrageProfilDto>, Response<ParametrageProfilDto>> {

	private Response<ParametrageProfilDto> response;
	@Autowired
	private ParametrageProfilRepository parametrageProfilRepository;
	@Autowired
	private FunctionalError functionalError;
	@Autowired
	private TechnicalError technicalError;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ExceptionUtils exceptionUtils; 
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private ParamsUtils paramsUtils;

	private SimpleDateFormat dateFormat;
	private SimpleDateFormat dateTimeFormat;

	public ParametrageProfilBusiness() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * create ParametrageProfil by using ParametrageProfilDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ParametrageProfilDto> create(Request<ParametrageProfilDto> request, Locale locale)  throws ParseException {
		log.info("----begin create ParametrageProfil-----");

		Response<ParametrageProfilDto> response = new Response<ParametrageProfilDto>();
		List<ParametrageProfil>        items    = new ArrayList<ParametrageProfil>();
			
		for (ParametrageProfilDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("email", dto.getEmail());
			fieldsToVerify.put("nomPrenom", dto.getNomPrenom());
			fieldsToVerify.put("idParametrage", dto.getIdParametrage());
			fieldsToVerify.put("numero", dto.getNumero());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if parametrageProfil to insert do not exist
			ParametrageProfil existingEntity = null;

			// verif unique email in db
			existingEntity = parametrageProfilRepository.findByEmail(dto.getEmail(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("parametrageProfil email -> " + dto.getEmail(), locale));
				response.setHasError(true);
				return response;
			}
			// verif unique email in items to save
			if (items.stream().anyMatch(a -> a.getEmail().equalsIgnoreCase(dto.getEmail()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" email ", locale));
				response.setHasError(true);
				return response;
			}

			// verif unique numero in db
			existingEntity = parametrageProfilRepository.findByNumero(dto.getNumero(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("parametrageProfil numero -> " + dto.getNumero(), locale));
				response.setHasError(true);
				return response;
			}
			// verif unique numero in items to save
			if (items.stream().anyMatch(a -> a.getNumero().equalsIgnoreCase(dto.getNumero()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" numero ", locale));
				response.setHasError(true);
				return response;
			}

				ParametrageProfil entityToSave = null;
			entityToSave = ParametrageProfilTransformer.INSTANCE.toEntity(dto);
			entityToSave.setCreatedAt(Utilities.getCurrentDate());
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<ParametrageProfil> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = parametrageProfilRepository.saveAll((Iterable<ParametrageProfil>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("parametrageProfil", locale));
				response.setHasError(true);
				return response;
			}
			List<ParametrageProfilDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ParametrageProfilTransformer.INSTANCE.toLiteDtos(itemsSaved) : ParametrageProfilTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end create ParametrageProfil-----");
		return response;
	}

	/**
	 * update ParametrageProfil by using ParametrageProfilDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ParametrageProfilDto> update(Request<ParametrageProfilDto> request, Locale locale)  throws ParseException {
		log.info("----begin update ParametrageProfil-----");

		Response<ParametrageProfilDto> response = new Response<ParametrageProfilDto>();
		List<ParametrageProfil>        items    = new ArrayList<ParametrageProfil>();
			
		for (ParametrageProfilDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la parametrageProfil existe
			ParametrageProfil entityToSave = null;
			entityToSave = parametrageProfilRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("parametrageProfil id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}

			if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
				entityToSave.setCreatedBy(dto.getCreatedBy());
			}
			if (Utilities.notBlank(dto.getEmail())) {
				entityToSave.setEmail(dto.getEmail());
			}
			if (Utilities.notBlank(dto.getNomPrenom())) {
				entityToSave.setNomPrenom(dto.getNomPrenom());
			}
			if (dto.getIdParametrage() != null && dto.getIdParametrage() > 0) {
				entityToSave.setIdParametrage(dto.getIdParametrage());
			}
			if (Utilities.notBlank(dto.getNumero())) {
				entityToSave.setNumero(dto.getNumero());
			}
			entityToSave.setUpdatedAt(Utilities.getCurrentDate());
			items.add(entityToSave);
		}

		if (!items.isEmpty()) {
			List<ParametrageProfil> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = parametrageProfilRepository.saveAll((Iterable<ParametrageProfil>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("parametrageProfil", locale));
				response.setHasError(true);
				return response;
			}
			List<ParametrageProfilDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ParametrageProfilTransformer.INSTANCE.toLiteDtos(itemsSaved) : ParametrageProfilTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update ParametrageProfil-----");
		return response;
	}

	/**
	 * delete ParametrageProfil by using ParametrageProfilDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ParametrageProfilDto> delete(Request<ParametrageProfilDto> request, Locale locale)  {
		log.info("----begin delete ParametrageProfil-----");

		Response<ParametrageProfilDto> response = new Response<ParametrageProfilDto>();
		List<ParametrageProfil>        items    = new ArrayList<ParametrageProfil>();
			
		for (ParametrageProfilDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la parametrageProfil existe
			ParametrageProfil existingEntity = null;
			existingEntity = parametrageProfilRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("parametrageProfil -> " + dto.getId(), locale));
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
			parametrageProfilRepository.saveAll((Iterable<ParametrageProfil>) items);
			response.setStatus(functionalError.SUCCESS("", locale));
			response.setHasError(false);
		}

		log.info("----end delete ParametrageProfil-----");
		return response;
	}

	/**
	 * get ParametrageProfil by using ParametrageProfilDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ParametrageProfilDto> getByCriteria(Request<ParametrageProfilDto> request, Locale locale)  throws Exception {
		log.info("----begin get ParametrageProfil-----");

		Response<ParametrageProfilDto> response = new Response<ParametrageProfilDto>();
		List<ParametrageProfil> items 			 = parametrageProfilRepository.getByCriteria(request, em, locale);

		if (items != null && !items.isEmpty()) {
			List<ParametrageProfilDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ParametrageProfilTransformer.INSTANCE.toLiteDtos(items) : ParametrageProfilTransformer.INSTANCE.toDtos(items);

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
			if (Utilities.isNotEmpty(itemsDto)) {
				response.setFilePathDoc(writeFileName(itemsDto));
			}
			response.setCount(parametrageProfilRepository.count(request, em, locale));
			response.setStatus(functionalError.SUCCESS("", locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("parametrageProfil", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get ParametrageProfil-----");
		return response;
	}

	/**
	 * get full ParametrageProfilDto by using ParametrageProfil as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private ParametrageProfilDto getFullInfos(ParametrageProfilDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here

		if (Utilities.isTrue(isSimpleLoading)) {
			return dto;
		}
		if (size > 1) {
			return dto;
		}

		return dto;
	}

	public Response<ParametrageProfilDto> custom(Request<ParametrageProfilDto> request, Locale locale) {
		log.info("----begin custom ParametrageProfilDto-----");
		Response<ParametrageProfilDto> response = new Response<ParametrageProfilDto>();
		
		response.setHasError(false);
		response.setCount(1L);
		response.setItems(Arrays.asList(new ParametrageProfilDto()));
		log.info("----end custom ParametrageProfilDto-----");
		return response;
	}
	
	
	public String writeFileName(List<ParametrageProfilDto> dataActionLite) throws Exception {
		SimpleDateFormat sdfFileName = new SimpleDateFormat("dd_MMMMMMMMMMMMMMMM_yyyy_HH'H'_mm'min'_ss'Sec'_SSS");
		ClassPathResource templateClassPathResource = new ClassPathResource("templates/list_of_diffusion.xlsx");
		InputStream inputStreamFile = templateClassPathResource.getInputStream();
		XSSFWorkbook workbook = new XSSFWorkbook(inputStreamFile);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Cell cell = null;
		Row row = null;
		int rowIndex = 1;
		for (ParametrageProfilDto user : dataActionLite) {
			// Renseigner les ids
			log.info("--row--" + rowIndex);
			row = sheet.getRow(rowIndex);
			if (row == null) {
				row = sheet.createRow(rowIndex);
			}
			cell = getCell(row, 0);
			cell.setCellValue(user.getNomPrenom());
			cell = getCell(row, 1);
			cell.setCellValue(user.getEmail());
			cell = getCell(row, 2);
			cell.setCellValue(user.getNumero());
			cell = getCell(row, 3);
			cell.setCellValue(user.getCreatedAt());
			User userFound = userRepository.findOne(user.getCreatedBy(), Boolean.FALSE);
			if (userFound != null) {
				cell = getCell(row, 4);
				cell.setCellValue(userFound.getLogin() + "/" + userFound.getNom());
				cell = getCell(row, 5);
				cell.setCellValue(userFound.getProfil() != null ? userFound.getProfil().getLibelle() : "");
				cell = getCell(row, 6);
				cell.setCellValue(userFound.getContact() + "/" +userFound.getEmailAdresse());
			}
			
			
			rowIndex++;
		}
		// Ajuster les colonnes
		for (int i = 0; i <= Utilities.getColumnIndex("I"); i++) {
			sheet.autoSizeColumn(i);
		}
		inputStreamFile.close();
		String fileName = Utilities.saveFile("LIST_DIFFUSION_" + sdfFileName.format(new Date()), "xlsx", workbook,
				paramsUtils);

		return Utilities.getFileUrlLink(fileName, paramsUtils);
	}
	public static Cell getCell(Row row, Integer index) {
		return (row.getCell(index) == null) ? row.createCell(index) : row.getCell(index);
	}
}
