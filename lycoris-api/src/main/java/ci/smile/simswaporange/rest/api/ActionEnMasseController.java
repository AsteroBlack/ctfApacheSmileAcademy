
/*
 * Java controller for entity table action_en_masse
 * Created on 2022-10-04 ( Time 11:23:37 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2017 Savoir Faire Linux. All Rights Reserved.
 */

package ci.smile.simswaporange.rest.api;

import ci.smile.simswaporange.utils.dto.customize._ActionEnMasseDto;
import lombok.extern.java.Log;
import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionSystemException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import ci.smile.simswaporange.utils.*;
import ci.smile.simswaporange.utils.dto.*;
import ci.smile.simswaporange.utils.contract.*;
import ci.smile.simswaporange.utils.contract.Request;
import ci.smile.simswaporange.utils.contract.Response;
import ci.smile.simswaporange.utils.enums.FunctionalityEnum;
import ci.smile.simswaporange.business.*;
import ci.smile.simswaporange.rest.fact.ControllerFactory;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

/**
 * Controller for table "action_en_masse"
 *
 * @author SFL Back-End developper
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/actionEnMasse")
public class ActionEnMasseController {

	@Autowired
	private FileStorageService fileStorageService;
	@Autowired
	private FileStorageProperties fileStorageProperties;
	@Autowired
	private HttpServletRequest requestBasic;
	@Autowired
	private FunctionalError functionalError;
	@Autowired
	private ExceptionUtils exceptionUtils;

	@Autowired
	private  JobLauncher jobLauncher;
	@Autowired
	@Qualifier("job") // Spécifiez le nom du bean Job que vous voulez utiliser
	private  Job job;

	@Autowired
	@Qualifier("jobLockUnlockNumber") // Spécifiez le nom du bean Job que vous voulez utiliser
	private  Job jobLockUnlockNumber;

	@Autowired
	@Qualifier("jobRefreshNumber") // Spécifiez le nom du bean Job que vous voulez utiliser
	private  Job jobRefreshNumber;

	private Logger slf4jLogger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ControllerFactory<ActionEnMasseDto> controllerFactory;
	@Autowired
	private ActionEnMasseBusiness actionEnMasseBusiness;

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public Response<ActionEnMasseDto> create(@RequestBody Request<ActionEnMasseDto> request) {
		log.info("start method /actionEnMasse/create");
		Response<ActionEnMasseDto> response = controllerFactory.create(actionEnMasseBusiness, request,
				FunctionalityEnum.CREATE_ACTION_EN_MASSE);
		log.info("end method /actionEnMasse/create");
		return response;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public Response<ActionEnMasseDto> update(@RequestBody Request<ActionEnMasseDto> request) {
		log.info("start method /actionEnMasse/update");
		Response<ActionEnMasseDto> response = controllerFactory.update(actionEnMasseBusiness, request,
				FunctionalityEnum.UPDATE_ACTION_EN_MASSE);
		log.info("end method /actionEnMasse/update");
		return response;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public Response<ActionEnMasseDto> delete(@RequestBody Request<ActionEnMasseDto> request) {
		log.info("start method /actionEnMasse/delete");
		Response<ActionEnMasseDto> response = controllerFactory.delete(actionEnMasseBusiness, request,
				FunctionalityEnum.DELETE_ACTION_EN_MASSE);
		log.info("end method /actionEnMasse/delete");
		return response;
	}

	@RequestMapping(value = "/adminActionUser", method = RequestMethod.POST)
	public void deleteActionUtilisateurAdminCode(){
		log.info("debut");
		actionEnMasseBusiness.deletedActionUser();
		log.info("fin");

	}

	@RequestMapping(value = "/getByCriteria", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Response<ActionEnMasseDto> getByCriteria(@RequestBody Request<ActionEnMasseDto> request) {
		log.info("start method /actionEnMasse/getByCriteria");
		Response<ActionEnMasseDto> response = controllerFactory.getByCriteria(actionEnMasseBusiness, request,
				FunctionalityEnum.VIEW_ACTION_EN_MASSE);
		log.info("end method /actionEnMasse/getByCriteria");
		return response;
	}

	@RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public Response<ActionEnMasseDto> custom(@RequestBody Request<ActionEnMasseDto> request) {
		log.info("ActionEnMasseDto method /$/custom");
		Response<ActionEnMasseDto> response = new Response<ActionEnMasseDto>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
			response = Validate.validateList(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = actionEnMasseBusiness.custom(request, locale);
			} else {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage());
				return response;
			}
			if (!response.isHasError()) {
				slf4jLogger.info("end method custom");
				slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);
			} else {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage());
			}
		} catch (CannotCreateTransactionException e) {
			exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
		} catch (TransactionSystemException e) {
			exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		}
		slf4jLogger.info("end method /ActionEnMasseDto/custom");
		return response;
	}

//    @RequestMapping(value = "/uploadFileExcel", method = RequestMethod.POST, consumes = {"multipart/form-data"})
//    public Response<ActionUtilisateurDto> uploadFileExcel(@RequestParam("identifiant") String identifiant, @RequestParam("user") Integer user) throws IOException {
//        Response<ActionUtilisateurDto> response = new Response<ActionUtilisateurDto>();
//        slf4jLogger.info("end method /disruptiveDistributionV2/numero");
//        Locale locale = new Locale("fr");
//        // repertoire de sauvegarde du fichier uploadé
//        try {
////            String fileName = fileStorageService.storeFile(file);
////            Path pathDest = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
////            String pathDestNormalize = pathDest.toString();
////            pathDestNormalize += "/" + fileName;
////            String file_name_full = pathDestNormalize;
//            response = actionEnMasseBusiness.uploadFileExcel(identifiant, user);
//            System.out.println(" ---> response = " + response);
//            if (!response.isHasError()) {
//                slf4jLogger.info("end method numero");
//                slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);
//                response.getItems();
//                response.getStatus().getMessage();
//                return response;
//            } else {
//                response.setHasError(true);
//                slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
//                return response;
//            }
//
//        } catch (CannotCreateTransactionException e) {
//            exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
//        } catch (TransactionSystemException e) {
//            exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
//        } catch (RuntimeException e) {
//            exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
//        } catch (Exception e) {
//            exceptionUtils.EXCEPTION(response, locale, e);
//        }
//        slf4jLogger.info("end method /disruptiveDistributionV2/uploadDeploiementReseau");
//        return response;
//    }

	@RequestMapping(value = "/uploadOneFile", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public Response<ActionEnMasseDto> uploadOneFile(@RequestParam("file") MultipartFile file,
			@RequestParam("identifiant") String identifiant, @RequestParam("user") Integer user, @RequestParam("idStatus") Integer idStatus) throws IOException {
		Response<ActionEnMasseDto> response = new Response<ActionEnMasseDto>();
		slf4jLogger.info("end method /disruptiveDistributionV2/numero");
		Locale locale = new Locale("fr");
		// repertoire de sauvegarde du fichier uploadé
		try {
			String fileName = fileStorageService.storeFile(file);
			Path pathDest = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
			String pathDestNormalize = pathDest.toString();
			pathDestNormalize += "/" + fileName;
			String file_name_full = pathDestNormalize;
			response = actionEnMasseBusiness.uploadOneFile(file_name_full, identifiant, user, idStatus);
			System.out.println(" ---> response = " + response);
			if (!response.isHasError()) {
				slf4jLogger.info("end method numero");
				slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);
				response.getItems();
				response.getStatus().getMessage();
				return response;
			} else {
				response.setHasError(true);
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage());
				return response;
			}

		} catch (CannotCreateTransactionException e) {
			exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
		} catch (TransactionSystemException e) {
			exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		}
		slf4jLogger.info("end method /disruptiveDistributionV2/uploadDeploiementReseau");
		return response;
	}

	@RequestMapping(value = "/timeStamp", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public Response<ActionEnMasseDto> timeStamp(@RequestBody Request<ActionEnMasseDto> request) {
		log.info("ActionEnMasseDto method /$/custom");
		Response<ActionEnMasseDto> response = new Response<ActionEnMasseDto>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
			response = Validate.validateObject(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = actionEnMasseBusiness.timeStamp(request, locale);
			} else {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage());
				return response;
			}
			if (!response.isHasError()) {
				slf4jLogger.info("end method custom");
				slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);
			} else {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage());
			}
		} catch (CannotCreateTransactionException e) {
			exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
		} catch (TransactionSystemException e) {
			exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		}
		slf4jLogger.info("end method /ActionEnMasseDto/custom");
		return response;
	}

	@RequestMapping(value = "/cronLockAllUnclockNumberService", method = RequestMethod.POST, consumes = {"application/json"}, produces = {
			"application/json"})
	public Response<ActionEnMasseDto> cronLockAllUnclockNumberService(@RequestBody ActionEnMasseDto actionEnMasseDto) {
		log.info("ActionEnMasseDto method /$/cronLockAllUnclockNumberService");
		Response<ActionEnMasseDto> response = new Response<ActionEnMasseDto>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
			actionEnMasseBusiness.cronLockAllUnclockNumberService(actionEnMasseDto.getUser());

		} catch (CannotCreateTransactionException e) {
			exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
		} catch (TransactionSystemException e) {
			exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		}
		slf4jLogger.info("end method /ActionEnMasseDto/cronLockAllUnclockNumberService");
		return response;
	}


	@RequestMapping(value = "/executeOnMasse", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Response<ActionUtilisateurDto> executeOnMasse(@RequestBody Request<ActionUtilisateurDto> request) {
		log.info("ActionEnMasseDto method /$/custom");
		Response<ActionUtilisateurDto> response = new Response<ActionUtilisateurDto>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
			response = Validate.validateObject(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = actionEnMasseBusiness.executeOnMasse(request, locale);
			} else {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage());
				return response;
			}
			if (!response.isHasError()) {
				slf4jLogger.info("end method custom");
				slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);
			} else {
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
						response.getStatus().getMessage());
			}
		} catch (CannotCreateTransactionException e) {
			exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
		} catch (TransactionSystemException e) {
			exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
		} catch (RuntimeException e) {
			exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
		} catch (Exception e) {
			exceptionUtils.EXCEPTION(response, locale, e);
		}
		slf4jLogger.info("end method /ActionEnMasseDto/custom");
		return response;
	}

	@RequestMapping(value = "/savedFile", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public List<ActionUtilisateurDto> readFile(@RequestBody ActionUtilisateurDto actionUtilisateurDto) {
		log.info("ActionEnMasseDto method /$/custom");
		List<ActionUtilisateurDto> actionUtilisateurDtos = null;
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		actionUtilisateurDtos = actionEnMasseBusiness.exposed();
		slf4jLogger.info("end method /ActionEnMasseDto/custom");
		return actionUtilisateurDtos;
	}

	@RequestMapping(value = "/readFileFromHomeRepository", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public List<ActionUtilisateurDto> readFileFromHomeRepository(@RequestBody ActionUtilisateurDto actionUtilisateurDto) {
		log.info("ActionEnMasseDto method /$/custom");
		List<ActionUtilisateurDto> actionUtilisateurDtos = null;
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		actionUtilisateurDtos = actionEnMasseBusiness.readFileFromHomeRepository(actionUtilisateurDto.getFilePath());
		slf4jLogger.info("end method /ActionEnMasseDto/custom");
		return actionUtilisateurDtos;
	}

	@RequestMapping(value = "/stockFile", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public void stockFile(@RequestBody _ActionEnMasseDto actionUtilisateurDto) {
		log.info("ActionEnMasseDto method /$/custom");
		actionEnMasseBusiness.stockFile(actionUtilisateurDto);
		slf4jLogger.info("end method /ectratct/custom");
	}

	@RequestMapping(value = "/stockFileCsvAndXlsx", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public void stockFiles(@RequestBody ActionUtilisateurDto actionUtilisateurDto) {
		log.info("ActionEnMasseDto begin csv and exel /$/custom");
		actionEnMasseBusiness.readAndRefreshFileCsvAndXlsxFromTomcat(actionUtilisateurDto.getFilePath());
		slf4jLogger.info("end method /ectratct/custom m");
	}

	@RequestMapping(value = "/importCsvToDBJob", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public void importCsvToDBJob(@RequestBody _ActionEnMasseDto actionUtilisateurDto) {
		log.info("debut importCsvToDBJob");

		try {
			JobParameters jobParameters = new JobParametersBuilder()
					.addString("identifiant", UUID.randomUUID().toString())
					.addString("date", Utilities.getCurrentDateTime())
					.addLong("JobId",System.currentTimeMillis())
					.addLong("userId",actionUtilisateurDto.getUser() != null ? actionUtilisateurDto.getUser().longValue() : 0)
					.addLong("time",System.currentTimeMillis()).toJobParameters();
			JobExecution execution = jobLauncher.run(job, jobParameters);
			System.out.println("STATUS :: "+execution.getStatus());
			log.info("fin importCsvToDBJob");

		} catch (JobExecutionAlreadyRunningException
				 | JobRestartException
				 | JobInstanceAlreadyCompleteException
				 | JobParametersInvalidException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/setJobLockUnlockNumber", method = RequestMethod.POST, consumes = {	"application/json"	}, produces = {
			"application/json" })
	public void setJobLockUnlockNumber(@RequestBody _ActionEnMasseDto actionUtilisateurDto) {
		try {
			log.info("debut setJobLockUnlockNumber");
			JobParameters jobParameters = new JobParametersBuilder()
					.addString("identifiant", UUID.randomUUID().toString())
					.addString("date", Utilities.getCurrentDateTime())
					.addLong("userId",actionUtilisateurDto.getUser() != null ? actionUtilisateurDto.getUser().longValue() : 0)
					.addLong("JobId",System.currentTimeMillis())
					.addLong("time",System.currentTimeMillis()).toJobParameters();
			JobExecution execution = jobLauncher.run(jobLockUnlockNumber, jobParameters);
			System.out.println("STATUS :: "+execution.getStatus());
			log.info("fin setJobLockUnlockNumber");

		} catch (JobExecutionAlreadyRunningException
				 | JobRestartException
				 | JobInstanceAlreadyCompleteException
				 | JobParametersInvalidException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/setJobRefreshNumber", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public void setJobRefreshNumber(@RequestBody _ActionEnMasseDto actionUtilisateurDto) {
		log.info("debut setJobRefreshNumber");
		try {
			JobParameters jobParameters = new JobParametersBuilder()
					.addString("identifiant", UUID.randomUUID().toString())
					.addString("date", Utilities.getCurrentDateTime())
					.addLong("JobId",System.currentTimeMillis())
					.addLong("userId",actionUtilisateurDto.getUser() != null ? actionUtilisateurDto.getUser().longValue() : 0)
					.addLong("time",System.currentTimeMillis()).toJobParameters();
			JobExecution execution = jobLauncher.run(jobRefreshNumber, jobParameters);
			System.out.println("STATUS :: "+execution.getStatus());

			log.info("fin setJobRefreshNumber");

		} catch (JobExecutionAlreadyRunningException
				 | JobRestartException
				 | JobInstanceAlreadyCompleteException
				 | JobParametersInvalidException e) {
			e.printStackTrace();
		}
	}




}
