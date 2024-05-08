

/*
 * Java controller for entity table action_parametrable 
 * Created on 2023-06-29 ( Time 14:01:11 )
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
Controller for table "action_parametrable"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/actionParametrable")
public class ActionParametrableController {

     @Autowired
	 private HttpServletRequest requestBasic;
	 @Autowired
	 private FunctionalError functionalError;
	 @Autowired
	 private ExceptionUtils exceptionUtils;

	 private Logger slf4jLogger = LoggerFactory.getLogger(getClass());

	@Autowired
    private ControllerFactory<ActionParametrableDto> controllerFactory;
	@Autowired
	private ActionParametrableBusiness actionParametrableBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ActionParametrableDto> create(@RequestBody Request<ActionParametrableDto> request) {
    	log.info("start method /actionParametrable/create");
        Response<ActionParametrableDto> response = controllerFactory.create(actionParametrableBusiness, request, FunctionalityEnum.CREATE_ACTION_PARAMETRABLE);
		log.info("end method /actionParametrable/create");
        return response;
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ActionParametrableDto> update(@RequestBody Request<ActionParametrableDto> request) {
    	log.info("start method /actionParametrable/update");
        Response<ActionParametrableDto> response = controllerFactory.update(actionParametrableBusiness, request, FunctionalityEnum.UPDATE_ACTION_PARAMETRABLE);
		log.info("end method /actionParametrable/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ActionParametrableDto> delete(@RequestBody Request<ActionParametrableDto> request) {
    	log.info("start method /actionParametrable/delete");
        Response<ActionParametrableDto> response = controllerFactory.delete(actionParametrableBusiness, request, FunctionalityEnum.DELETE_ACTION_PARAMETRABLE);
		log.info("end method /actionParametrable/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ActionParametrableDto> getByCriteria(@RequestBody Request<ActionParametrableDto> request) {
    	log.info("start method /actionParametrable/getByCriteria");
        Response<ActionParametrableDto> response = controllerFactory.getByCriteria(actionParametrableBusiness, request, FunctionalityEnum.VIEW_ACTION_PARAMETRABLE);
		log.info("end method /actionParametrable/getByCriteria");
        return response;
    }

    @RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = {
	   "application/json" }, produces = { "application/json" })
	 public Response<ActionParametrableDto> custom(@RequestBody Request<ActionParametrableDto> request) {
	  log.info("ActionParametrableDto method /$/custom");
	  Response<ActionParametrableDto> response = new Response<ActionParametrableDto>();
	  String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
	  Locale locale = new Locale(languageID, "");
	  try {
	   response = Validate.validateList(request, response, functionalError, locale);
	   if (!response.isHasError()) {
	    response = actionParametrableBusiness.custom(request, locale);
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
	  slf4jLogger.info("end method /ActionParametrableDto/custom");
	  return response;
	 }
}
