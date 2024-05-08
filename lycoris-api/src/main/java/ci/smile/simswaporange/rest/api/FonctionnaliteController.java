

/*
 * Java controller for entity table fonctionnalite 
 * Created on 2022-06-13 ( Time 13:07:16 )
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

import ci.smile.simswaporange.utils.*;
import ci.smile.simswaporange.utils.dto.*;
import ci.smile.simswaporange.utils.contract.*;
import ci.smile.simswaporange.utils.contract.Request;
import ci.smile.simswaporange.utils.contract.Response;
import ci.smile.simswaporange.utils.enums.FunctionalityEnum;
import ci.smile.simswaporange.business.*;
import ci.smile.simswaporange.rest.fact.ControllerFactory;

/**
Controller for table "fonctionnalite"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/fonctionnalite")
public class FonctionnaliteController {

	@Autowired
    private ControllerFactory<FonctionnaliteDto> controllerFactory;
	@Autowired
	private FonctionnaliteBusiness fonctionnaliteBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<FonctionnaliteDto> create(@RequestBody Request<FonctionnaliteDto> request) {
    	log.info("start method /fonctionnalite/create");
        Response<FonctionnaliteDto> response = controllerFactory.create(fonctionnaliteBusiness, request, FunctionalityEnum.CREATE_FONCTIONNALITE);
		log.info("end method /fonctionnalite/create");
        return response;
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<FonctionnaliteDto> update(@RequestBody Request<FonctionnaliteDto> request) {
    	log.info("start method /fonctionnalite/update");
        Response<FonctionnaliteDto> response = controllerFactory.update(fonctionnaliteBusiness, request, FunctionalityEnum.UPDATE_FONCTIONNALITE);
		log.info("end method /fonctionnalite/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<FonctionnaliteDto> delete(@RequestBody Request<FonctionnaliteDto> request) {
    	log.info("start method /fonctionnalite/delete");
        Response<FonctionnaliteDto> response = controllerFactory.delete(fonctionnaliteBusiness, request, FunctionalityEnum.DELETE_FONCTIONNALITE);
		log.info("end method /fonctionnalite/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<FonctionnaliteDto> getByCriteria(@RequestBody Request<FonctionnaliteDto> request) {
    	log.info("start method /fonctionnalite/getByCriteria");
        Response<FonctionnaliteDto> response = controllerFactory.getByCriteria(fonctionnaliteBusiness, request, FunctionalityEnum.VIEW_FONCTIONNALITE);
		log.info("end method /fonctionnalite/getByCriteria");
        return response;
    }
}
