
/*
 * Java controller for entity table action_utilisateur
 * Created on 2022-06-13 ( Time 13:07:16 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2017 Savoir Faire Linux. All Rights Reserved.
 */

package ci.smile.simswaporange.rest.api;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import ci.smile.simswaporange.utils.dto.ActionEnMasseDto;
import ci.smile.simswaporange.utils.dto.customize.DataKeyValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.smile.simswaporange.business.ActionUtilisateurBusiness;
import ci.smile.simswaporange.proxy.response.ApiResponse;
import ci.smile.simswaporange.proxy.response.LockUnLockFreezeDto;
import ci.smile.simswaporange.proxy.response.SensitiveNumberData;
import ci.smile.simswaporange.proxy.response.SensitiveNumberPackDto;
import ci.smile.simswaporange.proxy.response.TokenDto;
import ci.smile.simswaporange.proxy.service.SimSwapServiceProxyService;
import ci.smile.simswaporange.rest.fact.ControllerFactory;
import ci.smile.simswaporange.utils.ExceptionUtils;
import ci.smile.simswaporange.utils.FileStorageProperties;
import ci.smile.simswaporange.utils.FileStorageService;
import ci.smile.simswaporange.utils.FunctionalError;
import ci.smile.simswaporange.utils.StatusCode;
import ci.smile.simswaporange.utils.StatusMessage;
import ci.smile.simswaporange.utils.Validate;
import ci.smile.simswaporange.utils.contract.Request;
import ci.smile.simswaporange.utils.contract.Response;
import ci.smile.simswaporange.utils.dto.ActionUtilisateurDto;
import ci.smile.simswaporange.utils.dto.HistoriqueDto;
import ci.smile.simswaporange.utils.dto.customize._AppRequestDto;
import ci.smile.simswaporange.utils.enums.FunctionalityEnum;
import javassist.tools.web.BadHttpRequest;
import lombok.extern.java.Log;

