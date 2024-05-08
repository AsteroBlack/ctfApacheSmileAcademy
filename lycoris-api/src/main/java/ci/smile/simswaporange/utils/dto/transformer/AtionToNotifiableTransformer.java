

/*
 * Java transformer for entity table ation_to_notifiable 
 * Created on 2023-06-29 ( Time 14:15:46 )
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
 * TRANSFORMER for table "ation_to_notifiable"
 * 
 * @author Geo
 *
 */
@Mapper
public interface AtionToNotifiableTransformer {

	AtionToNotifiableTransformer INSTANCE = Mappers.getMapper(AtionToNotifiableTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="updatedAt"),
		@Mapping(source="entity.user.id", target="idUserDemand"),
		@Mapping(source="entity.user.nom", target="userNom"),
		@Mapping(source="entity.user.prenom", target="userPrenom"),
		@Mapping(source="entity.user.login", target="userLogin"),
		@Mapping(source="entity.actionSimswap.id", target="idAction"),
		@Mapping(source="entity.actionSimswap.libelle", target="actionSimswapLibelle"),
		@Mapping(source="entity.status.id", target="idStatus"),
		@Mapping(source="entity.status.code", target="statusCode"),
		@Mapping(source="entity.user2.id", target="idUserValid"),
//		@Mapping(source="entity.user2.nom", target="userNom"),
//		@Mapping(source="entity.user2.prenom", target="userPrenom"),
//		@Mapping(source="entity.user2.login", target="userLogin"),
	})
	AtionToNotifiableDto toDto(AtionToNotifiable entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<AtionToNotifiableDto> toDtos(List<AtionToNotifiable> entities) throws ParseException;

    default AtionToNotifiableDto toLiteDto(AtionToNotifiable entity) {
		if (entity == null) {
			return null;
		}
		AtionToNotifiableDto dto = new AtionToNotifiableDto();
		dto.setId( entity.getId() );
		return dto;
    }

	default List<AtionToNotifiableDto> toLiteDtos(List<AtionToNotifiable> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<AtionToNotifiableDto> dtos = new ArrayList<AtionToNotifiableDto>();
		for (AtionToNotifiable entity : entities) {
			dtos.add(toLiteDto(entity));
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.numero", target="numero"),
		@Mapping(source="dto.isNotify", target="isNotify"),
		@Mapping(source="dto.isEnMasse", target="isEnMasse"),
		@Mapping(source="dto.statutAction", target="statutAction"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
		@Mapping(source="dto.message", target="message"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="user", target="user"),
		@Mapping(source="actionSimswap", target="actionSimswap"),
		@Mapping(source="status", target="status"),
		@Mapping(source="user2", target="user2"),
	})
    AtionToNotifiable toEntity(AtionToNotifiableDto dto, User user, ActionSimswap actionSimswap, Status status, User user2) throws ParseException;

    //List<AtionToNotifiable> toEntities(List<AtionToNotifiableDto> dtos) throws ParseException;

}
