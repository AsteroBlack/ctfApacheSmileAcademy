

/*
 * Java transformer for entity table action_utilisateur 
 * Created on 2024-01-11 ( Time 09:57:48 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.smile.simswaporange.utils.dto.transformer;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import ci.smile.simswaporange.utils.contract.*;
import ci.smile.simswaporange.utils.dto.*;
import ci.smile.simswaporange.dao.entity.*;


/**
 * TRANSFORMER for table "action_utilisateur"
 * 
 * @author Geo
 *
 */
@Mapper
public interface ActionUtilisateurTransformer {

	ActionUtilisateurTransformer INSTANCE = Mappers.getMapper(ActionUtilisateurTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="updatedAt"),
		@Mapping(source="entity.lastMachineDate", dateFormat="dd/MM/yyyy HH:mm:ss",target="lastMachineDate"),
		@Mapping(source="entity.lastDebloquage", dateFormat="dd/MM/yyyy HH:mm:ss",target="lastDebloquage"),
		@Mapping(source="entity.activationDate", dateFormat="dd/MM/yyyy HH:mm:ss",target="activationDate"),
		@Mapping(source="entity.lastBlocageDate", dateFormat="dd/MM/yyyy HH:mm:ss",target="lastBlocageDate"),
		@Mapping(source="entity.date", dateFormat="dd/MM/yyyy",target="date"),
		@Mapping(source="entity.typeNumero.id", target="typeNumeroId"),
		@Mapping(source="entity.typeNumero.libelle", target="typeNumeroLibelle"),
		@Mapping(source="entity.status.id", target="idStatus"),
		@Mapping(source="entity.status.code", target="statusCode"),
		@Mapping(source="entity.category.id", target="idCategory"),
		@Mapping(source="entity.category.code", target="categoryCode"),
		@Mapping(source="entity.category.libelle", target="categoryLibelle"),
		@Mapping(source="entity.user.id", target="idUser"),
		@Mapping(source="entity.user.nom", target="userNom"),
		@Mapping(source="entity.user.prenom", target="userPrenom"),
		@Mapping(source="entity.user.login", target="userLogin"),
		@Mapping(source="entity.profil.id", target="idProfil"),
		@Mapping(source="entity.profil.libelle", target="profilLibelle"),
		@Mapping(source="entity.profil.code", target="profilCode"),
	})
	ActionUtilisateurDto toDto(ActionUtilisateur entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<ActionUtilisateurDto> toDtos(List<ActionUtilisateur> entities) throws ParseException;

    default ActionUtilisateurDto toLiteDto(ActionUtilisateur entity) {
		if (entity == null) {
			return null;
		}
		ActionUtilisateurDto dto = new ActionUtilisateurDto();
		dto.setId( entity.getId() );
		return dto;
    }

	default List<ActionUtilisateurDto> toLiteDtos(List<ActionUtilisateur> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<ActionUtilisateurDto> dtos = new ArrayList<ActionUtilisateurDto>();
		for (ActionUtilisateur entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.date", target="date"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="createdAt"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="updatedAt"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.numero", target="numero"),
		@Mapping(source="dto.motif", target="motif"),
		@Mapping(source="dto.empreinte", target="empreinte"),
		@Mapping(source="dto.isExcelNumber", target="isExcelNumber"),
		@Mapping(source="dto.statut", target="statut"),
		@Mapping(source="dto.isMachine", target="isMachine"),
		@Mapping(source="dto.lastMachineDate", dateFormat="dd/MM/yyyy HH:mm:ss",target="lastMachineDate"),
		@Mapping(source="dto.lastDebloquage", dateFormat="dd/MM/yyyy HH:mm:ss",target="lastDebloquage"),
		@Mapping(source="dto.statusBscs", target="statusBscs"),
		@Mapping(source="dto.activationDate", dateFormat="dd/MM/yyyy HH:mm:ss",target="activationDate"),
		@Mapping(source="dto.offerName", target="offerName"),
		@Mapping(source="dto.fromBss", target="fromBss"),
		@Mapping(source="dto.lastBlocageDate", dateFormat="dd/MM/yyyy HH:mm:ss",target="lastBlocageDate"),
		@Mapping(source="dto.contractId", target="contractId"),
		@Mapping(source="dto.portNumber", target="portNumber"),
		@Mapping(source="dto.serialNumber", target="serialNumber"),
		@Mapping(source="dto.tmCode", target="tmCode"),
		@Mapping(source="typeNumero", target="typeNumero"),
		@Mapping(source="status", target="status"),
		@Mapping(source="category", target="category"),
		@Mapping(source="user", target="user"),
		@Mapping(source="profil", target="profil"),
	})
    ActionUtilisateur toEntity(ActionUtilisateurDto dto, TypeNumero typeNumero, Status status, Category category, User user, Profil profil) throws ParseException;

    //List<ActionUtilisateur> toEntities(List<ActionUtilisateurDto> dtos) throws ParseException;

}
