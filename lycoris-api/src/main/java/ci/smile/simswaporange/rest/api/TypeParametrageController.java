

/*
 * Java controller for entity table type_parametrage 
 * Created on 2023-06-21 ( Time 19:28:14 )
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
Controller for table "type_parametrage"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/typeParametrage")
public class TypeParametrageController {

     @Autowired
	 private HttpServletRequest requestBasic;
	 @Autowired
	 private FunctionalError functionalError;
	 @Autowired
	 private ExceptionUtils exceptionUtils;

	 private Logger slf4jLogger = LoggerFactory.getLogger(getClass());

	@Autowired
    private ControllerFactory<TypeParametrageDto> controllerFactory;
	@Autowired
	private TypeParametrageBusiness typeParametrageBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<TypeParametrageDto> create(@RequestBody Request<TypeParametrageDto> request) {
    	log.info("start method /typeParametrage/create");
        Response<TypeParametrageDto> response = controllerFactory.create(typeParametrageBusiness, request, FunctionalityEnum.CREATE_TYPE_PARAMETRAGE);
		log.info("end method /typeParametrage/create");
        return response;
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<TypeParametrageDto> update(@RequestBody Request<TypeParametrageDto> request) {
    	log.info("start method /typeParametrage/update");
        Response<TypeParametrageDto> response = controllerFactory.update(typeParametrageBusiness, request, FunctionalityEnum.UPDATE_TYPE_PARAMETRAGE);
		log.info("end method /typeParametrage/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<TypeParametrageDto> delete(@RequestBody Request<TypeParametrageDto> request) {
    	log.info("start method /typeParametrage/delete");
        Response<TypeParametrageDto> response = controllerFactory.delete(typeParametrageBusiness, request, FunctionalityEnum.DELETE_TYPE_PARAMETRAGE);
		log.info("end method /typeParametrage/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<TypeParametrageDto> getByCriteria(@RequestBody Request<TypeParametrageDto> request) {
    	log.info("start method /typeParametrage/getByCriteria");
        Response<TypeParametrageDto> response = controllerFactory.getByCriteria(typeParametrageBusiness, request, FunctionalityEnum.VIEW_TYPE_PARAMETRAGE);
		log.info("end method /typeParametrage/getByCriteria");
        return response;
    }

    @RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = {
	   "application/json" }, produces = { "application/json" })
	 public Response<TypeParametrageDto> custom(@RequestBody Request<TypeParametrageDto> request) {
	  log.info("TypeParametrageDto method /$/custom");
	  Response<TypeParametrageDto> response = new Response<TypeParametrageDto>();
	  String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
	  Locale locale = new Locale(languageID, "");
	  try {
	   response = Validate.validateList(request, response, functionalError, locale);
	   if (!response.isHasError()) {
	    response = typeParametrageBusiness.custom(request, locale);
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
	  slf4jLogger.info("end method /TypeParametrageDto/custom");
	  return response;
	 }
}
