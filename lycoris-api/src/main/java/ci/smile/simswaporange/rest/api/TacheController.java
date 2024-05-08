

/*
 * Java controller for entity table tache 
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
Controller for table "tache"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/tache")
public class TacheController {

     @Autowired
	 private HttpServletRequest requestBasic;
	 @Autowired
	 private FunctionalError functionalError;
	 @Autowired
	 private ExceptionUtils exceptionUtils;

	 private Logger slf4jLogger = LoggerFactory.getLogger(getClass());

	@Autowired
    private ControllerFactory<TacheDto> controllerFactory;
	@Autowired
	private TacheBusiness tacheBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<TacheDto> create(@RequestBody Request<TacheDto> request) {
    	log.info("start method /tache/create");
        Response<TacheDto> response = controllerFactory.create(tacheBusiness, request, FunctionalityEnum.CREATE_TACHE);
		log.info("end method /tache/create");
        return response;
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<TacheDto> update(@RequestBody Request<TacheDto> request) {
    	log.info("start method /tache/update");
        Response<TacheDto> response = controllerFactory.update(tacheBusiness, request, FunctionalityEnum.UPDATE_TACHE);
		log.info("end method /tache/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<TacheDto> delete(@RequestBody Request<TacheDto> request) {
    	log.info("start method /tache/delete");
        Response<TacheDto> response = controllerFactory.delete(tacheBusiness, request, FunctionalityEnum.DELETE_TACHE);
		log.info("end method /tache/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<TacheDto> getByCriteria(@RequestBody Request<TacheDto> request) {
    	log.info("start method /tache/getByCriteria");
        Response<TacheDto> response = controllerFactory.getByCriteria(tacheBusiness, request, FunctionalityEnum.VIEW_TACHE);
		log.info("end method /tache/getByCriteria");
        return response;
    }

    @RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = {
	   "application/json" }, produces = { "application/json" })
	 public Response<TacheDto> custom(@RequestBody Request<TacheDto> request) {
	  log.info("TacheDto method /$/custom");
	  Response<TacheDto> response = new Response<TacheDto>();
	  String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
	  Locale locale = new Locale(languageID, "");
	  try {
	   response = Validate.validateList(request, response, functionalError, locale);
	   if (!response.isHasError()) {
	    response = tacheBusiness.custom(request, locale);
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
	  slf4jLogger.info("end method /TacheDto/custom");
	  return response;
	 }
}
