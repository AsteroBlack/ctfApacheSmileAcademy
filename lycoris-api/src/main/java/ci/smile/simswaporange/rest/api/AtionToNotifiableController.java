

/*
 * Java controller for entity table ation_to_notifiable 
 * Created on 2023-06-29 ( Time 14:15:46 )
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
Controller for table "ation_to_notifiable"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/ationToNotifiable")
public class AtionToNotifiableController {

     @Autowired
	 private HttpServletRequest requestBasic;
	 @Autowired
	 private FunctionalError functionalError;
	 @Autowired
	 private ExceptionUtils exceptionUtils;

	 private Logger slf4jLogger = LoggerFactory.getLogger(getClass());

	@Autowired
    private ControllerFactory<AtionToNotifiableDto> controllerFactory;
	@Autowired
	private AtionToNotifiableBusiness ationToNotifiableBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<AtionToNotifiableDto> create(@RequestBody Request<AtionToNotifiableDto> request) {
    	log.info("start method /ationToNotifiable/create");
        Response<AtionToNotifiableDto> response = controllerFactory.create(ationToNotifiableBusiness, request, FunctionalityEnum.CREATE_ATION_TO_NOTIFIABLE);
		log.info("end method /ationToNotifiable/create");
        return response;
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<AtionToNotifiableDto> update(@RequestBody Request<AtionToNotifiableDto> request) {
    	log.info("start method /ationToNotifiable/update");
        Response<AtionToNotifiableDto> response = controllerFactory.update(ationToNotifiableBusiness, request, FunctionalityEnum.UPDATE_ATION_TO_NOTIFIABLE);
		log.info("end method /ationToNotifiable/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<AtionToNotifiableDto> delete(@RequestBody Request<AtionToNotifiableDto> request) {
    	log.info("start method /ationToNotifiable/delete");
        Response<AtionToNotifiableDto> response = controllerFactory.delete(ationToNotifiableBusiness, request, FunctionalityEnum.DELETE_ATION_TO_NOTIFIABLE);
		log.info("end method /ationToNotifiable/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<AtionToNotifiableDto> getByCriteria(@RequestBody Request<AtionToNotifiableDto> request) {
    	log.info("start method /ationToNotifiable/getByCriteria");
        Response<AtionToNotifiableDto> response = controllerFactory.getByCriteria(ationToNotifiableBusiness, request, FunctionalityEnum.VIEW_ATION_TO_NOTIFIABLE);
		log.info("end method /ationToNotifiable/getByCriteria");
        return response;
    }

    @RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = {
	   "application/json" }, produces = { "application/json" })
	 public Response<AtionToNotifiableDto> custom(@RequestBody Request<AtionToNotifiableDto> request) {
	  log.info("AtionToNotifiableDto method /$/custom");
	  Response<AtionToNotifiableDto> response = new Response<AtionToNotifiableDto>();
	  String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
	  Locale locale = new Locale(languageID, "");
	  try {
	   response = Validate.validateList(request, response, functionalError, locale);
	   if (!response.isHasError()) {
	    response = ationToNotifiableBusiness.custom(request, locale);
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
	  slf4jLogger.info("end method /AtionToNotifiableDto/custom");
	  return response;
	 }
}
