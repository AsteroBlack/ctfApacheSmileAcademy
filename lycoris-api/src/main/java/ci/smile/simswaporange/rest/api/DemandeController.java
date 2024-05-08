
/*
 * Java controller for entity table demande 
 * Created on 2022-10-04 ( Time 11:23:37 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2017 Savoir Faire Linux. All Rights Reserved.
 */

package ci.smile.simswaporange.rest.api;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionSystemException;
import java.util.Locale;

import ci.smile.simswaporange.utils.*;
import ci.smile.simswaporange.utils.dto.*;
import ci.smile.simswaporange.utils.contract.*;
import ci.smile.simswaporange.utils.contract.Request;
import ci.smile.simswaporange.utils.contract.Response;
import ci.smile.simswaporange.utils.enums.FunctionalityEnum;
import ci.smile.simswaporange.business.*;
import ci.smile.simswaporange.rest.fact.ControllerFactory;

/**
 * Controller for table "demande"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/demande")
public class DemandeController {

	@Autowired
	private HttpServletRequest requestBasic;
	@Autowired
	private FunctionalError functionalError;
	@Autowired
	private ExceptionUtils exceptionUtils;

	private Logger slf4jLogger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ControllerFactory<DemandeDto> controllerFactory;
	@Autowired
	private DemandeBusiness demandeBusiness;

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public Response<DemandeDto> create(@RequestBody Request<DemandeDto> request) {
		log.info("start method /demande/create");
		Response<DemandeDto> response = controllerFactory.create(demandeBusiness, request,
				FunctionalityEnum.CREATE_DEMANDE);
		log.info("end method /demande/create");
		return response;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public Response<DemandeDto> update(@RequestBody Request<DemandeDto> request) {
		log.info("start method /demande/update");
		Response<DemandeDto> response = controllerFactory.update(demandeBusiness, request,
				FunctionalityEnum.UPDATE_DEMANDE);
		log.info("end method /demande/update");
		return response;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public Response<DemandeDto> delete(@RequestBody Request<DemandeDto> request) {
		log.info("start method /demande/delete");
		Response<DemandeDto> response = controllerFactory.delete(demandeBusiness, request,
				FunctionalityEnum.DELETE_DEMANDE);
		log.info("end method /demande/delete");
		return response;
	}

	@RequestMapping(value = "/getByCriteria", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Response<DemandeDto> getByCriteria(@RequestBody Request<DemandeDto> request) {
		log.info("start method /demande/getByCriteria");
		Response<DemandeDto> response = controllerFactory.getByCriteria(demandeBusiness, request,
				FunctionalityEnum.VIEW_DEMANDE);
		log.info("end method /demande/getByCriteria");
		return response;
	}

	@RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public Response<DemandeDto> custom(@RequestBody Request<DemandeDto> request) {
		log.info("DemandeDto method /$/custom");
		Response<DemandeDto> response = new Response<DemandeDto>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
			response = Validate.validateList(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = demandeBusiness.custom(request, locale);
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
		slf4jLogger.info("end method /DemandeDto/custom");
		return response;
	}

	@RequestMapping(value = "/validerRefuser", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Response<DemandeDto> validerRefuser(@RequestBody Request<DemandeDto> request) {
		log.info("Begin Demande / validerRefuser");
		Response<DemandeDto> response = new Response<DemandeDto>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
			response = Validate.validateList(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = demandeBusiness.valideRefuser(request, locale);
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
		slf4jLogger.info("End Demande / validerRefuser");
		return response;
	}

	@RequestMapping(value = "/actionOnNumber", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Response<DemandeDto> actionOnNumber(@RequestBody Request<DemandeDto> request) {
		log.info("Begin Demande / validerRefuser");
		Response<DemandeDto> response = new Response<DemandeDto>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
			response = Validate.validateList(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = demandeBusiness.actionOnNumber(request, locale);
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
		slf4jLogger.info("End Demande / validerRefuser");
		return response;
	}

	@RequestMapping(value = "/alerteSms", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public void onBscsNumber(@RequestBody Request<DemandeDto> request) {
		log.info("Begin Demande / validerRefuser");
		Response<String> response = new Response<String>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
//			response = Validate.validateList(request, response, functionalError, locale);
			demandeBusiness.alerteSMSService();

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
		slf4jLogger.info("End Demande / validerRefuser");
	}
}
