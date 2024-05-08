                                                    											
/*
 * Java business for entity table profil 
 * Created on 2021-03-19 ( Time 19:40:41 )
 * Generator tool : Telosys Tools Generator ( version 3.1.2 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.business;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import ci.smile.simswaporange.dao.entity.Fonctionnalite;
import ci.smile.simswaporange.dao.entity.Profil;
import ci.smile.simswaporange.dao.entity.ProfilFonctionnalite;
import ci.smile.simswaporange.dao.entity.User;
import ci.smile.simswaporange.dao.repository.FonctionnaliteRepository;
import ci.smile.simswaporange.dao.repository.ProfilFonctionnaliteRepository;
import ci.smile.simswaporange.dao.repository.ProfilRepository;
import ci.smile.simswaporange.dao.repository.UserRepository;
import ci.smile.simswaporange.utils.ExceptionUtils;
import ci.smile.simswaporange.utils.FunctionalError;
import ci.smile.simswaporange.utils.ParamsUtils;
import ci.smile.simswaporange.utils.TechnicalError;
import ci.smile.simswaporange.utils.Utilities;
import ci.smile.simswaporange.utils.Validate;
import ci.smile.simswaporange.utils.contract.IBasicBusiness;
import ci.smile.simswaporange.utils.contract.Request;
import ci.smile.simswaporange.utils.contract.Response;
import ci.smile.simswaporange.utils.dto.FonctionnaliteDto;
import ci.smile.simswaporange.utils.dto.ProfilDto;
import ci.smile.simswaporange.utils.dto.ProfilFonctionnaliteDto;
import ci.smile.simswaporange.utils.dto.UserDto;
import ci.smile.simswaporange.utils.dto.transformer.FonctionnaliteTransformer;
import ci.smile.simswaporange.utils.dto.transformer.ProfilFonctionnaliteTransformer;
import ci.smile.simswaporange.utils.dto.transformer.ProfilTransformer;
import lombok.extern.java.Log;

/**
BUSINESS for table "profil"
 * 
 * @author Geo
 *
 */
