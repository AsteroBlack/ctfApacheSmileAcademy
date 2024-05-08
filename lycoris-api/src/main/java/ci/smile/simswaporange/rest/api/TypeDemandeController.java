

/*
 * Java controller for entity table type_demande 
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
Controller for table "type_demande"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/typeDemande")
public class TypeDemandeController {

     @Autowired
	 private HttpServletRequest requestBasic;
	 @Autowired
	 private FunctionalError functionalError;
	 @Autowired
	 private ExceptionUtils exceptionUtils;

	 private Logger slf4jLogger = LoggerFactory.getLogger(getClass());

	@Autowired
    private ControllerFactory<TypeDemandeDto> controllerFactory;
	@Autowired
	private TypeDemandeBusiness typeDemandeBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<TypeDemandeDto> create(@RequestBody Request<TypeDemandeDto> request) {
    	log.info("start method /typeDemande/create");
        Response<TypeDemandeDto> response = controllerFactory.create(typeDemandeBusiness, request, FunctionalityEnum.CREATE_TYPE_DEMANDE);
		log.info("end method /typeDemande/create");
        return response;
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<TypeDemandeDto> update(@RequestBody Request<TypeDemandeDto> request) {
    	log.info("start method /typeDemande/update");
        Response<TypeDemandeDto> response = controllerFactory.update(typeDemandeBusiness, request, FunctionalityEnum.UPDATE_TYPE_DEMANDE);
		log.info("end method /typeDemande/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<TypeDemandeDto> delete(@RequestBody Request<TypeDemandeDto> request) {
    	log.info("start method /typeDemande/delete");
        Response<TypeDemandeDto> response = controllerFactory.delete(typeDemandeBusiness, request, FunctionalityEnum.DELETE_TYPE_DEMANDE);
		log.info("end method /typeDemande/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<TypeDemandeDto> getByCriteria(@RequestBody Request<TypeDemandeDto> request) {
    	log.info("start method /typeDemande/getByCriteria");
        Response<TypeDemandeDto> response = controllerFactory.getByCriteria(typeDemandeBusiness, request, FunctionalityEnum.VIEW_TYPE_DEMANDE);
		log.info("end method /typeDemande/getByCriteria");
        return response;
    }

    @RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = {
	   "application/json" }, produces = { "application/json" })
	 public Response<TypeDemandeDto> custom(@RequestBody Request<TypeDemandeDto> request) {
	  log.info("TypeDemandeDto method /$/custom");
	  Response<TypeDemandeDto> response = new Response<TypeDemandeDto>();
	  String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
	  Locale locale = new Locale(languageID, "");
	  try {
	   response = Validate.validateList(request, response, functionalError, locale);
	   if (!response.isHasError()) {
	    response = typeDemandeBusiness.custom(request, locale);
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
	  slf4jLogger.info("end method /TypeDemandeDto/custom");
	  return response;
	 }
}
