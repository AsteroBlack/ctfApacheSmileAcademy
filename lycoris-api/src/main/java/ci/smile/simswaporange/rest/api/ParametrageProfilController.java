

/*
 * Java controller for entity table parametrage_profil 
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
Controller for table "parametrage_profil"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/parametrageProfil")
public class ParametrageProfilController {

     @Autowired
	 private HttpServletRequest requestBasic;
	 @Autowired
	 private FunctionalError functionalError;
	 @Autowired
	 private ExceptionUtils exceptionUtils;

	 private Logger slf4jLogger = LoggerFactory.getLogger(getClass());

	@Autowired
    private ControllerFactory<ParametrageProfilDto> controllerFactory;
	@Autowired
	private ParametrageProfilBusiness parametrageProfilBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ParametrageProfilDto> create(@RequestBody Request<ParametrageProfilDto> request) {
    	log.info("start method /parametrageProfil/create");
        Response<ParametrageProfilDto> response = controllerFactory.create(parametrageProfilBusiness, request, FunctionalityEnum.CREATE_PARAMETRAGE_PROFIL);
		log.info("end method /parametrageProfil/create");
        return response;
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ParametrageProfilDto> update(@RequestBody Request<ParametrageProfilDto> request) {
    	log.info("start method /parametrageProfil/update");
        Response<ParametrageProfilDto> response = controllerFactory.update(parametrageProfilBusiness, request, FunctionalityEnum.UPDATE_PARAMETRAGE_PROFIL);
		log.info("end method /parametrageProfil/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ParametrageProfilDto> delete(@RequestBody Request<ParametrageProfilDto> request) {
    	log.info("start method /parametrageProfil/delete");
        Response<ParametrageProfilDto> response = controllerFactory.delete(parametrageProfilBusiness, request, FunctionalityEnum.DELETE_PARAMETRAGE_PROFIL);
		log.info("end method /parametrageProfil/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ParametrageProfilDto> getByCriteria(@RequestBody Request<ParametrageProfilDto> request) {
    	log.info("start method /parametrageProfil/getByCriteria");
        Response<ParametrageProfilDto> response = controllerFactory.getByCriteria(parametrageProfilBusiness, request, FunctionalityEnum.VIEW_PARAMETRAGE_PROFIL);
		log.info("end method /parametrageProfil/getByCriteria");
        return response;
    }

    @RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = {
	   "application/json" }, produces = { "application/json" })
	 public Response<ParametrageProfilDto> custom(@RequestBody Request<ParametrageProfilDto> request) {
	  log.info("ParametrageProfilDto method /$/custom");
	  Response<ParametrageProfilDto> response = new Response<ParametrageProfilDto>();
	  String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
	  Locale locale = new Locale(languageID, "");
	  try {
	   response = Validate.validateList(request, response, functionalError, locale);
	   if (!response.isHasError()) {
	    response = parametrageProfilBusiness.custom(request, locale);
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
	  slf4jLogger.info("end method /ParametrageProfilDto/custom");
	  return response;
	 }
}
