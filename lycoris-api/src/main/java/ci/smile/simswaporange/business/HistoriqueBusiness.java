
/*
 * Java business for entity table historique
 * Created on 2023-07-05 ( Time 13:10:58 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.business;

import ci.smile.simswaporange.dao.constante.Constant;
import ci.smile.simswaporange.dao.entity.Status;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import ci.smile.simswaporange.utils.*;
import ci.smile.simswaporange.utils.dto.*;
import ci.smile.simswaporange.utils.enums.*;
import ci.smile.simswaporange.utils.contract.*;
import ci.smile.simswaporange.utils.contract.IBasicBusiness;
import ci.smile.simswaporange.utils.contract.Request;
import ci.smile.simswaporange.utils.contract.Response;
import ci.smile.simswaporange.utils.dto.transformer.*;
import ci.smile.simswaporange.dao.entity.Historique;
import ci.smile.simswaporange.dao.entity.ActionSimswap;
import ci.smile.simswaporange.dao.entity.*;
import ci.smile.simswaporange.dao.repository.*;

/**
 * BUSINESS for table "historique"
 *
 * @author Geo
 */
@Log
@Component
public class HistoriqueBusiness implements IBasicBusiness<Request<HistoriqueDto>, Response<HistoriqueDto>> {

    private Response<HistoriqueDto> response;
    @Autowired
    private HistoriqueRepository historiqueRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActionSimswapRepository actionSimswapRepository;
    @Autowired
    private FunctionalError functionalError;
    @Autowired
    private TechnicalError technicalError;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private ExceptionUtils exceptionUtils;
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ParamsUtils paramsUtils;

    private SimpleDateFormat dateFormat;
    private SimpleDateFormat dateTimeFormat;

