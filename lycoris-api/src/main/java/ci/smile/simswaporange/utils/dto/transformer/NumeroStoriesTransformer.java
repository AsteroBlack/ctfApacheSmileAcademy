

/*
 * Java transformer for entity table numero_stories 
 * Created on 2023-07-04 ( Time 13:03:13 )
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
 * TRANSFORMER for table "numero_stories"
 * 
 * @author Geo
 *
 */
@Mapper
public interface NumeroStoriesTransformer {

	NumeroStoriesTransformer INSTANCE = Mappers.getMapper(NumeroStoriesTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="updatedAt"),
		@Mapping(source="entity.typeNumero.id", target="idTypeNumero"),
		@Mapping(source="entity.typeNumero.libelle", target="typeNumeroLibelle"),
		@Mapping(source="entity.profil.id", target="idProfil"),
		@Mapping(source="entity.profil.libelle", target="profilLibelle"),
		@Mapping(source="entity.profil.code", target="profilCode"),
		@Mapping(source="entity.category.id", target="idCategory"),
		@Mapping(source="entity.category.code", target="categoryCode"),
		@Mapping(source="entity.category.libelle", target="categoryLibelle"),
		@Mapping(source="entity.user.id", target="createdBy"),
		@Mapping(source="entity.user.nom", target="userNom"),
		@Mapping(source="entity.user.prenom", target="userPrenom"),
		@Mapping(source="entity.user.login", target="userLogin"),
		@Mapping(source="entity.status.id", target="idStatut"),
		@Mapping(source="entity.status.code", target="statusCode"),
	})
	NumeroStoriesDto toDto(NumeroStories entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<NumeroStoriesDto> toDtos(List<NumeroStories> entities) throws ParseException;

    default NumeroStoriesDto toLiteDto(NumeroStories entity) {
		if (entity == null) {
			return null;
		}
		NumeroStoriesDto dto = new NumeroStoriesDto();
		dto.setId( entity.getId() );
		dto.setLogin( entity.getLogin() );
		return dto;
    }

	default List<NumeroStoriesDto> toLiteDtos(List<NumeroStories> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<NumeroStoriesDto> dtos = new ArrayList<NumeroStoriesDto>();
		for (NumeroStories entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.numero", target="numero"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.isMachine", target="isMachine"),
		@Mapping(source="dto.statut", target="statut"),
		@Mapping(source="dto.login", target="login"),
		@Mapping(source="dto.machine", target="machine"),
		@Mapping(source="dto.portNumber", target="portNumber"),
		@Mapping(source="dto.offerName", target="offerName"),
		@Mapping(source="dto.contractId", target="contractId"),
		@Mapping(source="dto.serialNumber", target="serialNumber"),
		@Mapping(source="dto.adresseIp", target="adresseIp"),
		@Mapping(source="typeNumero", target="typeNumero"),
		@Mapping(source="profil", target="profil"),
		@Mapping(source="category", target="category"),
		@Mapping(source="user", target="user"),
		@Mapping(source="status", target="status"),
	})
    NumeroStories toEntity(NumeroStoriesDto dto, TypeNumero typeNumero, Profil profil, Category category, User user, Status status) throws ParseException;

    //List<NumeroStories> toEntities(List<NumeroStoriesDto> dtos) throws ParseException;

}
