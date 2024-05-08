

/*
 * Java transformer for entity table user 
 * Created on 2023-07-05 ( Time 13:47:04 )
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
 * TRANSFORMER for table "user"
 * 
 * @author Geo
 *
 */
@Mapper
public interface UserTransformer {

	UserTransformer INSTANCE = Mappers.getMapper(UserTransformer.class);

	@FullTransformerQualifier
	@Mappings({
		@Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="createdAt"),
		@Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="updatedAt"),
		@Mapping(source="entity.lockedAt", dateFormat="dd/MM/yyyy HH:mm:ss",target="lockedAt"),
		@Mapping(source="entity.firstConnection", dateFormat="dd/MM/yyyy HH:mm:ss",target="firstConnection"),
		@Mapping(source="entity.lastConnection", dateFormat="dd/MM/yyyy HH:mm:ss",target="lastConnection"),
		@Mapping(source="entity.profil.id", target="idProfil"),
		@Mapping(source="entity.profil.libelle", target="profilLibelle"),
		@Mapping(source="entity.profil.code", target="profilCode"),
		@Mapping(source="entity.civilite.id", target="idCivilite"),
		@Mapping(source="entity.civilite.libelle", target="civiliteLibelle"),
		@Mapping(source="entity.category.id", target="idCategory"),
		@Mapping(source="entity.category.code", target="categoryCode"),
		@Mapping(source="entity.category.libelle", target="categoryLibelle"),
	})
	UserDto toDto(User entity) throws ParseException;

	@IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<UserDto> toDtos(List<User> entities) throws ParseException;

    default UserDto toLiteDto(User entity) {
		if (entity == null) {
			return null;
		}
		UserDto dto = new UserDto();
		dto.setId( entity.getId() );
		dto.setNom( entity.getNom() );
		dto.setPrenom( entity.getPrenom() );
		dto.setLogin( entity.getLogin() );
		return dto;
    }

	default List<UserDto> toLiteDtos(List<User> entities) {
		if (entities == null || entities.stream().allMatch(o -> o == null)) {
			return null;
		}
		List<UserDto> dtos = new ArrayList<UserDto>();
		for (User entity : entities) {
			dtos.add(toLiteDto(entity)); 
		}
		return dtos;
	}

	@Mappings({
		@Mapping(source="dto.id", target="id"),
		@Mapping(source="dto.nom", target="nom"),
		@Mapping(source="dto.prenom", target="prenom"),
		@Mapping(source="dto.isValidated", target="isValidated"),
		@Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
		@Mapping(source="dto.createdBy", target="createdBy"),
		@Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
		@Mapping(source="dto.updatedBy", target="updatedBy"),
		@Mapping(source="dto.isDeleted", target="isDeleted"),
		@Mapping(source="dto.isLocked", target="isLocked"),
		@Mapping(source="dto.lockedAt", dateFormat="dd/MM/yyyy",target="lockedAt"),
		@Mapping(source="dto.lockedBy", target="lockedBy"),
		@Mapping(source="dto.login", target="login"),
		@Mapping(source="dto.isSuperAdmin", target="isSuperAdmin"),
		@Mapping(source="dto.locked", target="locked"),
		@Mapping(source="dto.contact", target="contact"),
		@Mapping(source="dto.isConnected", target="isConnected"),
		@Mapping(source="dto.emailAdresse", target="emailAdresse"),
			@Mapping(source="dto.status", target="status"),
		@Mapping(source="dto.ipadress", target="ipadress"),
		@Mapping(source="dto.machine", target="machine"),


		@Mapping(source="dto.firstConnection", dateFormat="dd/MM/yyyy",target="firstConnection"),
		@Mapping(source="dto.lastConnection", dateFormat="dd/MM/yyyy",target="lastConnection"),
		@Mapping(source="profil", target="profil"),
		@Mapping(source="civilite", target="civilite"),
		@Mapping(source="category", target="category"),
	})
    User toEntity(UserDto dto, Profil profil, Civilite civilite, Category category) throws ParseException;

    //List<User> toEntities(List<UserDto> dtos) throws ParseException;

}
