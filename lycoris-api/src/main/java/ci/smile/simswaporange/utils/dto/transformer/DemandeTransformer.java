

/*
 * Java transformer for entity table demande 
 * Created on 2023-08-08 ( Time 13:26:59 )
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
 * TRANSFORMER for table "demande"
 * 
 * @author Geo
 *
 */
@Mapper
public interface DemandeTransformer {

	DemandeTransformer INSTANCE = Mappers.getMapper(DemandeTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="updatedAt"),
		@Mapping(source="entity.activationDate", dateFormat="dd/MM/yyyy HH:mm:ss",target="activationDate"),
		@Mapping(source="entity.category.id", target="idTypeCategory"),
		@Mapping(source="entity.category.code", target="categoryCode"),
		@Mapping(source="entity.category.libelle", target="categoryLibelle"),
		@Mapping(source="entity.status.id", target="idStatus"),
		@Mapping(source="entity.status.code", target="statusCode"),
		@Mapping(source="entity.user.id", target="idUser"),
		@Mapping(source="entity.user.nom", target="userNom"),
		@Mapping(source="entity.user.prenom", target="userPrenom"),
		@Mapping(source="entity.user.login", target="userLogin"),
		@Mapping(source="entity.typeNumero.id", target="typeNumeroId"),
		@Mapping(source="entity.typeNumero.libelle", target="typeNumeroLibelle"),
		@Mapping(source="entity.typeDemande.id", target="idTypeDemande"),
		@Mapping(source="entity.typeDemande.code", target="typeDemandeCode"),
		@Mapping(source="entity.typeDemande.libelle", target="typeDemandeLibelle"),

			@Mapping(source="entity.actionEnMasse.id", target="idActionEnMasse"),
			@Mapping(source="entity.actionEnMasse.libelle", target="actionEnMasseLibelle"),
			@Mapping(source="entity.actionEnMasse.code", target="actionEnMasseCode"),
			@Mapping(source="entity.actionEnMasse.identifiant", target="actionEnMasseIdentifiant"),
			@Mapping(source="entity.actionEnMasse.lienFichier", target="actionEnMasseLienFichier"),
	})
	DemandeDto toDto(Demande entity) throws ParseException;




	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<DemandeDto> toDtos(List<Demande> entities) throws ParseException;

    default DemandeDto toLiteDto(Demande entity) {
		if (entity == null) {
			return null;
		}
		DemandeDto dto = new DemandeDto();
		dto.setId( entity.getId() );
		return dto;
    }

	default List<DemandeDto> toLiteDtos(List<Demande> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<DemandeDto> dtos = new ArrayList<DemandeDto>();
		for (Demande entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.numero", target="numero"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="createdAt"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="updatedAt"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.updatedByTelco", target="updatedByTelco"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
		@Mapping(source="dto.motif", target="motif"),
		@Mapping(source="dto.offerName", target="offerName"),
		@Mapping(source="dto.activationDate", dateFormat="dd/MM/yyyy HH:mm:ss",target="activationDate"),
		@Mapping(source="dto.statusBscs", target="statusBscs"),
		@Mapping(source="dto.isValidated", target="isValidated"),
		@Mapping(source="dto.isTransfert", target="isTransfert"),
		@Mapping(source="dto.updatedByOmci", target="updatedByOmci"),
		@Mapping(source="dto.portNumber", target="portNumber"),
		@Mapping(source="dto.contractId", target="contractId"),
		@Mapping(source="dto.serialNumber", target="serialNumber"),
		@Mapping(source="category", target="category"),
		@Mapping(source="status", target="status"),
		@Mapping(source="user", target="user"),
		@Mapping(source="typeNumero", target="typeNumero"),
		@Mapping(source="typeDemande", target="typeDemande"),
		@Mapping(source="actionEnMasse", target="actionEnMasse"),
	})
    Demande toEntity(DemandeDto dto, Category category, Status status, User user, TypeNumero typeNumero, TypeDemande typeDemande, ActionEnMasse actionEnMasse) throws ParseException;

    //List<Demande> toEntities(List<DemandeDto> dtos) throws ParseException;

}
