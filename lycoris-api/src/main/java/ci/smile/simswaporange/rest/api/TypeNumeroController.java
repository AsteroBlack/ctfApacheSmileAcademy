

/*
 * Java controller for entity table type_numero 
 * Created on 2023-06-23 ( Time 15:16:29 )
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
Controller for table "type_numero"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/typeNumero")
public class TypeNumeroController {

     @Autowired
	 private HttpServletRequest requestBasic;
	 @Autowired
	 private FunctionalError functionalError;
	 @Autowired
	 private ExceptionUtils exceptionUtils;

	 private Logger slf4jLogger = LoggerFactory.getLogger(getClass());

	@Autowired
    private ControllerFactory<TypeNumeroDto> controllerFactory;
	@Autowired
	private TypeNumeroBusiness typeNumeroBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<TypeNumeroDto> create(@RequestBody Request<TypeNumeroDto> request) {
    	log.info("start method /typeNumero/create");
        Response<TypeNumeroDto> response = controllerFactory.create(typeNumeroBusiness, request, FunctionalityEnum.CREATE_TYPE_NUMERO);
		log.info("end method /typeNumero/create");
        return response;
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<TypeNumeroDto> update(@RequestBody Request<TypeNumeroDto> request) {
    	log.info("start method /typeNumero/update");
        Response<TypeNumeroDto> response = controllerFactory.update(typeNumeroBusiness, request, FunctionalityEnum.UPDATE_TYPE_NUMERO);
		log.info("end method /typeNumero/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<TypeNumeroDto> delete(@RequestBody Request<TypeNumeroDto> request) {
    	log.info("start method /typeNumero/delete");
        Response<TypeNumeroDto> response = controllerFactory.delete(typeNumeroBusiness, request, FunctionalityEnum.DELETE_TYPE_NUMERO);
		log.info("end method /typeNumero/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<TypeNumeroDto> getByCriteria(@RequestBody Request<TypeNumeroDto> request) {
    	log.info("start method /typeNumero/getByCriteria");
        Response<TypeNumeroDto> response = controllerFactory.getByCriteria(typeNumeroBusiness, request, FunctionalityEnum.VIEW_TYPE_NUMERO);
		log.info("end method /typeNumero/getByCriteria");
        return response;
    }

    @RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = {
	   "application/json" }, produces = { "application/json" })
	 public Response<TypeNumeroDto> custom(@RequestBody Request<TypeNumeroDto> request) {
	  log.info("TypeNumeroDto method /$/custom");
	  Response<TypeNumeroDto> response = new Response<TypeNumeroDto>();
	  String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
	  Locale locale = new Locale(languageID, "");
	  try {
	   response = Validate.validateList(request, response, functionalError, locale);
	   if (!response.isHasError()) {
	    response = typeNumeroBusiness.custom(request, locale);
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
	  slf4jLogger.info("end method /TypeNumeroDto/custom");
	  return response;
	 }
}
