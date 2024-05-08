

/*
 * Java controller for entity table profil 
 * Created on 2022-06-13 ( Time 13:07:17 )
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
Controller for table "profil"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/profil")
public class ProfilController {

	@Autowired
    private ControllerFactory<ProfilDto> controllerFactory;
	@Autowired
	private ProfilBusiness profilBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ProfilDto> create(@RequestBody Request<ProfilDto> request) {
    	log.info("start method /profil/create");
        Response<ProfilDto> response = controllerFactory.create(profilBusiness, request, FunctionalityEnum.CREATE_PROFIL);
		log.info("end method /profil/create");
        return response;
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ProfilDto> update(@RequestBody Request<ProfilDto> request) {
    	log.info("start method /profil/update");
        Response<ProfilDto> response = controllerFactory.update(profilBusiness, request, FunctionalityEnum.UPDATE_PROFIL);
		log.info("end method /profil/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ProfilDto> delete(@RequestBody Request<ProfilDto> request) {
    	log.info("start method /profil/delete");
        Response<ProfilDto> response = controllerFactory.delete(profilBusiness, request, FunctionalityEnum.DELETE_PROFIL);
		log.info("end method /profil/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ProfilDto> getByCriteria(@RequestBody Request<ProfilDto> request) {
    	log.info("start method /profil/getByCriteria");
        Response<ProfilDto> response = controllerFactory.getByCriteria(profilBusiness, request, FunctionalityEnum.VIEW_PROFIL);
		log.info("end method /profil/getByCriteria");
        return response;
    }
}
