

/*
 * Java controller for entity table profil_fonctionnalite 
 * Created on 2022-06-29 ( Time 14:51:16 )
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
Controller for table "profil_fonctionnalite"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/profilFonctionnalite")
public class ProfilFonctionnaliteController {

     @Autowired
	 private HttpServletRequest requestBasic;
	 @Autowired
	 private FunctionalError functionalError;
	 @Autowired
	 private ExceptionUtils exceptionUtils;

	 private Logger slf4jLogger = LoggerFactory.getLogger(getClass());

	@Autowired
    private ControllerFactory<ProfilFonctionnaliteDto> controllerFactory;
	@Autowired
	private ProfilFonctionnaliteBusiness profilFonctionnaliteBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ProfilFonctionnaliteDto> create(@RequestBody Request<ProfilFonctionnaliteDto> request) {
    	log.info("start method /profilFonctionnalite/create");
        Response<ProfilFonctionnaliteDto> response = controllerFactory.create(profilFonctionnaliteBusiness, request, FunctionalityEnum.CREATE_PROFIL_FONCTIONNALITE);
		log.info("end method /profilFonctionnalite/create");
        return response;
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ProfilFonctionnaliteDto> update(@RequestBody Request<ProfilFonctionnaliteDto> request) {
    	log.info("start method /profilFonctionnalite/update");
        Response<ProfilFonctionnaliteDto> response = controllerFactory.update(profilFonctionnaliteBusiness, request, FunctionalityEnum.UPDATE_PROFIL_FONCTIONNALITE);
		log.info("end method /profilFonctionnalite/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ProfilFonctionnaliteDto> delete(@RequestBody Request<ProfilFonctionnaliteDto> request) {
    	log.info("start method /profilFonctionnalite/delete");
        Response<ProfilFonctionnaliteDto> response = controllerFactory.delete(profilFonctionnaliteBusiness, request, FunctionalityEnum.DELETE_PROFIL_FONCTIONNALITE);
		log.info("end method /profilFonctionnalite/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ProfilFonctionnaliteDto> getByCriteria(@RequestBody Request<ProfilFonctionnaliteDto> request) {
    	log.info("start method /profilFonctionnalite/getByCriteria");
        Response<ProfilFonctionnaliteDto> response = controllerFactory.getByCriteria(profilFonctionnaliteBusiness, request, FunctionalityEnum.VIEW_PROFIL_FONCTIONNALITE);
		log.info("end method /profilFonctionnalite/getByCriteria");
        return response;
    }

//    @RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = {
//	   "application/json" }, produces = { "application/json" })
//	 public Response<ProfilFonctionnaliteDto> custom(@RequestBody Request<ProfilFonctionnaliteDto> request) {
//	  log.info("ProfilFonctionnaliteDto method /$/custom");
//	  Response<ProfilFonctionnaliteDto> response = new Response<ProfilFonctionnaliteDto>();
//	  String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
//	  Locale locale = new Locale(languageID, "");
//	  try {
//	   response = Validate.validateList(request, response, functionalError, locale);
//	   if (!response.isHasError()) {
//	    response = profilFonctionnaliteBusiness.custom(request, locale);
//	   } else {
//	    slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
//	      response.getStatus().getMessage());
//	    return response;
//	   }
//	   if (!response.isHasError()) {
//	    slf4jLogger.info("end method custom");
//	    slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);
//	   } else {
//	    slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
//	      response.getStatus().getMessage());
//	   }
//	  } catch (CannotCreateTransactionException e) {
//	   exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
//	  } catch (TransactionSystemException e) {
//	   exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
//	  } catch (RuntimeException e) {
//	   exceptionUtils.RUNTIME_EXCEPTION(response, locale, e);
//	  } catch (Exception e) {
//	   exceptionUtils.EXCEPTION(response, locale, e);
//	  }
//	  slf4jLogger.info("end method /ProfilFonctionnaliteDto/custom");
//	  return response;
//	 }
}
