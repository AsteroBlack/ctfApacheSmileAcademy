

/*
 * Java transformer for entity table tache 
 * Created on 2023-07-04 ( Time 13:18:47 )
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
 * TRANSFORMER for table "tache"
 * 
 * @author Geo
 *
 */
@Mapper
public interface TacheTransformer {

	TacheTransformer INSTANCE = Mappers.getMapper(TacheTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="updatedAt"),
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="createdAt"),
		@Mapping(source="entity.dateDemande", dateFormat="dd/MM/yyyy HH:mm:ss",target="dateDemande"),
		@Mapping(source="entity.actionEnMasse.id", target="idActionEnMasse"),
		@Mapping(source="entity.actionEnMasse.code", target="actionEnMasseCode"),
		@Mapping(source="entity.actionEnMasse.libelle", target="actionEnMasseLibelle"),
		@Mapping(source="entity.actionSimswap.id", target="idActionSimswap"),
		@Mapping(source="entity.actionSimswap.libelle", target="actionSimswapLibelle"),
		@Mapping(source="entity.demande.id", target="idDemande"),
		@Mapping(source="entity.status.id", target="idStatus"),
		@Mapping(source="entity.status.code", target="statusCode"),
		@Mapping(source="entity.typeDemande.id", target="idTypeDemande"),
		@Mapping(source="entity.typeDemande.code", target="typeDemandeCode"),
		@Mapping(source="entity.typeDemande.libelle", target="typeDemandeLibelle"),
		@Mapping(source="entity.user.id", target="idUser"),
		@Mapping(source="entity.user.nom", target="userNom"),
		@Mapping(source="entity.user.prenom", target="userPrenom"),
		@Mapping(source="entity.user.login", target="userLogin"),
	})
	TacheDto toDto(Tache entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<TacheDto> toDtos(List<Tache> entities) throws ParseException;

    default TacheDto toLiteDto(Tache entity) {
		if (entity == null) {
			return null;
		}
		TacheDto dto = new TacheDto();
		dto.setId( entity.getId() );
		return dto;
    }

	default List<TacheDto> toLiteDtos(List<Tache> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<TacheDto> dtos = new ArrayList<TacheDto>();
		for (Tache entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.dateDemande", dateFormat="dd/MM/yyyy",target="dateDemande"),
		@Mapping(source="actionEnMasse", target="actionEnMasse"),
		@Mapping(source="actionSimswap", target="actionSimswap"),
		@Mapping(source="demande", target="demande"),
		@Mapping(source="status", target="status"),
		@Mapping(source="typeDemande", target="typeDemande"),
		@Mapping(source="user", target="user"),
	})
    Tache toEntity(TacheDto dto, ActionEnMasse actionEnMasse, ActionSimswap actionSimswap, Demande demande, Status status, TypeDemande typeDemande, User user) throws ParseException;

    //List<Tache> toEntities(List<TacheDto> dtos) throws ParseException;

}
