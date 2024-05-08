

/*
 * Java controller for entity table parametrage 
 * Created on 2023-06-21 ( Time 19:28:13 )
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
Controller for table "parametrage"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/parametrage")
public class ParametrageController {

     @Autowired
	 private HttpServletRequest requestBasic;
	 @Autowired
	 private FunctionalError functionalError;
	 @Autowired
	 private ExceptionUtils exceptionUtils;

	 private Logger slf4jLogger = LoggerFactory.getLogger(getClass());

	@Autowired
    private ControllerFactory<ParametrageDto> controllerFactory;
	@Autowired
	private ParametrageBusiness parametrageBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ParametrageDto> create(@RequestBody Request<ParametrageDto> request) {
    	log.info("start method /parametrage/create");
        Response<ParametrageDto> response = controllerFactory.create(parametrageBusiness, request, FunctionalityEnum.CREATE_PARAMETRAGE);
		log.info("end method /parametrage/create");
        return response;
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ParametrageDto> update(@RequestBody Request<ParametrageDto> request) {
    	log.info("start method /parametrage/update");
        Response<ParametrageDto> response = controllerFactory.update(parametrageBusiness, request, FunctionalityEnum.UPDATE_PARAMETRAGE);
		log.info("end method /parametrage/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ParametrageDto> delete(@RequestBody Request<ParametrageDto> request) {
    	log.info("start method /parametrage/delete");
        Response<ParametrageDto> response = controllerFactory.delete(parametrageBusiness, request, FunctionalityEnum.DELETE_PARAMETRAGE);
		log.info("end method /parametrage/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ParametrageDto> getByCriteria(@RequestBody Request<ParametrageDto> request) {
    	log.info("start method /parametrage/getByCriteria");
        Response<ParametrageDto> response = controllerFactory.getByCriteria(parametrageBusiness, request, FunctionalityEnum.VIEW_PARAMETRAGE);
		log.info("end method /parametrage/getByCriteria");
        return response;
    }

    @RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = {
	   "application/json" }, produces = { "application/json" })
	 public Response<ParametrageDto> custom(@RequestBody Request<ParametrageDto> request) {
	  log.info("ParametrageDto method /$/custom");
	  Response<ParametrageDto> response = new Response<ParametrageDto>();
	  String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
	  Locale locale = new Locale(languageID, "");
	  try {
	   response = Validate.validateList(request, response, functionalError, locale);
	   if (!response.isHasError()) {
	    response = parametrageBusiness.custom(request, locale);
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
	  slf4jLogger.info("end method /ParametrageDto/custom");
	  return response;
	 }
}