@Log
@Component
@RequiredArgsConstructor
public class ProfilBusiness implements IBasicBusiness<Request<ProfilDto>, Response<ProfilDto>> {
	@PersistenceContext
	private EntityManager em;
	private final ParamsUtils paramsUtils;
	private final UserRepository userRepository;
	private final FunctionalError functionalError;
	private final ProfilRepository profilRepository;
	private final FonctionnaliteRepository fonctionnaliteRepository;
	private final ProfilFonctionnaliteBusiness profilFonctionnaliteBusiness;
	private final ProfilFonctionnaliteRepository profilFonctionnaliteRepository;
	/**
	 * create Profil by using ProfilDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ProfilDto> create(Request<ProfilDto> request, Locale locale)  throws ParseException {
		log.info("----begin create Profil-----");

		Response<ProfilDto> response = new Response<ProfilDto>();
		List<Profil>        items    = new ArrayList<Profil>();
			
		for (ProfilDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("libelle", dto.getLibelle());
			fieldsToVerify.put("datasFonctionalites", dto.getDatasFonctionalites());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verify if profil to insert do not exist
			Profil existingEntity = null;
			dto.setCode("P-"+Utilities.generateAlphanumericCode(4));
			// verif unique code in db
			existingEntity = profilRepository.findByCode(dto.getCode(), false);
			while (existingEntity != null) {
				dto.setCode("P-"+Utilities.generateAlphanumericCode(4));
			}
			if(Utilities.isEmpty(dto.getDatasFonctionalites())) {
				response.setStatus(functionalError.FIELD_EMPTY("Veuillez renseigner le ou les fonctionnalités du profil", locale));
				response.setHasError(true);
				return response;
			}
			// verif unique code in db
			existingEntity = profilRepository.findByCode(dto.getCode(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("profil code -> " + dto.getCode(), locale));
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
			existingEntity = profilRepository.findByLibelle(dto.getLibelle(), false);
			if (existingEntity != null) {
				response.setStatus(functionalError.DATA_EXIST("profil libelle -> " + dto.getLibelle(), locale));
				response.setHasError(true);
				return response;
			}
			// verif unique libelle in items to save
			if (items.stream().anyMatch(a -> a.getLibelle().equalsIgnoreCase(dto.getLibelle()))) {
				response.setStatus(functionalError.DATA_DUPLICATE(" libelle ", locale));
				response.setHasError(true);
				return response;
			}


			dto.setCreatedAt(Utilities.getCurrentDateTime());
			Profil entityToSave = null;
			entityToSave = ProfilTransformer.INSTANCE.toEntity(dto);
			entityToSave.setCreatedBy(request.getUser());
			entityToSave.setIsDeleted(false);
			items.add(entityToSave);
			
			Profil entitySaved = profilRepository.save(entityToSave);	
			if (entitySaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("profil", locale));
				response.setHasError(true);
				return response;
			}

			List<ProfilFonctionnaliteDto> datasProfilFunctionality = new ArrayList<ProfilFonctionnaliteDto>();
			if(Utilities.isNotEmpty(dto.getDatasFonctionalites())) {
				dto.getDatasFonctionalites().forEach(f->{
					ProfilFonctionnaliteDto roleFunctionalityDto = new ProfilFonctionnaliteDto();
					roleFunctionalityDto.setProfilId(entitySaved.getId());
					roleFunctionalityDto.setFonctionnaliteId(f.getId());
					datasProfilFunctionality.add(roleFunctionalityDto);
				});

				Request<ProfilFonctionnaliteDto> subRequest = new Request<ProfilFonctionnaliteDto>();
				subRequest.setDatas(datasProfilFunctionality);
				subRequest.setUser(request.getUser());
				Response<ProfilFonctionnaliteDto> subResponse = profilFonctionnaliteBusiness.create(subRequest, locale);
				if(subResponse.isHasError()){
					response.setStatus(subResponse.getStatus());
					response.setHasError(true);
					return response;
				}
			}
		}
		
		if (!items.isEmpty()) {
			List<Profil> itemsSaved = null;
			// inserer les donnees en base de donnees
			itemsSaved = profilRepository.saveAll((Iterable<Profil>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("profil", locale));
				response.setHasError(true);
				return response;
			}
			
			
			List<ProfilDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ProfilTransformer.INSTANCE.toLiteDtos(itemsSaved) : ProfilTransformer.INSTANCE.toDtos(itemsSaved);

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
			response.setStatus(functionalError.SUCCESS("profil", locale));
			response.setHasError(false);
		}

		log.info("----end create Profil-----");
		response.setActionEffectue("Creer Profil");

		return response;
	}

	/**
	 * update Profil by using ProfilDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ProfilDto> update(Request<ProfilDto> request, Locale locale)  throws ParseException {
		log.info("----begin update Profil-----");

		Response<ProfilDto> response = new Response<ProfilDto>();
		List<Profil>        items    = new ArrayList<Profil>();
			
		for (ProfilDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la profil existe
			Profil entityToSave = null;
			entityToSave = profilRepository.findOne(dto.getId(), false);
			if (entityToSave == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("profil id -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}
			

			if (Utilities.notBlank(dto.getCode())) {
				entityToSave.setCode(dto.getCode());
			}
			if (Utilities.notBlank(dto.getLibelle())) {
				Profil existingEntity = profilRepository.findByLibelle(dto.getLibelle(), false);
				if (existingEntity != null && existingEntity.getId() != entityToSave.getId()) {
					response.setStatus(functionalError.DATA_EXIST("profil libelle -> " + dto.getLibelle(), locale));
					response.setHasError(true);
					return response;
				}
				// verif unique libelle in items to save
				if (items.stream().anyMatch(a -> a.getLibelle().equalsIgnoreCase(dto.getLibelle()))) {
					response.setStatus(functionalError.DATA_DUPLICATE(" libelle ", locale));
					response.setHasError(true);
					return response;
				}
				entityToSave.setLibelle(dto.getLibelle());
			}
			if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
				entityToSave.setCreatedBy(dto.getCreatedBy());
			}
			if (dto.getUpdatedBy() != null && dto.getUpdatedBy() > 0) {
				entityToSave.setUpdatedBy(dto.getUpdatedBy());
			}
			if (dto.getDeletedBy() != null && dto.getDeletedBy() > 0) {
				entityToSave.setDeletedBy(dto.getDeletedBy());
			}
			List<ProfilFonctionnalite> currents = profilFonctionnaliteRepository.findByProfilId(entityToSave.getId(), false);
			entityToSave.setUpdatedAt(Utilities.getCurrentDate());
			entityToSave.setUpdatedBy(request.getUser());
			
			Profil entitySaved = profilRepository.save(entityToSave);
			if (entitySaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("profil", locale));
				response.setHasError(true);
				return response;
			}

			
			items.add(entityToSave);
			
			if (Utilities.isNotEmpty(dto.getDatasFonctionalites())) {

				List<ProfilFonctionnalite> currentsToDelete = new ArrayList<ProfilFonctionnalite>();

				if(Utilities.isNotEmpty(currentsToDelete)) {
					Request<ProfilFonctionnaliteDto> subRequest = new Request<ProfilFonctionnaliteDto>();
					subRequest.setUser(request.getUser());
					subRequest.setDatas(ProfilFonctionnaliteTransformer.INSTANCE.toDtos(currentsToDelete));
					Response<ProfilFonctionnaliteDto> subResponse = profilFonctionnaliteBusiness.delete(subRequest, locale);
					if(subResponse.isHasError()){
						response.setStatus(subResponse.getStatus());
						response.setHasError(true);
						return response;
					}
				}
				else 
				{
					List<FonctionnaliteDto> datasCreate = dto.getDatasFonctionalites().stream().filter(df->currents.stream().noneMatch(crf->crf.getFonctionnalite().getId().equals(df.getId()))).collect(Collectors.toList());
					List<ProfilFonctionnalite> datasDelete = currents.stream().filter(crf->dto.getDatasFonctionalites().stream().noneMatch(df->df.getId().equals(crf.getFonctionnalite().getId()))).collect(Collectors.toList());

					Request<ProfilFonctionnaliteDto> subRequest = new Request<ProfilFonctionnaliteDto>();
					subRequest.setUser(request.getUser());

					//Suppression des fonctionnalite du menu à supprimer
					if(Utilities.isNotEmpty(datasDelete)) {
						subRequest.setDatas(ProfilFonctionnaliteTransformer.INSTANCE.toDtos(datasDelete));
						Response<ProfilFonctionnaliteDto> subResponse = profilFonctionnaliteBusiness.delete(subRequest, locale);
						if(subResponse.isHasError()){
							response.setStatus(subResponse.getStatus());
							response.setHasError(true);
							return response;
						}
					}

					//Création des fonctionnalite du menu à créer
					if(Utilities.isNotEmpty(datasCreate)) {
						List<ProfilFonctionnaliteDto> datasProfilFunctionality = new ArrayList<ProfilFonctionnaliteDto>();
						datasCreate.forEach(f->{
							ProfilFonctionnaliteDto roleFunctionalityDto = new ProfilFonctionnaliteDto();
							roleFunctionalityDto.setProfilId(entitySaved.getId());
							roleFunctionalityDto.setFonctionnaliteId(f.getId());
							datasProfilFunctionality.add(roleFunctionalityDto);
						});
						subRequest.setDatas(datasProfilFunctionality);
						Response<ProfilFonctionnaliteDto> subResponse = profilFonctionnaliteBusiness.create(subRequest, locale);
						if(subResponse.isHasError()){
							response.setStatus(subResponse.getStatus());
							response.setHasError(true);
							return response;
						}
					}
				}
			}

			
		}

		if (!items.isEmpty()) {
			List<Profil> itemsSaved = null;
			// maj les donnees en base
			itemsSaved = profilRepository.saveAll((Iterable<Profil>) items);
			if (itemsSaved == null) {
				response.setStatus(functionalError.SAVE_FAIL("profil", locale));
				response.setHasError(true);
				return response;
			}
			List<ProfilDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ProfilTransformer.INSTANCE.toLiteDtos(itemsSaved) : ProfilTransformer.INSTANCE.toDtos(itemsSaved);

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

		log.info("----end update Profil-----");
		response.setActionEffectue("Modifier Profil");

		return response;
	}

	/**
	 * delete Profil by using ProfilDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ProfilDto> delete(Request<ProfilDto> request, Locale locale)  {
		log.info("----begin delete Profil-----");

		Response<ProfilDto> response = new Response<ProfilDto>();
		List<Profil>        items    = new ArrayList<Profil>();
			
		for (ProfilDto dto : request.getDatas()) {
			// Definir les parametres obligatoires
			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
			fieldsToVerify.put("id", dto.getId());
			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
				response.setHasError(true);
				return response;
			}

			// Verifier si la profil existe
			Profil existingEntity = null;
			existingEntity = profilRepository.findOne(dto.getId(), false);
			if (existingEntity == null) {
				response.setStatus(functionalError.DATA_NOT_EXIST("profil -> " + dto.getId(), locale));
				response.setHasError(true);
				return response;
			}
			// user
			List<User> listOfUser = userRepository.findByIdProfil(existingEntity.getId(), false);
			if (listOfUser != null && !listOfUser.isEmpty()){
				response.setStatus(functionalError.DATA_NOT_DELETABLE("(" + listOfUser.size() + ")", locale));
				response.setHasError(true);
				return response;
			}

			existingEntity.setDeletedAt(Utilities.getCurrentDate());
			existingEntity.setDeletedBy(request.getUser());
			existingEntity.setIsDeleted(true);
			items.add(existingEntity);
		}

		if (!items.isEmpty()) {
			// supprimer les donnees en base
			profilRepository.saveAll((Iterable<Profil>) items);
			
			response.setStatus(functionalError.SUCCESS("profil supprimé", locale));
			response.setHasError(false);
		}

		log.info("----end delete Profil-----");
		response.setActionEffectue("Supprimer Profil");

		return response;
	}

	/**
	 * get Profil by using ProfilDto as object.
	 * 
	 * @param request
	 * @return response
	 * 
	 */
	@Override
	public Response<ProfilDto> getByCriteria(Request<ProfilDto> request, Locale locale)  throws Exception {
		log.info("----begin get Profil-----");

		Response<ProfilDto> response = new Response<ProfilDto>();
		List<Profil> items 			 = profilRepository.getByCriteria(request, em, locale);
		

		if (items != null && !items.isEmpty()) {
			List<ProfilDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading())) ? ProfilTransformer.INSTANCE.toLiteDtos(items) : ProfilTransformer.INSTANCE.toDtos(items);

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
			if (Utilities.isNotEmpty(itemsDto)) {
				response.setFilePathDoc(writeFileName(itemsDto));
			}
			response.setItems(itemsDto);
			response.setCount(profilRepository.count(request, em, locale));
			response.setStatus(functionalError.SUCCESS("", locale));
			response.setHasError(false);
		} else {
			response.setStatus(functionalError.DATA_EMPTY("profil", locale));
			response.setHasError(false);
			return response;
		}

		log.info("----end get Profil-----");
		response.setActionEffectue("Vue Profil");

		return response;
	}

	/**
	 * get full ProfilDto by using Profil as object.
	 * 
	 * @param dto
	 * @param size
	 * @param isSimpleLoading
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	private ProfilDto getFullInfos(ProfilDto dto, Integer size, Boolean isSimpleLoading, Locale locale) throws Exception {
		// put code here
		if (dto != null){
			List<Fonctionnalite> datasFonctionnalite = profilFonctionnaliteRepository.findFonctionnaliteByProfilId(dto.getId(), false);
			if (Utilities.isNotEmpty(datasFonctionnalite)) {
			    List<FonctionnaliteDto> datasFonctionnaliteDto = FonctionnaliteTransformer.INSTANCE.toDtos(datasFonctionnalite);
			    List<FonctionnaliteDto> rootFonctionnalites = new ArrayList<>();
			    for (FonctionnaliteDto foncDto : datasFonctionnaliteDto) {
			        // Identifier les fonctionnalités racines (celles sans parent)
			        if (foncDto.getIdParent() == null) {
			            rootFonctionnalites.add(foncDto);
			        } else {
			            rootFonctionnalites.add(foncDto);
			        	Fonctionnalite fonctionnalite = fonctionnaliteRepository.findOne(foncDto.getIdParent(), Boolean.FALSE);
			        	if (fonctionnalite != null) {
			        	    FonctionnaliteDto parentDto = FonctionnaliteTransformer.INSTANCE.toDto(fonctionnalite);
			        	    if (!containsFonctionnaliteWithLibelle(rootFonctionnalites, parentDto.getLibelle())) {
			        	        rootFonctionnalites.add(parentDto);
			        	    }
			        	}

			        }
			    }
			    dto.setDatasFonctionalites(rootFonctionnalites);
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
	
	private boolean containsFonctionnaliteWithLibelle(List<FonctionnaliteDto> fonctionnalites, String libelle) {
	    for (FonctionnaliteDto foncDto : fonctionnalites) {
	        if (foncDto.getLibelle() != null && foncDto.getLibelle().equals(libelle)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public String writeFileName(List<ProfilDto> dataActionLite) throws Exception {
		SimpleDateFormat sdfFileName = new SimpleDateFormat("dd_MMMMMMMMMMMMMMMM_yyyy_HH'H'_mm'min'_ss'Sec'_SSS");
		ClassPathResource templateClassPathResource = new ClassPathResource("templates/Liste_KPI_PROFIL_FONCTIONNALITE_Exporte.xlsx");
		InputStream inputStreamFile = templateClassPathResource.getInputStream();
		XSSFWorkbook workbook = new XSSFWorkbook(inputStreamFile);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Cell cell = null;
		Row row = null;
		int rowIndex = 1;
		for (ProfilDto profil : dataActionLite) {
			// Renseigner les ids
			log.info("--row--" + rowIndex);
			row = sheet.getRow(rowIndex);
			if (row == null) {
				row = sheet.createRow(rowIndex);
			}
			cell = getCell(row, 0);
			cell.setCellValue(profil.getLibelle());
			cell = getCell(row, 1);
			cell.setCellValue(profil.getCreatedAt());
			User createdBy = userRepository.findOne(profil.getCreatedBy(), Boolean.FALSE);
			cell = getCell(row, 2);
			cell.setCellValue(createdBy.getLogin());
			User updatedBy = userRepository.findOne(profil.getUpdatedBy(), Boolean.FALSE);
			cell = getCell(row, 3);
			cell.setCellValue(updatedBy != null ? updatedBy.getLogin() : "");
			cell = getCell(row, 4);
			cell.setCellValue(profil.getUpdatedAt());
			cell = getCell(row, 5);
			List<String> fonct = profil.getDatasFonctionalites().stream().map(arg -> arg.getLibelle()).collect(Collectors.toList());
			cell.setCellValue(Utilities.isNotEmpty(fonct) ? fonct.toString() : "");
		
			rowIndex++;
		}
		for (int i = 0; i <= Utilities.getColumnIndex("I"); i++) {
			sheet.autoSizeColumn(i);
		}
		inputStreamFile.close();
		String fileName = Utilities.saveFile("LIST_PROFIL_" + sdfFileName.format(new Date()), "xlsx", workbook,
				paramsUtils);
		return Utilities.getFileUrlLink(fileName, paramsUtils);
	}

	public static Cell getCell(Row row, Integer index) {
		return (row.getCell(index) == null) ? row.createCell(index) : row.getCell(index);
	}

}
