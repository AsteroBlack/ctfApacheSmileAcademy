
/*
 * Java controller for entity table numero_stories 
 * Created on 2022-07-19 ( Time 14:54:09 )
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

@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/numeroStories")
public class NumeroStoriesController {
	@Autowired
	private HttpServletRequest requestBasic;
	@Autowired
	private FunctionalError functionalError;
	@Autowired
	private ExceptionUtils exceptionUtils;
	private Logger slf4jLogger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ControllerFactory<NumeroStoriesDto> controllerFactory;
	@Autowired
	private NumeroStoriesBusiness numeroStoriesBusiness;

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public Response<NumeroStoriesDto> create(@RequestBody Request<NumeroStoriesDto> request) {
		log.info("start method /numeroStories/create");
		Response<NumeroStoriesDto> response = controllerFactory.create(numeroStoriesBusiness, request,
				FunctionalityEnum.CREATE_NUMERO_STORIES);
		log.info("end method /numeroStories/create");
		return response;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public Response<NumeroStoriesDto> update(@RequestBody Request<NumeroStoriesDto> request) {
		log.info("start method /numeroStories/update");
		Response<NumeroStoriesDto> response = controllerFactory.update(numeroStoriesBusiness, request,
				FunctionalityEnum.UPDATE_NUMERO_STORIES);
		log.info("end method /numeroStories/update");
		return response;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public Response<NumeroStoriesDto> delete(@RequestBody Request<NumeroStoriesDto> request) {
		log.info("start method /numeroStories/delete");
		Response<NumeroStoriesDto> response = controllerFactory.delete(numeroStoriesBusiness, request,
				FunctionalityEnum.DELETE_NUMERO_STORIES);
		log.info("end method /numeroStories/delete");
		return response;
	}

	@RequestMapping(value = "/getByCriteria", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Response<NumeroStoriesDto> getByCriteria(@RequestBody Request<NumeroStoriesDto> request) {
		log.info("start method /numeroStories/getByCriteria");
		Response<NumeroStoriesDto> response = controllerFactory.getByCriteria(numeroStoriesBusiness, request,
				FunctionalityEnum.VIEW_NUMERO_STORIES);
		log.info("end method /numeroStories/getByCriteria");
		return response;
	}

	@RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public Response<NumeroStoriesDto> custom(@RequestBody Request<NumeroStoriesDto> request) {
		log.info("NumeroStoriesDto method /$/custom");
		Response<NumeroStoriesDto> response = new Response<NumeroStoriesDto>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
			response = Validate.validateList(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = numeroStoriesBusiness.custom(request, locale);
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
		slf4jLogger.info("end method /NumeroStoriesDto/custom");
		return response;
	}

	@RequestMapping(value = "/exportNumeroStorieFile", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public Response<NumeroStoriesDto> export(@RequestBody Request<NumeroStoriesDto> request) {
		log.info("begin file export");
		Response<NumeroStoriesDto> response = new Response<NumeroStoriesDto>();
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale locale = new Locale(languageID, "");
		try {
			if(!response.isHasError()){
				response = numeroStoriesBusiness.exportNumberStories(request, locale);
			}else{
				slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
				response.getStatus().getMessage());
				return response;
			}
			if (!response.isHasError()) {
				slf4jLogger.info("end method custom");
				slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);
			}else {
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

}