    public HistoriqueBusiness() {
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    /**
     * create Historique by using HistoriqueDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<HistoriqueDto> create(Request<HistoriqueDto> request, Locale locale) throws ParseException {
        log.info("----begin create Historique-----");

        Response<HistoriqueDto> response = new Response<HistoriqueDto>();
        List<Historique> items = new ArrayList<Historique>();

        for (HistoriqueDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
//			Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
//			fieldsToVerify.put("request", dto.getRequest());
//			fieldsToVerify.put("response", dto.getResponse());
//			fieldsToVerify.put("idUser", dto.getIdUser());
//			fieldsToVerify.put("idStatus", dto.getIdStatus());
//			fieldsToVerify.put("date", dto.getDate());
//			fieldsToVerify.put("actionService", dto.getActionService());
//			fieldsToVerify.put("login", dto.getLogin());
//			fieldsToVerify.put("nom", dto.getNom());
//			fieldsToVerify.put("prenom", dto.getPrenom());
//			fieldsToVerify.put("idActionUser", dto.getIdActionUser());
//			fieldsToVerify.put("isConnexion", dto.getIsConnexion());
//			fieldsToVerify.put("statusConnection", dto.getStatusConnection());
//			fieldsToVerify.put("machine", dto.getMachine());
//			fieldsToVerify.put("ipadress", dto.getIpadress());
//			if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
//				response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
//				response.setHasError(true);
//				return response;
//			}
//
//			// Verify if historique to insert do not exist
//			Historique existingEntity = null;
////			if (existingEntity != null) {
////				response.setStatus(functionalError.DATA_EXIST("historique id -> " + dto.getId(), locale));
////				response.setHasError(true);
////				return response;
////			}
//
//			// verif unique login in db
//			existingEntity = historiqueRepository.findByLogin(dto.getLogin(), false);
//			if (existingEntity != null) {
//				response.setStatus(functionalError.DATA_EXIST("historique login -> " + dto.getLogin(), locale));
//				response.setHasError(true);
//				return response;
//			}
            // verif unique login in items to save
            if (items.stream().anyMatch(a -> a.getLogin().equalsIgnoreCase(dto.getLogin()))) {
                response.setStatus(functionalError.DATA_DUPLICATE(" login ", locale));
                response.setHasError(true);
                return response;
            }
            // Verify if actionSimswap exist
            ActionSimswap existingActionSimswap = null;
            if (dto.getIdActionUser() != null && dto.getIdActionUser() > 0) {
                existingActionSimswap = actionSimswapRepository.findOne(dto.getIdActionUser(), false);
                if (existingActionSimswap == null) {
                    response.setStatus(functionalError
                            .DATA_NOT_EXIST("actionSimswap idActionUser -> " + dto.getIdActionUser(), locale));
                    response.setHasError(true);
                    return response;
                }
            }
            Status status = statusRepository.findOne(dto.getIdStatus(), Boolean.FALSE);
            User user = userRepository.findOne(dto.getIdUser(), Boolean.FALSE);
            Historique entityToSave = null;
            entityToSave = HistoriqueTransformer.INSTANCE.toEntity(dto, existingActionSimswap, user, status);
            entityToSave.setCreatedAt(Utilities.getCurrentDate());
            entityToSave.setCreatedBy(request.getUser());
            entityToSave.setIsDeleted(false);
            items.add(entityToSave);
        }
        if (!items.isEmpty()) {
            List<Historique> itemsSaved = null;
            // inserer les donnees en base de donnees
            itemsSaved = historiqueRepository.saveAll((Iterable<Historique>) items);
            if (itemsSaved == null) {
                response.setStatus(functionalError.SAVE_FAIL("historique", locale));
                response.setHasError(true);
                return response;
            }
            List<HistoriqueDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
                    ? HistoriqueTransformer.INSTANCE.toLiteDtos(itemsSaved)
                    : HistoriqueTransformer.INSTANCE.toDtos(itemsSaved);

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
            response.setStatus(functionalError.SUCCESS("", locale));
            response.setHasError(false);
        }

        log.info("----end create Historique-----");
        return response;
    }

    /**
     * update Historique by using HistoriqueDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<HistoriqueDto> update(Request<HistoriqueDto> request, Locale locale) throws ParseException {
        log.info("----begin update Historique-----");

        Response<HistoriqueDto> response = new Response<HistoriqueDto>();
        List<Historique> items = new ArrayList<Historique>();

        for (HistoriqueDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("id", dto.getId());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
                response.setHasError(true);
                return response;
            }

            // Verifier si la historique existe
            Historique entityToSave = null;
            entityToSave = historiqueRepository.findOne(dto.getId(), false);
            if (entityToSave == null) {
                response.setStatus(functionalError.DATA_NOT_EXIST("historique id -> " + dto.getId(), locale));
                response.setHasError(true);
                return response;
            }

            // Verify if actionSimswap exist
            if (dto.getIdActionUser() != null && dto.getIdActionUser() > 0) {
                ActionSimswap existingActionSimswap = actionSimswapRepository.findOne(dto.getIdActionUser(), false);
                if (existingActionSimswap == null) {
                    response.setStatus(functionalError
                            .DATA_NOT_EXIST("actionSimswap idActionUser -> " + dto.getIdActionUser(), locale));
                    response.setHasError(true);
                    return response;
                }
                entityToSave.setActionSimswap(existingActionSimswap);
            }

            if (dto.getIdUser() != null && dto.getIdUser() > 0) {
                User user = userRepository.findOne(dto.getIdUser(), false);
                if (user == null) {
                    response.setStatus(functionalError
                            .DATA_NOT_EXIST("user idActionUser -> " + dto.getIdUser(), locale));
                    response.setHasError(true);
                    return response;
                }
                entityToSave.setUser(user);
            }

            if (dto.getIdStatus() != null && dto.getIdStatus() > 0) {
                Status status = statusRepository.findOne(dto.getIdStatus(), false);
                if (status == null) {
                    response.setStatus(functionalError
                            .DATA_NOT_EXIST("status idActionUser -> " + dto.getIdStatus(), locale));
                    response.setHasError(true);
                    return response;
                }
                entityToSave.setStatus(status);
            }
            if (Utilities.notBlank(dto.getRequest())) {
                entityToSave.setRequest(dto.getRequest());
            }
            if (Utilities.notBlank(dto.getResponse())) {
                entityToSave.setResponse(dto.getResponse());
            }
            if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
                entityToSave.setCreatedBy(dto.getCreatedBy());
            }
            if (dto.getUpdatedBy() != null && dto.getUpdatedBy() > 0) {
                entityToSave.setUpdatedBy(dto.getUpdatedBy());
            }
            if (Utilities.notBlank(dto.getDate())) {
                entityToSave.setDate(dateFormat.parse(dto.getDate()));
            }
            if (Utilities.notBlank(dto.getActionService())) {
                entityToSave.setActionService(dto.getActionService());
            }
            if (Utilities.notBlank(dto.getLogin())) {
                entityToSave.setLogin(dto.getLogin());
            }
            if (Utilities.notBlank(dto.getNom())) {
                entityToSave.setNom(dto.getNom());
            }
            if (Utilities.notBlank(dto.getPrenom())) {
                entityToSave.setPrenom(dto.getPrenom());
            }
            if (dto.getIsConnexion() != null) {
                entityToSave.setIsConnexion(dto.getIsConnexion());
            }
            if (Utilities.notBlank(dto.getStatusConnection())) {
                entityToSave.setStatusConnection(dto.getStatusConnection());
            }
            if (Utilities.notBlank(dto.getMachine())) {
                entityToSave.setMachine(dto.getMachine());
            }
            if (Utilities.notBlank(dto.getIpadress())) {
                entityToSave.setIpadress(dto.getIpadress());
            }
            entityToSave.setUpdatedAt(Utilities.getCurrentDate());
            entityToSave.setUpdatedBy(request.getUser());
            items.add(entityToSave);
        }

        if (!items.isEmpty()) {
            List<Historique> itemsSaved = null;
            // maj les donnees en base
            itemsSaved = historiqueRepository.saveAll((Iterable<Historique>) items);
            if (itemsSaved == null) {
                response.setStatus(functionalError.SAVE_FAIL("historique", locale));
                response.setHasError(true);
                return response;
            }
            List<HistoriqueDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
                    ? HistoriqueTransformer.INSTANCE.toLiteDtos(itemsSaved)
                    : HistoriqueTransformer.INSTANCE.toDtos(itemsSaved);

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
            response.setStatus(functionalError.SUCCESS("", locale));
            response.setHasError(false);
        }

        log.info("----end update Historique-----");
        return response;
    }

    /**
     * delete Historique by using HistoriqueDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<HistoriqueDto> delete(Request<HistoriqueDto> request, Locale locale) {
        log.info("----begin delete Historique-----");

        Response<HistoriqueDto> response = new Response<HistoriqueDto>();
        List<Historique> items = new ArrayList<Historique>();

        for (HistoriqueDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, java.lang.Object> fieldsToVerify = new HashMap<String, java.lang.Object>();
            fieldsToVerify.put("id", dto.getId());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
                response.setHasError(true);
                return response;
            }

            // Verifier si la historique existe
            Historique existingEntity = null;
            existingEntity = historiqueRepository.findOne(dto.getId(), false);
            if (existingEntity == null) {
                response.setStatus(functionalError.DATA_NOT_EXIST("historique -> " + dto.getId(), locale));
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
            historiqueRepository.saveAll((Iterable<Historique>) items);
            response.setStatus(functionalError.SUCCESS("", locale));
            response.setHasError(false);
        }

        log.info("----end delete Historique-----");
        return response;
    }

    /**
     * get Historique by using HistoriqueDto as object.
     *
     * @param request
     * @return response
     */
    @Override
    public Response<HistoriqueDto> getByCriteria(Request<HistoriqueDto> request, Locale locale) throws Exception {
        log.info("----begin get Historique-----");

        Response<HistoriqueDto> response = new Response<HistoriqueDto>();
        List<Historique> items = historiqueRepository.getByCriteria(request, em, locale);

        if (items != null && !items.isEmpty()) {
            List<HistoriqueDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
                    ? HistoriqueTransformer.INSTANCE.toLiteDtos(items)
                    : HistoriqueTransformer.INSTANCE.toDtos(items);

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
            response.setCount(historiqueRepository.count(request, em, locale));
            response.setHasError(false);
        } else {
            response.setStatus(functionalError.DATA_EMPTY("historique", locale));
            response.setHasError(false);
            return response;
        }

        log.info("----end get Historique-----");
        return response;
    }

    /**
     * get full HistoriqueDto by using Historique as object.
     *
     * @param dto
     * @param size
     * @param isSimpleLoading
     * @param locale
     * @return
     * @throws Exception
     */
    private HistoriqueDto getFullInfos(HistoriqueDto dto, Integer size, Boolean isSimpleLoading, Locale locale)
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

    public Response<HistoriqueDto> custom(Request<HistoriqueDto> request, Locale locale) {
        log.info("----begin custom HistoriqueDto-----");
        Response<HistoriqueDto> response = new Response<HistoriqueDto>();

        response.setHasError(false);
        response.setCount(1L);
        response.setItems(Arrays.asList(new HistoriqueDto()));
        log.info("----end custom HistoriqueDto-----");
        return response;
    }

    public static Cell getCell(Row row, Integer index) {
        return (row.getCell(index) == null) ? row.createCell(index) : row.getCell(index);
    }

    public Response<HistoriqueDto> exportHistorique(Request<HistoriqueDto> request, Locale locale) {
        log.info("----begin exportHistorique -----");
        response = new Response<>();
        List<Historique> historiques = historiqueRepository.findAll();
        try {
            request.getData();
            response = getByCriteria(request, locale);
            if (historiques != null && Utilities.isNotEmpty(historiques)) {
                SimpleDateFormat sdfFileName = new SimpleDateFormat(
                        "dd_MMMMMMMMMMMMMMMM_yyyy_HH'H'_mm'min'_ss'Sec'_SSS");
                ClassPathResource templateClassPathResource = new ClassPathResource(
                        "templates/list_of_historique.xlsx");
                InputStream fileInputStream = templateClassPathResource.getInputStream();
                XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
                XSSFSheet sheet = workbook.getSheetAt(0); // la 1ere feuille du classeur excel
                Cell cell = null;
                Row row = null;
                int rowIndex = 1;
                for (Historique historique : historiques) {
                    log.info("--row--" + rowIndex);
                    row = sheet.getRow(rowIndex);
                    if (row == null) {
                        row = sheet.createRow(rowIndex);
                    }
                    cell = getCell(row, 0);
                    cell.setCellValue(
                            Utilities.notBlank(historique.getId().toString()) ? historique.getId().toString() : "");
                    cell = getCell(row, 1);
                    cell.setCellValue(Utilities.notBlank(historique.getLogin()) ? historique.getLogin() : "");

                    cell = getCell(row, 2);
                    cell.setCellValue(Utilities.notBlank(historique.getNom() + " " + historique.getPrenom())
                            ? historique.getNom() + " " + historique.getPrenom()
                            : "");
                    cell = getCell(row, 3);
                    cell.setCellValue(
                            Utilities.notBlank(historique.getActionService()) ? historique.getActionService() : "");
                    cell = getCell(row, 4);
                    cell.setCellValue(Utilities.notBlank(historique.getCreatedAt().toString())
                            ? historique.getCreatedAt().toString()
                            : "");
                    rowIndex++;
                }
                // Ajuster les colonnes
                for (int i = 0; i <= Utilities.getColumnIndex("I"); i++) {
                    sheet.autoSizeColumn(i);
                }
                fileInputStream.close();
                String fileName = Utilities.saveFile("LIST_OF_historique" + sdfFileName.format(new Date()), "xlsx",
                        workbook, paramsUtils);
                response.setFilePathDoc(Utilities.getFileUrlLink(fileName, paramsUtils));
                response.setHasError(Boolean.FALSE);
                response.setStatus(functionalError.SUCCESS("", locale));
                response.setItems(null);
            } else {
                response.setItems(new ArrayList<>());
                response.setStatus(functionalError.DATA_EMPTY("Liste vide", locale));
                response.setHasError(Boolean.FALSE);
            }
            log.info("----end exportHistorique -----");
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

    public Response<HistoriqueDto> exportLogsUser(Request<HistoriqueDto> request, Locale locale) throws Exception {
        Response<HistoriqueDto> response = new Response<>();
        log.info("----begin exportHistorique -----");
        if (request.getData().getIdActionUser().equals(StatusApiEnum.ACTION_LOGS_CONNEXION)) {
            if (request.getData().getForLogConnexion() == Boolean.FALSE) {
                List<User> userList = userRepository.findByIsDeleted(Boolean.FALSE);
                String fileName = extraireFichierPourToutesLesConnexions(userList);
                List<HistoriqueDto> historiqueDtos = userList.stream().map(arg ->{
                    if (arg.getFirstConnection() != null){
                        HistoriqueDto historiqueDto = new HistoriqueDto();
                        historiqueDto.setLogin(arg.getLogin());
                        historiqueDto.setMachine(arg.getMachine());
                        historiqueDto.setNom(arg.getNom() + " " + arg.getPrenom());
                        historiqueDto.setActionService("TENTATIVE DE CONNEXION");
                        historiqueDto.setStatusConnection(arg.getStatus());
                        historiqueDto.setIpadress(arg.getIpadress());
                        historiqueDto.setLastConnection(arg.getLastConnection() != null ? dateTimeFormat.format(arg.getLastConnection()) : null);
                        historiqueDto.setFirstConnection(arg.getFirstConnection() != null ? dateTimeFormat.format(arg.getFirstConnection()) : null);
                        return historiqueDto;
                    }
                return null;
                }).collect(Collectors.toList());
                response.setFilePathDoc(Utilities.getFileUrlLink(fileName, paramsUtils));
                response.setStatus(functionalError.SUCCESS("", locale));
                response.setHasError(Boolean.FALSE);
                response.setItems(historiqueDtos);
                return response;
            }
        }
        response = new Response<>();
        try {
            response = getByCriteria(request, locale);
            if (response.getItems() != null && Utilities.isNotEmpty(response.getItems())) {
                    List<HistoriqueDto> historiqueDtos = response.getItems().stream().filter(args -> {
                        if (Constant.containsUrisSimswap().contains(args)) {
                            return true;
                        }
                        return false;
                    }).collect(Collectors.toList());
                    if (Utilities.isNotEmpty(historiqueDtos)) {
                        String fileName = extraireActionsSimswap(historiqueDtos);
                        response.setFilePathDoc(Utilities.getFileUrlLink(fileName, paramsUtils));
                        response.setHasError(Boolean.FALSE);
                        response.setItems(historiqueDtos);
                    } else {
                        String fileName = extraireActionsSimswap(response.getItems());
                        response.setFilePathDoc(Utilities.getFileUrlLink(fileName, paramsUtils));
                        response.setHasError(Boolean.FALSE);
                        response.setItems(response.getItems());
                    }
                response.setStatus(functionalError.SUCCESS("", locale));

            }
            log.info("----end exportHistorique -----");
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

    private String extraireLogsUniqueConnexion(List<HistoriqueDto> toParcours) throws Exception {
        SimpleDateFormat sdfFileName = new SimpleDateFormat(
                "dd_MMMMMMMMMMMMMMMM_yyyy_HH'H'_mm'min'_ss'Sec'_SSS");
        ClassPathResource templateClassPathResource = new ClassPathResource(
                "templates/reporting_connexions.xlsx");
        InputStream fileInputStream = templateClassPathResource.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0); // la 1ere feuille du classeur excel
        Cell cell = null;
        Row row = null;
        int rowIndex = 1;
        for (HistoriqueDto historique : toParcours) {
            log.info("--row--" + rowIndex);
            row = sheet.getRow(rowIndex);
            if (row == null) {
                row = sheet.createRow(rowIndex);
            }
            cell = getCell(row, 0);
            cell.setCellValue(rowIndex);
            cell = getCell(row, 1);
            cell.setCellValue(Utilities.notBlank(historique.getLogin()) ? historique.getLogin() : "");
            cell = getCell(row, 2);
            cell.setCellValue(historique.getFirstConnection());
            cell = getCell(row, 3);
            cell.setCellValue(historique.getLastConnection());
            cell = getCell(row, 4);
            cell.setCellValue(historique.getIpadress());
            cell = getCell(row, 5);
            cell.setCellValue(historique.getMachine());
            cell = getCell(row, 6);
            cell.setCellValue(historique.getStatusConnection());
            rowIndex++;
        }
        // Ajuster les colonnes
        for (int i = 0; i <= Utilities.getColumnIndex("I"); i++) {
            sheet.autoSizeColumn(i);
        }
        fileInputStream.close();
        String fileName = Utilities.saveFile("LIST_OF_REPORTING_CONNEXIONS" + sdfFileName.format(new Date()), "xlsx",
                workbook, paramsUtils);
        return fileName;
    }

    private String extraireAutresActions(List<HistoriqueDto> toParcours) throws Exception {
        SimpleDateFormat sdfFileName = new SimpleDateFormat(
                "dd_MMMMMMMMMMMMMMMM_yyyy_HH'H'_mm'min'_ss'Sec'_SSS");
        ClassPathResource templateClassPathResource = new ClassPathResource(
                "templates/LOGS_ACTION_UTILISATEURS.xlsx");
        InputStream fileInputStream = templateClassPathResource.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0); // la 1ere feuille du classeur excel
        Cell cell = null;
        Row row = null;
        int rowIndex = 1;
        for (HistoriqueDto historique : toParcours) {
            log.info("--row--" + rowIndex);
            row = sheet.getRow(rowIndex);
            if (row == null) {
                row = sheet.createRow(rowIndex);
            }
            cell = getCell(row, 0);
            cell.setCellValue(rowIndex);
            cell = getCell(row, 1);
            cell.setCellValue(Utilities.notBlank(historique.getLogin()) ? historique.getLogin() : "");
            cell = getCell(row, 2);
            cell.setCellValue(historique.getNom());
            cell = getCell(row, 3);
            cell.setCellValue(historique.getPrenom());
            cell = getCell(row, 4);
            cell.setCellValue(historique.getNumero());
            cell = getCell(row, 5);
            cell.setCellValue(historique.getActionSimswapLibelle() != null ? historique.getActionSimswapLibelle() : historique.getActionService());
            cell = getCell(row, 6);
            cell.setCellValue(historique.getCreatedAt());
            cell = getCell(row, 7);
            cell.setCellValue(Utilities.getCurrentDateTime());
            cell = getCell(row, 8);
            cell.setCellValue(historique.getStatusConnection());
            cell = getCell(row, 9);
            cell.setCellValue(historique.getIpadress());
            cell = getCell(row, 10);
            cell.setCellValue(historique.getMachine());
            cell = getCell(row, 11);
            cell.setCellValue(historique.getRaison());
            rowIndex++;
        }
        // Ajuster les colonnes
        for (int i = 0; i <= Utilities.getColumnIndex("I"); i++) {
            sheet.autoSizeColumn(i);
        }
        fileInputStream.close();
        String fileName = Utilities.saveFile("LIST_OF_LOGS_ACTION_UTILISATEURS" + sdfFileName.format(new Date()), "xlsx",
                workbook, paramsUtils);
        return fileName;
    }

    private String extraireActionsSimswap(List<HistoriqueDto> toParcours) throws Exception {
        SimpleDateFormat sdfFileName = new SimpleDateFormat(
                "dd_MMMMMMMMMMMMMMMM_yyyy_HH'H'_mm'min'_ss'Sec'_SSS");
        ClassPathResource templateClassPathResource = new ClassPathResource(
                "templates/LOGS_ACTION_UTILISATEURS_SIMSWAP.xlsx");
        InputStream fileInputStream = templateClassPathResource.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0); // la 1ere feuille du classeur excel
        Cell cell = null;
        Row row = null;
        int rowIndex = 1;
        for (HistoriqueDto historique : toParcours) {
            Status status = statusRepository.findOne(historique.getIdStatus(), Boolean.FALSE);
            log.info("--row--" + rowIndex);
            row = sheet.getRow(rowIndex);
            if (row == null) {
                row = sheet.createRow(rowIndex);
            }
            cell = getCell(row, 0);
            cell.setCellValue(rowIndex);
            cell = getCell(row, 1);
            cell.setCellValue(Utilities.notBlank(historique.getLogin()) ? historique.getLogin() : "");
            cell = getCell(row, 2);
            cell.setCellValue(historique.getNom());
            cell = getCell(row, 3);
            cell.setCellValue(historique.getPrenom());
            cell = getCell(row, 4);
            cell.setCellValue(historique.getNumero());
            cell = getCell(row, 5);
            cell.setCellValue(historique.getActionSimswapLibelle() != null ? historique.getActionSimswapLibelle() : historique.getActionService());
            cell = getCell(row, 6);
            cell.setCellValue(historique.getCreatedAt());
            cell = getCell(row, 7);
            cell.setCellValue(Utilities.getCurrentDateTime());
            cell = getCell(row, 8);
            cell.setCellValue(historique.getStatusConnection());
            cell = getCell(row, 9);
            cell.setCellValue(status != null ? status.getCode() : "AUCUN STATUS CONCERNÉ");
            cell = getCell(row, 10);
            cell.setCellValue(historique.getIpadress());
            cell = getCell(row, 11);
            cell.setCellValue(historique.getMachine());
            cell = getCell(row, 12);
            cell.setCellValue(historique.getRaison());
            cell = getCell(row, 13);
            cell.setCellValue(historique.getUsername() != null ? historique.getUsername() : "PAS CONCERNÉ");
            cell = getCell(row, 14);
            cell.setCellValue(historique.getName() != null ? historique.getName() : "PAS CONCERNÉ");
            cell = getCell(row, 15);
            cell.setCellValue(historique.getFirstname() != null ? historique.getFirstname() : "PAS CONCERNÉ");
            rowIndex++;
        }
        // Ajuster les colonnes
        for (int i = 0; i <= Utilities.getColumnIndex("I"); i++) {
            sheet.autoSizeColumn(i);
        }
        fileInputStream.close();
        String fileName = Utilities.saveFile("LIST_OF_LOGS_ACTION_UTILISATEURS_SIMSWAP" + sdfFileName.format(new Date()), "xlsx",
                workbook, paramsUtils);
        return fileName;
    }

    private String extraireFichierPourToutesLesConnexions(List<User> users) throws Exception {
        SimpleDateFormat sdfFileName = new SimpleDateFormat(
                "dd_MMMMMMMMMMMMMMMM_yyyy_HH'H'_mm'min'_ss'Sec'_SSS");
        ClassPathResource templateClassPathResource = new ClassPathResource(
                "templates/logs_connexions.xlsx");
        InputStream fileInputStream = templateClassPathResource.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0); // la 1ere feuille du classeur excel
        Cell cell = null;
        Row row = null;
        int rowIndex = 1;
        for (User historique : users) {
            log.info("--row--" + rowIndex);
            row = sheet.getRow(rowIndex);
            if (row == null) {
                row = sheet.createRow(rowIndex);
            }
            cell = getCell(row, 0);
            cell.setCellValue(rowIndex);
            cell = getCell(row, 1);
            cell.setCellValue(Utilities.notBlank(historique.getLogin()) ? historique.getLogin() : "");
            cell = getCell(row, 2);
            cell.setCellValue(historique.getCreatedAt());
            cell = getCell(row, 3);
            cell.setCellValue(historique.getIpadress());
            cell = getCell(row, 4);
            cell.setCellValue(historique.getMachine());
            cell = getCell(row, 5);
            cell.setCellValue(historique.getStatus());
            rowIndex++;
        }
        // Ajuster les colonnes
        for (int i = 0; i <= Utilities.getColumnIndex("I"); i++) {
            sheet.autoSizeColumn(i);
        }
        fileInputStream.close();
        String fileName = Utilities.saveFile("LIST_OF_LOGS_CONNEXIONS" + sdfFileName.format(new Date()), "xlsx",
                workbook, paramsUtils);
        return fileName;
    }

    public List<List<HistoriqueDto>> getSimilarLogin(List<HistoriqueDto> usersDtos) {
        List<List<HistoriqueDto>> list = new ArrayList<>();
        if (Utilities.isNotEmpty(usersDtos)) {
            for (int i = 0; i < usersDtos.size(); i++) {
                List<HistoriqueDto> dtos = new ArrayList<>();
                HistoriqueDto userDto = usersDtos.get(i);
                for (int j = 0; j < usersDtos.size(); j++) {
                    HistoriqueDto conditionUserDto = usersDtos.get(j);
                    if (conditionUserDto != null) {
                        if (Utilities.notBlank(conditionUserDto.getLogin()) && Utilities.notBlank(userDto.getLogin())) {
                            if (conditionUserDto.getLogin().equalsIgnoreCase(userDto.getLogin())) {
                                dtos.add(conditionUserDto);
                            }
                        }
                    }
                }
                list.add(dtos);
            }
        }
        return list;
    }

    public static List<List<HistoriqueDto>> supprimerDoublons(List<List<HistoriqueDto>> listeOriginale) {
        List<List<HistoriqueDto>> listeUnique = new ArrayList<>();
        if (Utilities.isNotEmpty(listeOriginale)) {
            for (List<HistoriqueDto> liste : listeOriginale) {
                if (!listeUnique.contains(liste)) {
                    listeUnique.add(liste);
                }
            }
        }

        return listeUnique;
    }

}