/**
 * Controller for table "action_utilisateur"
 *
 * @author SFL Back-End developper
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/actionUtilisateur")
public class ActionUtilisateurController {
	@Autowired
	private ExceptionUtils exceptionUtils;
	@Autowired
	private HttpServletRequest requestBasic;
	@Autowired
	private FunctionalError functionalError;
	@Autowired
	private FileStorageService fileStorageService;
	@Autowired
	private FileStorageProperties fileStorageProperties;
	@Autowired
	ActionUtilisateurBusiness actionUtilistaeurBusiness;
	@Autowired
	private ActionUtilisateurBusiness actionUtilisateurBusiness;
	@Autowired
	private SimSwapServiceProxyService simSwapServiceProxyService;
	private Logger slf4jLogger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ControllerFactory<ActionUtilisateurDto> controllerFactory;


	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public Response<ActionUtilisateurDto> create(@RequestBody Request<ActionUtilisateurDto> request) {
		log.info("start method /actionUtilisateur/create");
		Response<ActionUtilisateurDto> response = controllerFactory.create(actionUtilisateurBusiness, request,
				FunctionalityEnum.CREATE_ACTION_UTILISATEUR);
		log.info("end method /actionUtilisateur/create");
		return response;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public Response<ActionUtilisateurDto> update(@RequestBody Request<ActionUtilisateurDto> request) {
		log.info("start method /actionUtilisateur/update");
		Response<ActionUtilisateurDto> response = controllerFactory.update(actionUtilisateurBusiness, request,
				FunctionalityEnum.UPDATE_ACTION_UTILISATEUR);
		log.info("end method /actionUtilisateur/update");
		return response;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public Response<ActionUtilisateurDto> delete(@RequestBody Request<ActionUtilisateurDto> request) {
		log.info("start method /actionUtilisateur/delete");
		Response<ActionUtilisateurDto> response = controllerFactory.delete(actionUtilisateurBusiness, request,
				FunctionalityEnum.DELETE_ACTION_UTILISATEUR);
		log.info("end method /actionUtilisateur/delete");
		return response;
	}

	@RequestMapping(value = "/getByCriteria", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Response<ActionUtilisateurDto> getByCriteria(@RequestBody Request<ActionUtilisateurDto> request) {
		log.info("start method /actionUtilisateur/getByCriteria");
		Response<ActionUtilisateurDto> response = controllerFactory.getByCriteria(actionUtilisateurBusiness, request,
				FunctionalityEnum.VIEW_ACTION_UTILISATEUR);
		log.info("end method /actionUtilisateur/getByCriteria");
		return response;
	}

	/*
	 * @RequestMapping(value = "/getNumber/{number}", method = RequestMethod.GET)
	 * public Response<String> getNumber(@PathVariable("number") String number)
	 * throws IOException { String languageID = (String)
	 * requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER"); Locale locale = new
	 * Locale(languageID, ""); log.info("start method /Number/From/API");
	 * Response<String> response = actionUtilisateurBusiness.GetNumber(number,
	 * locale); log.info("end method /Number/From/API"); return response; } also
	 */

	@RequestMapping(value = "/getNumbers", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public ApiResponse<SensitiveNumberPackDto> getContracts(@RequestBody _AppRequestDto _AppRequestDto)
			throws JsonMappingException, JsonProcessingException, BadHttpRequest {
		log.info("start getSensitiveNumbers");
		ApiResponse<SensitiveNumberPackDto> apiResponse = simSwapServiceProxyService.getContracts(_AppRequestDto);
		return apiResponse;
	}

	@RequestMapping(value = "/lock", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public ApiResponse<LockUnLockFreezeDto> lock(@RequestBody _AppRequestDto _AppRequestDto)
			throws JsonMappingException, JsonProcessingException, BadHttpRequest {
		log.info("start getSensitiveNumbers");
		ApiResponse<LockUnLockFreezeDto> apiResponse = simSwapServiceProxyService.lockPhoneNumber(_AppRequestDto);
		return apiResponse;
	}

	@RequestMapping(value = "/sendsms", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public void sms(@RequestBody _AppRequestDto _AppRequestDto) throws BadHttpRequest {
		log.info("start getSensitiveNumbers");
		simSwapServiceProxyService.sendSMS(_AppRequestDto);
	}

	@RequestMapping(value = "/token", method = RequestMethod.GET, produces = { "application/json" })
	public TokenDto token() {
		ObjectMapper mapper = new ObjectMapper();
		String json = "{\"access_token\":\"dc351a7a-855e-3cbc-84fa-d34c1a354d07\",\"scope\":\"am_application_scope default\",\"token_type\":\"Bearer\",\"expires_in\":1615}";

		try {
			TokenDto tokenDto = mapper.readValue(json, TokenDto.class);
			System.out.println("Deserialization successful: " + tokenDto.toString());
		} catch (Exception e) {
			System.out.println("Deserialization failed. Error: " + e.getMessage());
		}
		log.info("start getSensitiveNumbers");
		TokenDto apiResponse = simSwapServiceProxyService.myToken();
		return apiResponse;
	}

	@RequestMapping(value = "/findByMsisdn", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public LockUnLockFreezeDto searchContract(@RequestBody _AppRequestDto _AppRequestDto)
			throws JsonMappingException, JsonProcessingException, BadHttpRequest {
		log.info("start findByMsisdn");
		LockUnLockFreezeDto apiResponse = simSwapServiceProxyService.findByMsisdn(UUID.randomUUID().toString(), _AppRequestDto.getNumbers(), _AppRequestDto.getUser(), Boolean.FALSE);
		log.info("end findByMsisdn");
		return apiResponse;
	}

	@RequestMapping(value = "/getNumeroByCategory", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Response<Map<String, Object>> getNumeroByCategory(@RequestBody Request<ActionUtilisateurDto> request) {
		log.info("Sensitive Number");
		Response<Map<String, Object>> response = new Response<>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");

		String requestID = (String) requestBasic.getAttribute("requestID");
		Locale locale = new Locale(languageID, "");
		try {
//	response = Validate.validateList(request, response, functionalError, locale);
			if (!response.isHasError()) {
//				request.setIdentifiantRequest(requestID);
				response = actionUtilistaeurBusiness.getNumeroByCategory(request, locale);
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
		slf4jLogger.info("end method /UserDto/custom");
		return response;
	}

	@RequestMapping(value = "/cronRefreshAllNumberForActionenMasse", method = RequestMethod.POST, consumes = {"application/json"}, produces = {
			"application/json"})
	public Response<ActionEnMasseDto> cronRefreshAllNumberForActionenMasse(@RequestBody Request<ActionUtilisateurDto> actionUtilisateurDto) {
		log.info("ActionEnMasseDto method /$/cronRefreshAllNumberForActionenMasse");
		Response<ActionEnMasseDto> response = new Response<ActionEnMasseDto>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
			actionUtilistaeurBusiness.cronRefreshAllNumberForActionenMasse(actionUtilisateurDto.getUser());

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

	@RequestMapping(value = "/blocageNumero", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Response<ActionUtilisateurDto> blocageNumero(@RequestBody Request<ActionUtilisateurDto> request) {
		log.info("UserDto method /$/custom");
		Response<ActionUtilisateurDto> response = new Response<ActionUtilisateurDto>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
			response = Validate.validateList(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = actionUtilistaeurBusiness.actionOnNumber(request, locale, Boolean.TRUE);
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
		slf4jLogger.info("end method /UserDto/custom");
		return response;
	}

	@RequestMapping(value = "/fromExcelFile", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Response<ActionUtilisateurDto> FromExcelFile(@RequestBody Request<ActionUtilisateurDto> request) {
		log.info("UserDto method /$/custom");
		Response<ActionUtilisateurDto> response = new Response<ActionUtilisateurDto>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
			response = Validate.validateObject(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = actionUtilistaeurBusiness.getNumberFromExcelFile(request, locale);
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
		slf4jLogger.info("end method /UserDto/custom");
		return response;
	}

	@RequestMapping(value = "/getByCriteriaLite", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Response<ActionUtilisateurDto> getByCriteriaLite(@RequestBody Request<ActionUtilisateurDto> request) {
		log.info("UserDto method /$/custom");
		Response<ActionUtilisateurDto> response = new Response<ActionUtilisateurDto>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
			response = Validate.validateObject(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = actionUtilistaeurBusiness.getByCriteriaLite(request, locale);
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
		slf4jLogger.info("end method getNumbersList/UserDto/custom");
		return response;
	}

	@RequestMapping(value = "/getNumbersList", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Response<ActionUtilisateurDto> getNumbersList(@RequestBody Request<ActionUtilisateurDto> request) {
		log.info("UserDto method /$/custom");
		Response<ActionUtilisateurDto> response = new Response<ActionUtilisateurDto>();
		String languageID = (String) requestBasic.getAttribute( 	"CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
			response = Validate.validateObject(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = actionUtilistaeurBusiness.getNumbersList(request, locale);
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
		slf4jLogger.info("end method /UserDto/custom");
		return response;
	}

	@RequestMapping(value = "/isConnexion", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public Response<HistoriqueDto> isConnexion(@RequestBody Request<HistoriqueDto> request) {
		log.info("UserDto method /$/custom");
		Response<HistoriqueDto> response = new Response<HistoriqueDto>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
			response = Validate.validateObject(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = actionUtilistaeurBusiness.getConnexionUser(request, locale);
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
		slf4jLogger.info("end method /UserDto/custom");
		return response;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public Response<List<DataKeyValues>> dashboards(@RequestBody Request<ActionUtilisateurDto> request) {
		log.info("Begin /ActionUtilisateur/dashboard");
		Response<List<DataKeyValues>> response = new Response<>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
//			response = Validate.validateObject(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = actionUtilistaeurBusiness.OptimizeDashboard(request, locale);
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
		slf4jLogger.info("end method /ActionUtilisateur/dashboard");
		return response;
	}

//    @RequestMapping(value = "/getDashboardMiseEnMachine", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
//    public Response<List<Map<String, String>>> getDashboardMiseEnMachine(@RequestBody Request<ActionUtilisateurDto> request) {
//        log.info("Begin /ActionUtilisateur/dashboard");
//        Response<List<Map<String, String>>> response = new Response<>();
//        String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
//        Locale locale = new Locale(languageID, "");
//        try {
////			response = Validate.validateObject(request, response, functionalError, locale);
//            if (!response.isHasError()) {
//            	String start = new String();
//            	String end = new String();
//                response = actionUtilistaeurBusiness.getDashboardMiseEnMachine(request, locale, start, end);
//            } else {
//                slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
//                return response;
//            }
//            if (!response.isHasError()) {
//                slf4jLogger.info("end method custom");
//                slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);
//            } else {
//                slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
//            }
//        } catch (CannotCreateTransactionException e) {
//            exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
//        } catch (TransactionSystemException e) {
//            exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
//        } catch (RuntimeException e) {
//            exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
//        } catch (Exception e) {
//            exceptionUtils.EXCEPTION(response, locale, e);
//        }
//        slf4jLogger.info("end method /ActionUtilisateur/dashboard");
//        return response;
//    }

	@RequestMapping(value = "/getDashboardMiseEnMachineStories", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Response<List<Map<String, String>>> getDashboardMiseEnMachineStories(
			@RequestBody Request<ActionUtilisateurDto> request) {
		log.info("Begin /ActionUtilisateur/dashboard");
		Response<List<Map<String, String>>> response = new Response<>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
//			response = Validate.validateObject(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = actionUtilistaeurBusiness.getDashboardMiseEnMachineStories(request, locale);
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
		slf4jLogger.info("end method /ActionUtilisateur/dashboard");
		return response;
	}

	@RequestMapping(value = "/dashboardLite", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Response<List<Map<String, String>>> dashboardsLite(@RequestBody Request<ActionUtilisateurDto> request) {
		log.info("start /dashboardLite ");
		Response<List<Map<String, String>>> response = new Response<>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
//	response = Validate.validateObject(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = actionUtilistaeurBusiness.getDashboardLite(request, locale);
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
		slf4jLogger.info("end method /UserDto/custom");
		return response;
	}

//    @RequestMapping(value = "/uploadFileExcel", method = RequestMethod.POST, consumes = {"multipart/form-data"})
//    public Response<ActionUtilisateurDto> uploadFileExcel(@RequestParam("file") MultipartFile file, @RequestParam("user") Integer user) throws IOException {
//        Response<ActionUtilisateurDto> response = new Response<ActionUtilisateurDto>();
//        slf4jLogger.info("end method /disruptiveDistributionV2/numero");
//        Locale locale = new Locale("fr");
//        // repertoire de sauvegarde du fichier uploadé
//        try {
//            String fileName = fileStorageService.storeFile(file);
//            Path pathDest = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
//            String pathDestNormalize = pathDest.toString();
//            pathDestNormalize += "/" + fileName;
//            String file_name_full = pathDestNormalize;
//            response = actionUtilisateurBusiness.uploadFileExcel(file_name_full, user);
//            System.out.println(" ---> response = " + response);
//            if (!response.isHasError()) {
//
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

	@RequestMapping(value = "/uploadFileCsv", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public Response<ActionUtilisateurDto> uploadFileCsv(@RequestParam("file") MultipartFile file,
			@RequestParam("user") Integer user) throws IOException {
		Response<ActionUtilisateurDto> response = new Response<ActionUtilisateurDto>();
		slf4jLogger.info("end method /disruptiveDistributionV2/numero");
		Locale locale = new Locale("fr");
		// repertoire de sauvegarde du fichier uploadé
		try {
			String fileName = fileStorageService.storeFile(file);
			Path pathDest = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
			String pathDestNormalize = pathDest.toString();
			pathDestNormalize += "/" + fileName;
			String file_name_full = pathDestNormalize;
			response = actionUtilisateurBusiness.uploadFileCsv(file_name_full, user);
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

	@RequestMapping(value = "/exportActionUtilisateur", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Response<ActionUtilisateurDto> exportActionUtilisateur(@RequestBody Request<ActionUtilisateurDto> request) {
		log.info("begin file export");
		Response<ActionUtilisateurDto> response = new Response<ActionUtilisateurDto>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
//response = Validate.validateObject(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = actionUtilisateurBusiness.exportActionUtilisateur(request, locale);
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
		slf4jLogger.info("end file export");
		return response;
	}


//    @RequestMapping(value = "/getNumbers", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
//    public Response<ActionUtilisateurDto> getSensitiveNumbers(@RequestBody Request<ActionUtilisateurDto> request) {
//        log.info("start Number");
//        Response<ActionUtilisateurDto> response = new Response<>();
//        String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
//
//        String requestID = (String) requestBasic.getAttribute("requestID");
//        Locale locale = new Locale(languageID, "");
//        try {
////			response = Validate.validateList(request, response, functionalError, locale);
//            if (!response.isHasError()) {
//                request.setIdentifiantRequest(requestID);
//                response = actionUtilistaeurBusiness.GetNumber(request, locale);
//            } else {
//                slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
//                return response;
//            }
//            if (!response.isHasError()) {
//                slf4jLogger.info("end method custom");
//                slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);
//            } else {
//                slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage());
//            }
//        } catch (CannotCreateTransactionException e) {
//            exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
//        } catch (TransactionSystemException e) {
//            exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
//        } catch (RuntimeException e) {
//            exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
//        } catch (Exception e) {
//            exceptionUtils.EXCEPTION(response, locale, e);
//        }
//        slf4jLogger.info("end method /UserDto/custom");
//        return response;
//    }

}
