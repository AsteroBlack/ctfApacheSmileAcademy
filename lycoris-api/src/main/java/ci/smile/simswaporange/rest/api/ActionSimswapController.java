

/*
 * Java controller for entity table action_simswap 
 * Created on 2023-07-04 ( Time 12:38:30 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2017 Savoir Faire Linux. All Rights Reserved.
 */

package ci.smile.simswaporange.rest.api;

import ci.smile.simswaporange.proxy.response.LockUnLockFreezeDto;
import ci.smile.simswaporange.proxy.response.LockUnLockFreezeDtos;
import ci.smile.simswaporange.proxy.service.SimSwapServiceProxyService;
import ci.smile.simswaporange.utils.dto.customize._AppRequestDto;
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

import java.io.IOException;
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

/**
Controller for table "action_simswap"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/actionSimswap")
public class ActionSimswapController {

     @Autowired
	 private HttpServletRequest requestBasic;
	 @Autowired
	 private FunctionalError functionalError;
	 @Autowired
	 private ExceptionUtils exceptionUtils;

	@Autowired
	private SimSwapServiceProxyService simSwapServiceProxyService;

	 private Logger slf4jLogger = LoggerFactory.getLogger(getClass());

	@Autowired
    private ControllerFactory<ActionSimswapDto> controllerFactory;
	@Autowired
	private ActionSimswapBusiness actionSimswapBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ActionSimswapDto> create(@RequestBody Request<ActionSimswapDto> request) {
    	log.info("start method /actionSimswap/create");
        Response<ActionSimswapDto> response = controllerFactory.create(actionSimswapBusiness, request, FunctionalityEnum.CREATE_ACTION_SIMSWAP);
		log.info("end method /actionSimswap/create");
        return response;
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ActionSimswapDto> update(@RequestBody Request<ActionSimswapDto> request) {
    	log.info("start method /actionSimswap/update");
        Response<ActionSimswapDto> response = controllerFactory.update(actionSimswapBusiness, request, FunctionalityEnum.UPDATE_ACTION_SIMSWAP);
		log.info("end method /actionSimswap/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ActionSimswapDto> delete(@RequestBody Request<ActionSimswapDto> request) {
    	log.info("start method /actionSimswap/delete");
        Response<ActionSimswapDto> response = controllerFactory.delete(actionSimswapBusiness, request, FunctionalityEnum.DELETE_ACTION_SIMSWAP);
		log.info("end method /actionSimswap/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ActionSimswapDto> getByCriteria(@RequestBody Request<ActionSimswapDto> request) {
    	log.info("start method /actionSimswap/getByCriteria");
        Response<ActionSimswapDto> response = controllerFactory.getByCriteria(actionSimswapBusiness, request, FunctionalityEnum.VIEW_ACTION_SIMSWAP);
		log.info("end method /actionSimswap/getByCriteria");
        return response;
    }

    @RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = {
	   "application/json" }, produces = { "application/json" })
	 public Response<ActionSimswapDto> custom(@RequestBody Request<ActionSimswapDto> request) {
	  log.info("ActionSimswapDto method /$/custom");
	  Response<ActionSimswapDto> response = new Response<ActionSimswapDto>();
	  String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
	  Locale locale = new Locale(languageID, "");
	  try {
	   response = Validate.validateList(request, response, functionalError, locale);
	   if (!response.isHasError()) {
	    response = actionSimswapBusiness.custom(request, locale);
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
	  slf4jLogger.info("end method /ActionSimswapDto/custom");
	  return response;
	 }

	@RequestMapping(value = "/findbymsisdn", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public LockUnLockFreezeDto findByMsIsDn(@RequestBody _AppRequestDto appRequestDto) {
		log.info("ActionSimswapDto method /$/custom");
		LockUnLockFreezeDto response;
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
        response = simSwapServiceProxyService.findByMsisdn(UUID.randomUUID().toString() ,appRequestDto.getNumbers(), appRequestDto.getUser(), Boolean.FALSE);
        slf4jLogger.info("end method /ActionSimswapDto/custom");
		return response;
	}

	@RequestMapping(value = "/freezeNumber", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public LockUnLockFreezeDtos freezeNumber(@RequestBody _AppRequestDto appRequestDto) {
		log.info("ActionSimswapDto method /$/custom");
		LockUnLockFreezeDtos response;
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		response = simSwapServiceProxyService.freezeNumber(appRequestDto.getNumbers(), UUID.randomUUID().toString());
		slf4jLogger.info("end method /ActionSimswapDto/custom");
		return response;
	}

	@RequestMapping(value = "/lockNumber", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public LockUnLockFreezeDtos lockNumber(@RequestBody _AppRequestDto appRequestDto) {
		log.info("ActionSimswapDto method /$/custom");
		LockUnLockFreezeDtos response;
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		response = simSwapServiceProxyService.lockNumber(appRequestDto.getNumbers(), UUID.randomUUID().toString());
		slf4jLogger.info("end method /ActionSimswapDto/custom");
		return response;
	}

	@RequestMapping(value = "/unlockNumber", method = RequestMethod.POST, consumes = {
			"application/json" }, produces = { "application/json" })
	public LockUnLockFreezeDtos unlockNumber(@RequestBody _AppRequestDto appRequestDto) {
		log.info("ActionSimswapDto method /$/custom");
		LockUnLockFreezeDtos response;
		String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		response = simSwapServiceProxyService.unlockNumber(appRequestDto.getNumbers(), UUID.randomUUID().toString());
		slf4jLogger.info("end method /ActionSimswapDto/custom");
		return response;
	}
}
